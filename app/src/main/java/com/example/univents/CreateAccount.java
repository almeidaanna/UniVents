package com.example.univents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.univents.databinding.ActivityCreateAccountBinding;
import com.example.univents.model.Student;
import com.example.univents.viewmodel.StudentViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class CreateAccount extends AppCompatActivity {
    private ActivityCreateAccountBinding binding;
    private String userFName;
    private String userLName;
    private String userPhone;
    private String userEmail;
    private String userPassword;
    private String userConfirmPassword;
    private String userUniversity;
    private FirebaseAuth mAuth;
    private StudentViewModel studentViewModel;

    private static final String TAG = "CreateAccount";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialise Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // Customize the back button
        actionBar.setHomeAsUpIndicator(R.drawable.backbtn);

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

                binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        List<String> list = new ArrayList<String>();
        list.add("Select University");
        list.add("Monash University");
        list.add("RMIT");
        list.add("La Trobe University");
        list.add("University of Melbourne");

        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this ,android.R.layout.simple_spinner_item, list);
        binding.universitySpinner.setAdapter(spinnerAdapter);
        binding.universitySpinner.setPrompt("Select University");

        binding.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.firstNameId.setText("");
                binding.lastNameId.setText("");
                binding.phoneId.setText("");
                binding.emailId.setText("");
                binding.passwordId.setText("");
                binding.confirmPasswordId.setText("");
                binding.universitySpinner.setSelection(0);
            }
        });

        binding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userFName = binding.firstNameId.getText().toString().trim();
                userLName = binding.lastNameId.getText().toString().trim();
                userPhone = binding.phoneId.getText().toString().trim();
                userEmail = binding.emailId.getText().toString().trim();
                userPassword = binding.passwordId.getText().toString().trim();
                userConfirmPassword = binding.passwordId.getText().toString().trim();
                userUniversity = binding.universitySpinner.getSelectedItem().toString();
                if(userFName.isEmpty()|| isStringNumeric(userFName) )
                    Toast.makeText(view.getContext(),"Incorrect Name", Toast.LENGTH_SHORT).show();
                else if(userLName.isEmpty()|| isStringNumeric(userLName) )
                    Toast.makeText(view.getContext(),"Incorrect Name", Toast.LENGTH_SHORT).show();
                else if(userPhone.isEmpty() || !isStringNumeric(userPhone)|| userPhone.length() < 10 || userPhone.length() > 10)
                    Toast.makeText(view.getContext(),"Incorrect Phone number", Toast.LENGTH_SHORT).show();
                else if (!userEmail.contains(".edu") || !userEmail.contains("@")|| !userEmail.contains("student")|| userEmail.isEmpty())
                    Toast.makeText(view.getContext(),"Invalid EmailID", Toast.LENGTH_SHORT).show();
                else if(userPassword.isEmpty()||userPassword.length()<8||!userPassword.matches(".*[0-9].*"))
                    Toast.makeText(view.getContext(),"Invalid Password", Toast.LENGTH_SHORT).show();
                else if(!userConfirmPassword.equals(userPassword))
                    Toast.makeText(view.getContext(),"Invalid Password", Toast.LENGTH_SHORT).show();
                else if(userUniversity.equals("Select University"))
                    Toast.makeText(view.getContext(),"Invalid University", Toast.LENGTH_SHORT).show();
                else {
                    Student student = new Student(userFName, userLName, userPhone, userEmail, userPassword, userUniversity);
                    createAccount(student);
                }
            }
        });
    }

    private void createAccount(Student student){
        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    Toast.makeText(CreateAccount.this, "Log In Successful",
                            Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    studentViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(StudentViewModel.class);
                    studentViewModel.insert(student);
                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(CreateAccount.this, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        else {
            //
        }
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

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}