package com.sarkar.talk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.sarkar.talk.Adapters.FragmentsAdapter;
import com.sarkar.talk.databinding.ActivityMainBinding;
import com.sarkar.talk.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        binding.viewPager.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);











    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu , menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.settings:
                Intent i = new Intent(MainActivity.this , SettingsActivity.class);
                startActivity(i);
                break;
            case R.id.logout:
                auth.signOut();
                Intent intent = new Intent(MainActivity.this, signInActivity.class);
                startActivity(intent);
                break;
            case R.id.groupChat:
                Intent intent1 = new Intent(MainActivity.this, GroupChatActivity.class);
                startActivity(intent1);
        }
        return true;
    }
}



