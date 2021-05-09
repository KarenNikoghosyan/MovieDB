package edu.karen.nikoghosyan.moviedb.ui.login;

import android.app.ProgressDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import edu.karen.nikoghosyan.moviedb.R;


public class RegisterFragment extends Fragment {
    private TextView tvLogin;
    private Button btnRegister;
    private EditText etEmailRegister;
    private EditText etPasswordRegister;
    private EditText etConfirmPassword;

    private DocumentReference documentReference;
    private FirebaseFirestore fStore;
    private String userID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvLogin = view.findViewById(R.id.tvLogin);
        tvLogin.setPaintFlags(tvLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnRegister = view.findViewById(R.id.btnRegister);
        etEmailRegister = view.findViewById(R.id.etEmailLogin);
        etPasswordRegister = view.findViewById(R.id.etPasswordLogin);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);

        tvLogin.setOnClickListener(v -> NavHostFragment
                .findNavController(this)
                .navigate(R.id.action_registerFragment_to_loginFragment));

        btnRegister.setOnClickListener(v -> {

            if (!isConfirmPasswordValid()) { return; }
            if (!isEmailValid() | !isPasswordValid()) {
                return;
            }

            toggleProgressDialog(true);

            if (getActivity() == null) { return; }

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(getEmail(), getPassword())
                    .addOnSuccessListener(getActivity(), authResult -> {
                        fStore = FirebaseFirestore.getInstance();

                        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        documentReference = fStore.collection("users").document(userID);
                        Map<String, Object> user = new HashMap<>();

                        documentReference.get().addOnSuccessListener(documentSnapshot -> {
                            documentReference.set(user).addOnSuccessListener(aVoid -> Log.d("TAG", "Bookmark was added for user" + userID));
                            gotoLoginFragment();
                        });

                    }).addOnFailureListener(getActivity(), e -> showError(e));
        });
    }

    private void showError(Exception e) {
        toggleProgressDialog(false);
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void gotoLoginFragment() {
        toggleProgressDialog(false);
        NavHostFragment
                .findNavController(this)
                .navigate(R.id.action_registerFragment_to_loginFragment);
        Toast.makeText(getContext(), "Account Created Successfully", Toast.LENGTH_LONG).show();
    }

    private String getEmail() {
        return etEmailRegister.getText().toString();
    }

    private String getPassword() {
        return etPasswordRegister.getText().toString();
    }

    private String getConfirmPassword() {
        return etConfirmPassword.getText().toString();
    }

    private boolean isEmailValid() {
        boolean isValid = Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
        if (!isValid) {
            etEmailRegister.setError("Email is not valid");
        }
        return isValid;
    }

    private boolean isPasswordValid() {
        boolean isValid = getPassword().length() > 5;
        if (!isValid) {
            etPasswordRegister.setError("Password must contain at least 6 characters");
        }
        return isValid;
    }

    private boolean isConfirmPasswordValid() {
        boolean isValid = getConfirmPassword().equals(getPassword());
        if (!isValid) {
            etConfirmPassword.setError("Passwords don't match");
        }
        return isValid;
    }

    private ProgressDialog progressDialog;

    public void toggleProgressDialog(boolean shouldShow) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Connecting, Please Wait");
        }

        if (shouldShow)
            progressDialog.show();
        else
            progressDialog.dismiss();
    }
}