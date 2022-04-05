package com.abstractit.hostelmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class StudentLoginActivity extends AppCompatActivity {


    EditText edtStudentusername,edtStudentpassword;
    Button btnStudentLogin;

    FirebaseDatabase fdatabse = FirebaseDatabase.getInstance();
    DatabaseReference reference = fdatabse.getReference();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentlogin);

        edtStudentusername=findViewById(R.id.edtStudentLoginUsername);
        edtStudentpassword=findViewById(R.id.edtStudentLoginPassword);

        btnStudentLogin=findViewById(R.id.btnStudentLogin);


        btnStudentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginStudent();
            }
        });


    }

    private void loginStudent() {

        String name = edtStudentusername.getText().toString();
        String pass = edtStudentpassword.getText().toString();

        if (name.length() == 0) {
            edtStudentusername.setError("Please Enter Username");
            edtStudentusername.requestFocus();
            return;
        }

        if (pass.length() == 0) {
            edtStudentpassword.setError("Please Enter Password");
            edtStudentpassword.requestFocus();
            return;
        }

        loginVerify();

    }

    private void loginVerify() {

        FirebaseDatabase.getInstance().getReference().child("Student").orderByChild("studentEmail").equalTo(edtStudentusername.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                boolean isValidUser = false;

                for (DataSnapshot children : dataSnapshot.getChildren()) {
                    Studentdata mediaItem = children.getValue(Studentdata.class);
                    String rideID = children.getKey();


                    Studentdata studentdata = new Studentdata();
                    String username = mediaItem.getStudentEmail();
                    String pass = mediaItem.getStudentPassword();

                    if (username.equals(edtStudentusername.getText().toString())&&pass.equals(edtStudentpassword.getText().toString())) {
                        isValidUser = true;

                    }


                    if (isValidUser) {

                        Intent intent = new Intent(StudentLoginActivity.this,ActivityStudentAttendance.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("KEY",rideID);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(StudentLoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(StudentLoginActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

