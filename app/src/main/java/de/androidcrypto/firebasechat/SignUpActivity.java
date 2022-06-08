package de.androidcrypto.firebasechat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUp activity";
    EditText userEmail, userPassword;
    Button logIn, signIn;
    Intent logInIntent;
    private FirebaseAuth mAuth;
    FirebaseUser logedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userEmail = findViewById(R.id.etSigninEmail);
        userPassword = findViewById(R.id.etSigninPassword);
        logIn = findViewById(R.id.btnSigninLogIn);
        signIn = findViewById(R.id.btnSigninSignUp);
        logInIntent = new Intent(SignUpActivity.this, MainActivity.class);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(logInIntent);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();
                if (email.equals("")) {
                    System.out.println("*** Error: Email is empty ***");
                    return;
                }
                if (password.equals("")) {
                    System.out.println("*** Error: Password is empty ***");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signUpWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    logedInUser = mAuth.getCurrentUser();
                                    updateUI(user);
                                    Intent intent=new Intent(SignUpActivity.this,setProfile.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signUpWithEmail:failure", task.getException());
                                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    logedInUser = null;
                                    updateUI(null);
                                }
                            }
                        });
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            System.out.println("User: " + user.getEmail());
        }
    }
}