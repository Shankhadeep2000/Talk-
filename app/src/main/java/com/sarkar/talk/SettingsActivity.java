package com.sarkar.talk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sarkar.talk.Models.Users;
import com.sarkar.talk.databinding.ActivitySettingsBinding;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {


    ActivitySettingsBinding binding;


    FirebaseAuth auth;
    FirebaseDatabase database;

    FirebaseStorage storage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        getSupportActionBar().hide();

        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this , MainActivity.class);
                startActivity(intent);
            }
        });

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = binding.etStatus.getText().toString();
                String username = binding.etUserName.getText().toString();

                HashMap<String , Object > obj = new HashMap<>();
                obj.put("userName" , username);
                obj.put("status" , status);

                database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                        .updateChildren(obj);

                Toast.makeText(SettingsActivity.this, "profile Updated", Toast.LENGTH_SHORT).show();

            }
        });



        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        Users users = snapshot.getValue(Users.class);
                        Picasso.get().load(users.getProfilepic()).placeholder(R.drawable.user)
                                .into(binding.profileImage);

                        binding.etStatus.setText(users.getStatus());
                        binding.etUserName.setText(users.getUserName());


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent , 33);
            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);


        if(data.getData() !=null){

            Uri sFile = data.getData();
            binding.profileImage.setImageURI(sFile);

            final StorageReference reference = storage.getReference().child("profile_picture")
                    .child(FirebaseAuth.getInstance().getUid());
            reference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {


                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                    .child("profilepic").setValue(uri.toString());
                            Toast.makeText(SettingsActivity.this, "Profile Picture Updated", Toast.LENGTH_SHORT).show();

                        }
                    });


                }
            });


        }


    }
}