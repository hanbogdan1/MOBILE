package com.example.bogdan.aplicatiemobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import Domain.User;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference Users;

    EditText editUserName, editEmail, editPassword;
    Button btnregister, btnlogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        Users = database.getReference("Users");

        editUserName = (EditText) findViewById(R.id.usernameText);
        editEmail =  (EditText) findViewById(R.id.emailText);
        editPassword= (EditText) findViewById(R.id.passwordText);

        btnregister = (Button) findViewById(R.id.registerBtn);
        btnlogin    = (Button) findViewById(R.id.goToLoginBtn);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(s);
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editEmail.getText().toString().trim().isEmpty() || editUserName.getText().toString().trim().isEmpty()
                        || editPassword.getText().toString().trim().isEmpty()  )
                    Toast.makeText(MainActivity.this, "Please fill all fields ! ", Toast.LENGTH_LONG).show();
                else
                {
                    final User usr = new User(editUserName.getText().toString().trim(),editPassword.getText().toString().trim(),
                            editEmail.getText().toString().trim());

                    Users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(usr.getUsername()).exists()) {
                                Toast.makeText(MainActivity.this, "User already exists ! ", Toast.LENGTH_LONG).show();
                            }
                            else
                                Users.child(usr.getUsername()).setValue(usr);
                                Toast.makeText(MainActivity.this, "Succes register ! ", Toast.LENGTH_LONG).show();
                                editEmail.setText("");
                                editPassword.setText("");
                                editUserName.setText("");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(MainActivity.this, "Error! ", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        editEmail.setText("");
        editPassword.setText("");
        editUserName.setText("");
    }
}
