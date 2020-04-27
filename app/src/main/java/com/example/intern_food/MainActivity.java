package com.example.intern_food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT =5000;
    private FirebaseAuth mAuth;
    ImageView logo;
//    TextView app_name, tag_line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo=findViewById(R.id.logo);
//        app_name=findViewById(R.id.text);
//        tag_line= findViewById(R.id.text_tag);

        RelativeLayout linearLayout = findViewById(R.id.main_layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser!=null)
                {
                    Intent intent=new Intent(MainActivity.this,Dashboard.class);
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, logo, ViewCompat.getTransitionName(logo));
                    intent.putExtra("UserId",mAuth.getUid());
                    startActivity(intent, options.toBundle());
                    // startActivity(new Intent(MainActivity.this,Dashboard.class));
                }
                else
                {
                    startActivity(new Intent(MainActivity.this,Sign_in_up.class));
                }
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        },SPLASH_TIME_OUT);
        mAuth = FirebaseAuth.getInstance();
        //reference.setValue("Hello");
    }

    @Override
    protected void onStart() {
        super.onStart();

        // updateUI(currentUser);
    }
}
