package com.example.intern_food;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Signup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText Name,Email,Password,MobNo,RollNo,Branch,Hostel,RoomNo;
    String name,email,password,mobNo,rollNo,branch,hostel,roomNo;
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    StorageReference profilePicRef;
    private ImageView profilePic;
    DatabaseReference userRef= db.getReference().child("User");
    DatabaseReference test=FirebaseDatabase.getInstance().getReference();
    private Uri imageUri;
    private String saveCurrentDate;
    private String saveCurrentTime;
    private String key;
    private String downloadImageUri;
    //DatabaseReference loginRef= FirebaseDatabase.getInstance().getReference().child("UserLoginDetails");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth= FirebaseAuth.getInstance();
        Name=findViewById(R.id.name);
        Email=findViewById(R.id.email);
        Password=findViewById(R.id.password);
        MobNo=findViewById(R.id.phone_num);
        RollNo=findViewById(R.id.rno);
        Branch=findViewById(R.id.branch);
        Hostel=findViewById(R.id.hosteln);
        RoomNo=findViewById(R.id.room_no);
        profilePicRef= FirebaseStorage.getInstance().getReference().child("userPicture");
        profilePic=findViewById(R.id.profile_pic);
        // test.child("hi").setValue("hi");
        // test.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.child("hi").exists())
//                {
//                    Toast.makeText(Signup.this, "Hi", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(Signup.this, "bye", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

    }

    private void openGallery() {
        Intent tGallery=new Intent();
        tGallery.setAction(Intent.ACTION_GET_CONTENT);
        tGallery.setType("image/*");
        startActivityForResult(tGallery,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK && data!=null)
        {
            assert data != null;
            imageUri=data.getData();
            profilePic.setImageURI(imageUri);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void signUp(View view) {
        if (imageUri == null) {

            Toast.makeText(this, "You need to add your Picture", Toast.LENGTH_SHORT).show();
        } else {
            name = Name.getText().toString();
            email = Email.getText().toString();
            password = Password.getText().toString();
            mobNo = MobNo.getText().toString();
            rollNo = RollNo.getText().toString();
            branch = Branch.getText().toString();
            hostel = Hostel.getText().toString();
            roomNo = RoomNo.getText().toString();



            if (!(email.isEmpty() || password.isEmpty())) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Signup.this, "Sucessfully Signed Up", Toast.LENGTH_SHORT).show();
                                    createPicKey();
                                    //startActivity(new Intent(Signup.this, Dashboard.class));
                                } else
                                    Toast.makeText(Signup.this, "Error Signing Up" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        });

            } else
                Toast.makeText(this, "Invalid Fields", Toast.LENGTH_SHORT).show();


        }
    }

    private void createPicKey() {
        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,YYYY");
        saveCurrentDate =currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        key=saveCurrentDate + saveCurrentTime;

        final StorageReference filePath= profilePicRef.child(imageUri.getLastPathSegment()+key+".jpg");
        final UploadTask uploadTask= filePath.putFile(imageUri);
        filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        downloadImageUri=uri.toString().trim();
                        Toast.makeText(Signup.this,downloadImageUri, Toast.LENGTH_SHORT).show();
                        addUserDatatoDB();
                    }
                });

            }
        });

    }

    private void addUserDatatoDB() {
        final HashMap<String,Object> userData=new HashMap<>();
        userData.put("name",name);
        userData.put("idLogin",email);
        userData.put("Password",password);
        userData.put("phoneNo",mobNo);
        userData.put("idUserId",mAuth.getUid());
        userData.put("rollNo",rollNo);
        userData.put("branch",branch);
        userData.put("hostel",hostel);
        userData.put("roomNo",rollNo);
        userData.put("imageuri",downloadImageUri);

        userRef.child(mAuth.getUid()).updateChildren(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    startActivity(new Intent(Signup.this,Dashboard.class));
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Signup.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });







    }

}
