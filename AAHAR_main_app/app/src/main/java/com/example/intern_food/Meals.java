package com.example.intern_food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class Meals extends AppCompatActivity {
    Button submit;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    CheckBox BreakFast,Lunch,Snacks,Dinner;
    Boolean isBrkfst,isLnch,isSnck,isDnnr;
    HashMap<String,Object>mealMap=new HashMap<>();
    DatabaseReference mealref= FirebaseDatabase.getInstance().getReference().child("MealSelected");
    String formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c);

        BreakFast=findViewById(R.id.check_breakfast);
        Lunch=findViewById(R.id.check_lunch);
        Snacks=findViewById(R.id.check_snacks);
        Dinner =findViewById(R.id.check_dinner);

        submit= findViewById(R.id.submit_meals);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnSubmit();
            }
        });
    }

    public void OnSubmit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Meals.this);
        builder.setMessage("Are you sure ?");
        builder.setTitle("Alert");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                             @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {
                                mealref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.child(mAuth.getUid()).child(formattedDate).exists())
                                        {
                                            Toast.makeText(Meals.this, "Already Selected", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Meals.this,Dashboard.class));
                                        }
                                        else
                                        {
                                            addDataToHashMap();
                                            Intent i = new Intent(Meals.this, Load.class);
                                            startActivity(i);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        });


        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // If user click no
                                // then dialog box is canceled.
                                dialog.cancel();
                            }
                        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void addDataToHashMap() {
        int i=1;

        if(BreakFast.isChecked())
        {
          mealMap.put("name","Bread Butter");
          mealMap.put("time","10:30");
          mealref.child(mAuth.getUid()).child(formattedDate).child("Meal"+i).updateChildren(mealMap);
          i++;
        }

        if(Lunch.isChecked())
        {
            mealMap.put("name","Dal Roti Rice");
            mealMap.put("time","2:00");
            mealref.child(mAuth.getUid()).child(formattedDate).child("Meal"+i).updateChildren(mealMap);
            i++;
        }
        if(Snacks.isChecked())
        {
            mealMap.put("name","Burger");
            mealMap.put("time","5:00");
            mealref.child(mAuth.getUid()).child(formattedDate).child("Meal"+i).updateChildren(mealMap);
            i++;
        }
        if(Dinner.isChecked())
        {
            mealMap.put("name","Paneer Sabzi,Dal");
            mealMap.put("time","8:00");
            mealref.child(mAuth.getUid()).child(formattedDate).child("Meal"+i).updateChildren(mealMap);
        }
        //addDataToDatabase();


    }

    private void addDataToDatabase() {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c);

        mealref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(mAuth.getUid()).child(formattedDate).exists())
                {
                    Toast.makeText(Meals.this, "Already Selected ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mealref.child(mAuth.getUid()).child(formattedDate).updateChildren(mealMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Meals.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
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
