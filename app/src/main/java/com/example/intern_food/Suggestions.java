package com.example.intern_food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class Suggestions extends AppCompatActivity {
    FloatingActionButton fabSuggestions;
    ImageView img_sad, img_nuetral, img_satis, img_happy;
    EditText title,suggestion;
    RadioButton radioButton;
    RadioGroup radioGroup;
    String overallExp;
    FirebaseAuth mAuth;
    String formattedDate;
    DatabaseReference suggestionRef= FirebaseDatabase.getInstance().getReference().child("Suggestions");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);
        title=findViewById(R.id.suggestion_title);
        suggestion=findViewById(R.id.suggestion);
        radioGroup=findViewById(R.id.radio_grp_issue_type);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c);
        mAuth=FirebaseAuth.getInstance();
        fabSuggestions= findViewById(R.id.fab_suggestions);
        fabSuggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Suggestions.this, Dashboard.class);
                startActivity(i);
            }
        });

        img_sad= findViewById(R.id.sad);
        img_nuetral= findViewById(R.id.nuetral);
        img_satis= findViewById(R.id.satisfactory);
        img_happy= findViewById(R.id.happy);

//        img_sad.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                img_sad.set
//            }
//        });
    }
    public void submitSuggestions(View view)
    {

        int radioId=radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(radioId);

        final HashMap<String,Object> suggestionMap=new HashMap<>();

        suggestionMap.put("Title",title.getText().toString());
        suggestionMap.put("Suggestion",suggestion.getText().toString());
        suggestionMap.put("IssueType",radioButton.getText());
        suggestionMap.put("OverallExperience",overallExp);

        suggestionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(formattedDate).child(mAuth.getUid()).exists())
                {
                    Toast.makeText(Suggestions.this, "Already submitted suggestion for today", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    suggestionRef.child(formattedDate).child(mAuth.getUid()).updateChildren(suggestionMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(Suggestions.this, "Suggestion Added", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    public void happy(View view)
    {
        overallExp="Happy";
        img_happy.setImageResource(R.drawable.mood_happy_pink);
        img_satis.setImageResource(R.drawable.mood_satisfy);
        img_nuetral.setImageResource(R.drawable.mood_nuetral);
        img_sad.setImageResource(R.drawable.mood_bad);
    }
    public void satis(View view)
    {
        overallExp="Satisfactory";
        img_happy.setImageResource(R.drawable.mood_happy);
        img_satis.setImageResource(R.drawable.mood_satisfy_pink);
        img_nuetral.setImageResource(R.drawable.mood_nuetral);
        img_sad.setImageResource(R.drawable.mood_bad);
    }
    public void neut(View view)
    {
        overallExp="Neutral";
        img_happy.setImageResource(R.drawable.mood_happy);
        img_satis.setImageResource(R.drawable.mood_satisfy);
        img_nuetral.setImageResource(R.drawable.mood_nuetral_pink);
        img_sad.setImageResource(R.drawable.mood_bad);
    }
    public void sad(View view)
    {
        overallExp="Sad";
        img_happy.setImageResource(R.drawable.mood_happy);
        img_satis.setImageResource(R.drawable.mood_satisfy);
        img_nuetral.setImageResource(R.drawable.mood_nuetral);
        img_sad.setImageResource(R.drawable.mood_bad_pink);
    }
}
