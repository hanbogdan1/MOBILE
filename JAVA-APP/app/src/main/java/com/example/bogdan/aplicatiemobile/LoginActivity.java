package com.example.bogdan.aplicatiemobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Domain.User;

public class LoginActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference Users;

    EditText loginEditUserName, loginEditPassword;
    Button  loginBtnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = FirebaseDatabase.getInstance();
        Users = database.getReference("Users");

        loginEditUserName = (EditText) findViewById(R.id.loginusernameText);
        loginEditPassword= (EditText) findViewById(R.id.loginpasswordText);

        loginBtnlogin    = (Button) findViewById(R.id.loginBtn);

        loginBtnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify_sing_in(loginEditUserName.getText().toString(), loginEditPassword.getText().toString() );
            }
        });
    }

    private void verify_sing_in(final String Username, final String Pass){
        if (Username.trim().isEmpty() || Pass.trim().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please fill all fields ! ", Toast.LENGTH_LONG).show();
            return;
        }

        Users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child(Username).exists()) {
                    Toast.makeText(LoginActivity.this, "User invalid! ", Toast.LENGTH_LONG).show();
                    loginEditUserName.setText("");
                    loginEditPassword.setText("");
                    return;
                }
                User urs = dataSnapshot.child(Username).getValue(User.class);

                if (!urs.getPassword().toString().equals(Pass)) {
                    Toast.makeText(LoginActivity.this, "User invalid! ", Toast.LENGTH_LONG).show();
                    loginEditUserName.setText("");
                    loginEditPassword.setText("");
                    return;
                }

                Intent in = new Intent( LoginActivity.this, AfterLoginActivity.class);
                in.putExtra("USER_NAME" , urs.getUsername());
                startActivity(in);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "Error on user login! ", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginEditUserName.setText("");
        loginEditPassword.setText("");
    }
}
