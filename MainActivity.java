package com.example.aashna.mynotes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

Button loginSign;
    private FirebaseAuth mAuth;
EditText email, password;//emailTxt, passwordTxt
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        loginSign=findViewById(R.id.loginSignupBtn);
        email=findViewById(R.id.emailTxt);
        password=findViewById(R.id.passwordTxt);
        loginSign.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
                }
        });

        }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);
    }

    public void saveDetailsBtn(View view) {
        if(TextUtils.isEmpty(email.getText().toString()) && TextUtils.isEmpty(password.getText().toString())){
            Toast.makeText(MainActivity.this, "email and password cannot be empty", Toast.LENGTH_SHORT).show();
                    }
        else{
            //create and delete file here...
            Toast.makeText(MainActivity.this, "your credentials are successfully saved...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,Main2Activity.class));// starting next activity
        }
    }

    public void loginBtn(View view) {
        String mailID=email.getText().toString().trim();
        String passwrd=password.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(mailID,passwrd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

            }
        });
    }
}
