package com.abstractit.hostelmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentregisterActivity extends AppCompatActivity {


    EditText edtStudentname,edtStudentemail,edtStudentphone,edtStudentaddress,edtStudentbranch,edtStudentdepartment,edtPassword;

    Spinner spinStudentacadmicyear;

    String selectedYear;

    Button btnRegisterstudent;
    String userId;


    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentregister);

        edtStudentname=findViewById(R.id.editStudentname);
        edtStudentemail=findViewById(R.id.editStudentEmail);
        edtStudentphone=findViewById(R.id.editStudentPhone);
        edtStudentaddress=findViewById(R.id.editStudentAddress);
        edtStudentbranch=findViewById(R.id.editStudentBranch);
        edtStudentdepartment=findViewById(R.id.editStudentDepartment);
        spinStudentacadmicyear=findViewById(R.id.spinAcadmicyear);
        edtPassword=findViewById(R.id.editStudentPassword);

        btnRegisterstudent=findViewById(R.id.btnRegisterStudent);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        String[] ACADMIC_YEAR = {Academicyear.Year.toString(),
                Academicyear.FIRST_YEAR.toString(), Academicyear.SECOND_YEAR.toString(), Academicyear.THIRD_YEAR.toString(),
                Academicyear.FOURTH_YEAR.toString(), Academicyear.FIFTH_YEAR.toString(), Academicyear.SIXTH_YEAR.toString(),
        };

        ArrayAdapter<String> arrayAdapter_shiftagefrom = new ArrayAdapter<String>(this, R.layout.spinner_item, ACADMIC_YEAR);

        spinStudentacadmicyear.setAdapter(arrayAdapter_shiftagefrom);


        spinStudentacadmicyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedYear = spinStudentacadmicyear.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
//        userId=currentFirebaseUser.getUid();



        btnRegisterstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String studentname= edtStudentname.getText().toString();
                String studentemail =edtStudentemail.getText().toString();
                String studentphone= edtStudentphone.getText().toString();
                String studentaddress= edtStudentaddress.getText().toString();
                String studentdepartment= edtStudentdepartment.getText().toString();
                String password = edtPassword.getText().toString();

            //    String studentacadmicyear= edtStudentacadmicyear.getText().toString();
                String studentbranch= edtStudentbranch.getText().toString();

                Studentdata studentdata = new Studentdata();
                studentdata.setStudentName(studentname);
                studentdata.setStudentEmail(studentemail);
                studentdata.setStudentPhone(studentphone);
                studentdata.setStudentAddress(studentaddress);
                studentdata.setStudentBranch(studentbranch);
                studentdata.setStudentDepartment(studentdepartment);
                studentdata.setStudentAcadmicyear(selectedYear);
                studentdata.setStudentPassword(password);



                mDatabase = FirebaseDatabase.getInstance().getReference();
                //               mDatabase.child("users").child(userId).child("Pool Order").push().setValue(addpoolRideModel);
                mDatabase.child("Student").push().setValue(studentdata);



                Intent intent = new Intent(StudentregisterActivity.this,WelcomeActivity.class);
                startActivity(intent);
                finish();


            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back= new Intent(StudentregisterActivity.this,WelcomeActivity.class);
        startActivity(back);
        finish();
    }
}
