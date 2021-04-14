package edu.karen.nikoghosyan.moviedb.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import edu.karen.nikoghosyan.moviedb.LoginActivity;
import edu.karen.nikoghosyan.moviedb.MainActivity;
import edu.karen.nikoghosyan.moviedb.R;
import maes.tech.intentanim.CustomIntent;

public class LoginFragment extends Fragment {
    private TextView tvRegister;
    private Button btnLogin;
    private EditText etEmailLogin;
    private EditText etPasswordLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvRegister = view.findViewById(R.id.tvRegister);
        tvRegister.setPaintFlags(tvRegister.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnLogin = view.findViewById(R.id.btnLogin);
        etEmailLogin = view.findViewById(R.id.etEmailLogin);
        etPasswordLogin = view.findViewById(R.id.etPasswordLogin);

        tvRegister.setOnClickListener(v -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_loginFragment_to_registerFragment);
        });

        btnLogin.setOnClickListener(v -> {

            if (!isEmailValid() | !isPasswordValid()) {
                return;
            }

            toggleProgressDialog(true);

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(getEmail(), getPassword())
                    .addOnSuccessListener(getActivity(), authResult -> {
                        goToMainActivity();
                    }).addOnFailureListener(getActivity(), e -> {
                showError(e);
            });
        });
    }

    private void showError(Exception e) {
        toggleProgressDialog(false);
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void goToMainActivity() {
        toggleProgressDialog(false);
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        CustomIntent.customType(getContext(), "left-to-right");
        getActivity().finish();
    }

    private String getEmail() {
        return etEmailLogin.getText().toString();
    }

    private String getPassword() {
        return etPasswordLogin.getText().toString();
    }

    private boolean isEmailValid() {
        boolean isValid = Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
        if (!isValid) {
            etEmailLogin.setError("Email is not valid");
        }
        return isValid;
    }

    private boolean isPasswordValid() {
        boolean isValid = getPassword().length() > 5;
        if (!isValid) {
            etPasswordLogin.setError("Password must contain at least 6 characters");
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