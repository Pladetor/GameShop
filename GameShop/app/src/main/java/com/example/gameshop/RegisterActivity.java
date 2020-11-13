package com.example.gameshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button registerButton;
    private EditText InputEmail, InputPassword;
    private ProgressDialog bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Others
        bar = new ProgressDialog(this);

        // Inputs
        InputEmail = (EditText) findViewById(R.id.emailFill);
        InputPassword = (EditText) findViewById(R.id.passwordFill);

        // Buttons
        registerButton = (Button) findViewById(R.id.submitButton);

        // On-click listeners
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register()
    {
        String email = InputEmail.getText().toString();
        String password = InputPassword.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
        {
            Toast.makeText(RegisterActivity.this, "Some info is missing!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            bar.setTitle("Registration");
            bar.setMessage("Please wait while we create your account");
            bar.setCanceledOnTouchOutside(false);
            bar.show();

            checkIfEmailExists(email, password);
        }
    }

    private void checkIfEmailExists(String email, String password)
    {
        final DatabaseReference db;
        db = FirebaseDatabase.getInstance().getReference();

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("Users").child(email).exists()))
                {
                    // Adding user to the database
                    HashMap<String, Object> userDataMap = new HashMap<>();

                    userDataMap.put("email", email);
                    userDataMap.put("password", password);

                    db.child("Users").child(email).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(RegisterActivity.this, "Successfully registered!", Toast.LENGTH_SHORT).show();
                                bar.dismiss();

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this, "An unknown error has occured, please try again later.", Toast.LENGTH_SHORT).show();
                                bar.dismiss();

                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "User with this email already exists!", Toast.LENGTH_SHORT).show();
                    bar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try using another email.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}