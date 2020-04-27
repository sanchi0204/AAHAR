package com.example.intern_food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Feedback extends AppCompatActivity {

    CardView brkfast, lunch, snacks, dinner;
    FirebaseAuth mAuth;
    RadioGroup brgrp,lngrp,sngrp,dngrp;
    RadioButton radioButton1,radioButton2,radioButton3,radioButton4;
    DatabaseReference feedbackref= FirebaseDatabase.getInstance().getReference().child("feedback");
    private String formattedDate;
    FloatingActionButton fabFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        brgrp=findViewById(R.id.brGrp);
        lngrp=findViewById(R.id.lnGrp);
        sngrp=findViewById(R.id.sngrp);
        mAuth=FirebaseAuth.getInstance();
        dngrp=findViewById(R.id.dngrp);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c);

        fabFeedback= findViewById(R.id.fab_feedback);
        fabFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Feedback.this, Dashboard.class);
                startActivity(i);
            }
        });

        brkfast = findViewById(R.id.card_breakfast_item);
        lunch = findViewById(R.id.card_lunch_item);
        snacks = findViewById(R.id.card_snack_item);
        dinner = findViewById(R.id.card_dinner_item);




//        brkfast.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loadFragment(new FeedbackFragment());
//
//            }
//        });
//
//        lunch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                loadFragment(new FeedbackFragment());
//            }
//        });
//
//        snacks.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loadFragment(new FeedbackFragment());
//
//            }
//        });
//
//        dinner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loadFragment(new FeedbackFragment());
//
//            }
//        });

    }

//    private void loadFragment(Fragment fragment) {
//// create a FragmentManager
//        FragmentManager fm = getFragmentManager();
//// create a FragmentTransaction to begin the transaction and replace the Fragment
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//// replace the FrameLayout with new Fragment
//        fragmentTransaction.replace(R.id.feedback_fragment, fragment);
//        fragmentTransaction.commit(); // save the changes
//    }

    public void submitFeedback(View view)
    {
        int radioId1=brgrp.getCheckedRadioButtonId();
        int radioId2=lngrp.getCheckedRadioButtonId();
        int radioId3=sngrp.getCheckedRadioButtonId();
        int radioId4=dngrp.getCheckedRadioButtonId();

        radioButton1=findViewById(radioId1);
        radioButton2=findViewById(radioId2);
        radioButton3=findViewById(radioId3);
        radioButton4=findViewById(radioId4);

        final HashMap<String,Object> feedbackMap=new HashMap<>();

        feedbackMap.put("Bread Butter",radioButton1.getText());
        feedbackMap.put("Dal Roti Rice",radioButton2.getText());
        feedbackMap.put("Burger",radioButton3.getText());
        feedbackMap.put("Paneer Sabzi,Dal",radioButton4.getText());

        feedbackref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(formattedDate).child(mAuth.getUid()).child("FB").exists())
                {
                    Toast.makeText(Feedback.this, "Already Submitted Feedback for today", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    feedbackref.child(formattedDate).child(mAuth.getUid()).child("FB").updateChildren(feedbackMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Feedback.this, "Successfully Added Feedback", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
