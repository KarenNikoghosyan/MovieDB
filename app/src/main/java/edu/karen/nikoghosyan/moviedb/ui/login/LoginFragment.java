package edu.karen.nikoghosyan.moviedb.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
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

import edu.karen.nikoghosyan.moviedb.LoadingActivity;
import edu.karen.nikoghosyan.moviedb.MainActivity;
import edu.karen.nikoghosyan.moviedb.R;
import maes.tech.intentanim.CustomIntent;

public class LoginFragment extends Fragment {
    private TextView tvRegister;
    private Button btnLogin;
    private EditText etName;
    private EditText etEmailLogin;
    private EditText etPasswordLogin;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null) {
            prefs = getActivity().getApplicationContext().getSharedPreferences("MovieDBPrefs", 0);
            editor = prefs.edit();
        }


        etName = view.findViewById(R.id.etName);
        etEmailLogin = view.findViewById(R.id.etEmailLogin);
        etPasswordLogin = view.findViewById(R.id.etPasswordLogin);
        btnLogin = view.findViewById(R.id.btnLogin);
        tvRegister = view.findViewById(R.id.tvRegister);
        tvRegister.setPaintFlags(tvRegister.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        tvRegister.setOnClickListener(v -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_loginFragment_to_registerFragment);
        });

        if (LoadingActivity.isLogged) {
            goToMainActivity();
        }

        btnLogin.setOnClickListener(v -> {

            if (!isNameValid() | !isEmailValid() | !isPasswordValid()) {
                return;
            }

            toggleProgressDialog(true);

            if (getActivity() == null) { return; }

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(getEmail(), getPassword())
                    .addOnSuccessListener(getActivity(), authResult -> {
                        editor.putString("name", etName.getText().toString());
                        editor.apply();

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
        if (getActivity() == null) { return; }

        toggleProgressDialog(false);
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        CustomIntent.customType(getContext(), "left-to-right");
        getActivity().finish();
    }

    private String getName() { return etName.getText().toString(); }

    private String getEmail() {
        return etEmailLogin.getText().toString();
    }

    private String getPassword() {
        return etPasswordLogin.getText().toString();
    }

    private boolean isNameValid() {
        boolean isValid = getName().length() > 1;
        if (!isValid) {
            etName.setError("Name is too short");
        }
        return isValid;
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