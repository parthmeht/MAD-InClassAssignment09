package com.parth.android.inclassassignment9;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements LoginFragment.LoginFragmentListener,
        SignUpFragment.SignUpListener, ChatRoomFragment.ChatRoomListener {

    private FirebaseAuth mAuth;
    private String TAG = "MainActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser,"start");
    }

    private void updateUI(FirebaseUser currentUser, String flag) {
        if (currentUser!=null){
            Log.d(TAG, "Logged In Successfully");
            setTitle("Chat Room");
            if (flag.equalsIgnoreCase("start")){
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new ChatRoomFragment(), "ChatRoomFragment")
                        .commit();
            }else{
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new ChatRoomFragment(), "ChatRoomFragment")
                        .addToBackStack(null)
                        .commit();
            }
        }else {
            setTitle("Login");
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LoginFragment(), "LoginFragment")
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void goToSignUp() {
        setTitle("Sign Up");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new SignUpFragment(), "SignUpFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void login(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user, "login");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null,null);
                        }
                    }
                });
    }

    @Override
    public void signUp(User user) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user, "login");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null, null);
                        }

                    }
                });
    }

    @Override
    public void goToLogin() {
        setTitle("Login");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new LoginFragment(), "LoginFragment")
                .addToBackStack(null)
                .commit();
    }

    public static boolean validateEmail(String email) {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public static boolean validatePassword(String password) {
        if (password == null || password.equalsIgnoreCase("") || password.length() < 6) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void signOut() {
        mAuth.signOut();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new LoginFragment(), "LoginFragment")
                .addToBackStack(null)
                .commit();
    }
}
