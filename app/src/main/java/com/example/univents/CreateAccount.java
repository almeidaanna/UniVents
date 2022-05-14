package com.example.univents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.univents.databinding.ActivityCreateAccountBinding;
import com.example.univents.databinding.ActivityLogInBinding;

import java.util.Locale;

public class CreateAccount extends AppCompatActivity {
    private ActivityCreateAccountBinding binding;
    private String userFName;
    private String userLName;
    private String userPhone;
    private String userName;
    private String userPassword;
    private String userConfirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userFName = binding.firstNameId.getText().toString().trim().toLowerCase();
                userLName = binding.lastNameId.getText().toString().trim().toLowerCase();
                userPhone = binding.phoneId.getText().toString().trim();
                userName = binding.emailId.getText().toString().trim();
                userPassword = binding.passwordId.getText().toString().trim();
                userConfirmPassword = binding.passwordId.getText().toString().trim();
                if(userFName.isEmpty()|| isStringNumeric(userFName) )
                    Toast.makeText(view.getContext(),"Incorrect Name", Toast.LENGTH_SHORT).show();
                if(userLName.isEmpty()|| isStringNumeric(userLName) )
                    Toast.makeText(view.getContext(),"Incorrect Name", Toast.LENGTH_SHORT).show();
                if(userPhone.isEmpty() || !isStringNumeric(userPhone)|| userPhone.length() < 10 || userPhone.length() > 10)
                    Toast.makeText(view.getContext(),"Incorrect Phone number", Toast.LENGTH_SHORT).show();
                if (!userName.endsWith(".student.edu") || !userName.contains("@") || userName.isEmpty())
                    Toast.makeText(view.getContext(),"Invalid EmailID", Toast.LENGTH_SHORT).show();
                if(userPassword.isEmpty()||userPassword.length()<8)
                    Toast.makeText(view.getContext(),"Invalid Password", Toast.LENGTH_SHORT).show();
                if(!userConfirmPassword.equals(userPassword))
                    Toast.makeText(view.getContext(),"Invalid Password", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    public static boolean isStringNumeric(String word)
    {
        boolean flag = true;
        for (int i = 0; i < (word.length()); i++)
        {
            flag = Character.isDigit(word.charAt(i));
            if (flag == false)
                return false;
        }
        return true;
    }
}