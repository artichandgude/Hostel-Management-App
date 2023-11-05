package com.abstractit.hostelmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText uname, pwd;
    Button btnLoginRector;
    TextView txtLoginstudent;

    FirebaseDatabase fdatabse = FirebaseDatabase.getInstance();
    DatabaseReference reference = fdatabse.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uname = findViewById(R.id.editUsename);
        pwd = findViewById(R.id.editPassword);
        txtLoginstudent = findViewById(R.id.txtLoginstudent);
        btnLoginRector = findViewById(R.id.btnLogin);

        btnLoginRector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginuser();
            }
        });

        txtLoginstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,StudentLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loginuser() {

        String name = uname.getText().toString();
        String pass = pwd.getText().toString();

        if (name.length() == 0) {
            uname.setError("Please Enter Uname");
            uname.requestFocus();
            return;
        }

        if (pass.length() == 0) {
            pwd.setError("Please Enter Password");
            pwd.requestFocus();
            return;
        }

        Query query = reference.child("Rector");
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            boolean isValidUser = false;
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String key = snapshot.getKey();
                    if (key.equals("password")) {
                        String pass = snapshot.getValue(String.class);

                        if (pwd.getText().toString().equals(pass)) {
                            isValidUser = true;
                        }
                    } else if (key.equals("username")) {
                        String user = snapshot.getValue(String.class);
                        if (uname.getText().toString().equals(user)) {
                            isValidUser = true;
                        }
                    }
                    if (isValidUser) {
                        Toast.makeText(MainActivity.this, "loging in", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this,WelcomeActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
