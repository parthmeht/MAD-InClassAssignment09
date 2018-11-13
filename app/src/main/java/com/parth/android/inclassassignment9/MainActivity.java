package com.parth.android.inclassassignment9;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginFragmentListener, SignUpFragment.SignUpListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new LoginFragment(), "LoginFragment")
                .commit();


    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount()>0){
            getSupportFragmentManager().popBackStack();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void goToSignUp() {
        setTitle("Select Avatar");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new SignUpFragment(), "SignUpFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToLogin() {
        setTitle("Select Avatar");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new LoginFragment(), "LoginFragment")
                .addToBackStack(null)
                .commit();
    }
}
