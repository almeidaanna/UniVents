package com.example.univents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.univents.databinding.ActivityLogInBinding;

public class LogInActivity extends AppCompatActivity {
    private ActivityLogInBinding binding;
    private String userName;
    private String userPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
                startActivity(intent);
            }
        });
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = binding.emailId.getText().toString().trim();
                userPassword = binding.password.getText().toString().trim();
                if (!userName.endsWith(".student.edu") || !userName.contains("@") || userName.isEmpty())
                    Toast.makeText(view.getContext(),"Invalid EmailID", Toast.LENGTH_SHORT).show();
                else if(userPassword.isEmpty()||userPassword.length()<8)
                    Toast.makeText(view.getContext(),"Invalid Password", Toast.LENGTH_SHORT).show();
                else
                {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //add functionality to exit the app
        Toast.makeText(this, "Exit App", Toast.LENGTH_SHORT).show();
    }
}