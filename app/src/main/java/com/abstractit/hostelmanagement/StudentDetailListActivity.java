package com.abstractit.hostelmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StudentDetailListActivity extends AppCompatActivity {

    RecyclerView recyclerStudentList;
    Spinner spinSearchstudent;
    String searchyear;

    String temp = "";

    StringBuffer sb = null;

    private DatabaseReference mDatabase;
    Button btnSubmit;


    ArrayList<Attendance> attendanceList;

    private ArrayList<Studentdata> studentdataArrayList;
    private StudentDeatilsListAdapter adapter;
    private FirebaseAuth mAuth;
    Attendance attendance = new Attendance();

    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentlist);



        recyclerStudentList = findViewById(R.id.recyclerStudentlist);

        spinSearchstudent = findViewById(R.id.spinSerachStudent);

        btnSubmit = findViewById(R.id.btnSubmit);

        studentdataArrayList = new ArrayList<>();
        attendanceList = new ArrayList<>();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c);

                SimpleDateFormat monthYear = new SimpleDateFormat("MMM-yyyy");
                String yearMonth = monthYear.format(c);

                if (studentdataArrayList.size() > 0) {


                    for (int i = 0; i < studentdataArrayList.size(); i++) {

                        View v = recyclerStudentList.getChildAt(i);


                        TextView name = v.findViewById(R.id.txtStudentName);
                        TextView studentnodeid = v.findViewById(R.id.txtNodeid);
                        TextView studentacadmicyear = v.findViewById(R.id.txtStudentYear);
                        String status = "";
                        String sname = name.getText().toString();
                        String stnoid = studentnodeid.getText().toString();
                        String studentstudyyear = studentacadmicyear.getText().toString();
                        CheckBox chk = v.findViewById(R.id.checkbox);

                        if (chk.isChecked()) {
                            status = "Present";
                        } else {
                            status = "Absent";
                        }


                        attendance.setAttendaceStatus(status);
                        attendance.setStudentNodeId(stnoid);
                        attendance.setAttendaceDate(formattedDate);
                        attendance.setAttendDate_nodeid(stnoid + "_" + formattedDate);
                        attendance.setAttendDate_studentYear(formattedDate+"_"+studentstudyyear);
                        attendance.setStudentNodeid_MonthYear(stnoid+"_"+yearMonth);

                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Attendance");
                        mDatabase.push().setValue(attendance);

                    }


                    Intent intent = new Intent(StudentDetailListActivity.this,WelcomeActivity.class);
                    startActivity(intent);
                    finish();

                }


            }
        });

        adapter = new StudentDeatilsListAdapter(getApplicationContext(), studentdataArrayList);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerStudentList.setHasFixedSize(true);
        recyclerStudentList.setLayoutManager(linearLayoutManager);
        recyclerStudentList.setAdapter(adapter);


        String[] ACADMIC_YEAR = {Academicyear.Year.toString(),
                Academicyear.FIRST_YEAR.toString(), Academicyear.SECOND_YEAR.toString(), Academicyear.THIRD_YEAR.toString(),
                Academicyear.FOURTH_YEAR.toString(), Academicyear.FIFTH_YEAR.toString(), Academicyear.SIXTH_YEAR.toString(),
        };

        ArrayAdapter<String> arrayAdapter_shiftagefrom = new ArrayAdapter<String>(this, R.layout.spinner_item, ACADMIC_YEAR);

        spinSearchstudent.setAdapter(arrayAdapter_shiftagefrom);


        spinSearchstudent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                searchyear = spinSearchstudent.getSelectedItem().toString();
                studentdataArrayList.clear();
                Query query = FirebaseDatabase.getInstance().getReference().child("Student").orderByChild("studentAcadmicyear").equalTo(searchyear);
                query.addListenerForSingleValueEvent(valueEventListener);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mAuth = FirebaseAuth.getInstance();


    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            for (DataSnapshot children : dataSnapshot.getChildren()) {
                Studentdata mediaItem = children.getValue(Studentdata.class);
                String rideID = children.getKey();


                Studentdata studentdata = new Studentdata();

                studentdata.setStudentName(mediaItem.getStudentName());
                studentdata.setStudentEmail(mediaItem.getStudentEmail());
                studentdata.setStudentPhone(mediaItem.getStudentPhone());
                studentdata.setStudentBranch(mediaItem.getStudentBranch());
                studentdata.setStudentAddress(mediaItem.getStudentAddress());
                studentdata.setStudentAcadmicyear(mediaItem.getStudentAcadmicyear());
                studentdata.setStudentDepartment(mediaItem.getStudentDepartment());
                studentdata.setStudeNodeid(rideID);

                studentdataArrayList.add(studentdata);

            }

            adapter.notifyDataSetChanged();

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(StudentDetailListActivity.this, WelcomeActivity.class);
        back.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(back);
        finish();
    }
}

