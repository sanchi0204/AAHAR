package com.example.intern_food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intern_food.Adapter.MealAdapter;
import com.example.intern_food.Model.Meal;
import com.example.intern_food.Model.UserClass;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextView textView,Roll,Branch,Hostel,Name,BranchM;
    DatabaseReference userref= FirebaseDatabase.getInstance().getReference("User");
    String username,rollno,branch,currentUserID;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    UserClass userClass;
    List<Meal> mealList;
    RecyclerView recyclerView;
    MealAdapter mealAdapter;
    String formattedDate;
    ImageView profilepic;
    ProgressDialog loadingbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Hostel=findViewById(R.id.hostel_name);
        loadingbar=new ProgressDialog(this);
        Name=findViewById(R.id.name);
        profilepic=findViewById(R.id.profile_pic1);
        BranchM=findViewById(R.id.branch);
        recyclerView=findViewById(R.id.recycler_food_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mealList=new ArrayList<>();
        DatabaseReference mealref=FirebaseDatabase.getInstance().getReference("MealSelected");
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c);

        mealref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.child(mAuth.getUid()).child(formattedDate).getChildren())
                {
                Meal meal=ds.getValue(Meal.class);
                mealList.add(meal);
                }
                mealAdapter=new MealAdapter(Dashboard.this,mealList);
                recyclerView.setAdapter(mealAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_dashboard);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        getIntentValue();
        loadingbar.setTitle("Loading Data");
        loadingbar.setMessage("Please Wait");
        loadingbar.setCanceledOnTouchOutside(false);
        loadingbar.show();
        fetchDataForHomePage();


    }

    private void fetchDataForHomePage() {
        userref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserClass userClass=dataSnapshot.child(mAuth.getUid()).getValue(UserClass.class);
//                username=dataSnapshot.child(id).child("name").getValue().toString();
                Name.setText(userClass.getName());
                Hostel.setText(userClass.getHostel());
                BranchM.setText(userClass.getBranch());
                Picasso.get().load(Uri.parse(userClass.getImageuri())).into(profilepic);
                loadingbar.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getIntentValue() {
        if(getIntent().hasExtra("UserId"))
        {
            currentUserID=getIntent().getStringExtra("UserId");
        }
    }

    public void fetchUserDetails() {
        userref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserClass userClass=dataSnapshot.child(mAuth.getUid()).getValue(UserClass.class);
                username=userClass.getName();
//                username=dataSnapshot.child(id).child("name").getValue().toString();
                textView.setText(username);

                Roll.setText(userClass.getRollNo());
                Branch.setText(userClass.getBranch());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Dashboard.this,Sign_in_up.class));
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notif_dash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.item1:
                Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


        @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        textView=findViewById(R.id.user_name);
        Roll=findViewById(R.id.user_rno);
        Branch=findViewById(R.id.user_branch);
        switch (item.getItemId()){

            case R.id.nav_dashboard:
                fetchUserDetails();
                break;
            case R.id.nav_logout:
                signOut();
                break;

            case R.id.nav_meals:   startActivity(new Intent(Dashboard.this, Meals.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;

            case R.id.nav_menu:    startActivity(new Intent(Dashboard.this, My_test.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;

            case R.id.nav_feedback:      startActivity(new Intent(Dashboard.this, Feedback.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;

            case R.id.nav_suggestions:      startActivity(new Intent(Dashboard.this, Suggestions.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
