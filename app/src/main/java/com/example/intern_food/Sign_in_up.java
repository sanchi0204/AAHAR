package com.example.intern_food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Sign_in_up extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_up);
    }
    public void tSignIn(View view)
    {
        startActivity(new Intent(Sign_in_up.this,Login.class));
    }
    public void tSignUp(View view)
    {
        startActivity(new Intent(Sign_in_up.this,Signup.class));
    }
}
