package com.abstractit.hostelmanagement;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class ActivityAttendancebyDate extends AppCompatActivity {

    EditText edtDate;
    Button btnSearch;
    private ArrayList<Attendance> attendanceList;
    private ArrayList<Studentdata> studentList;
    ArrayList<Studentdata> filterData = new ArrayList<>();
    String temp_studentid;
    ArrayList<AllAttendancedetails> allAttendacedetail;
    RecyclerView recyclerAttendancedetail;
    AttendancedetailAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    DatePickerDialog picker;


    Button btnSubmit;
    Spinner spinAttendAcadmicYear;

    String selectedYear;

    int index = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendancebydate);

        edtDate = findViewById(R.id.edtDate);

        btnSearch = findViewById(R.id.btnSubmit);
        recyclerAttendancedetail = findViewById(R.id.recyclerAttendancedetail);
        spinAttendAcadmicYear = findViewById(R.id.spinAttendAcadmicyear);
        btnSubmit = findViewById(R.id.btnUpdate);


        attendanceList = new ArrayList<>();
        studentList = new ArrayList<>();

        allAttendacedetail = new ArrayList<>();
        adapter = new AttendancedetailAdapter(getApplicationContext(), studentList);


        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerAttendancedetail.setHasFixedSize(true);
        recyclerAttendancedetail.setLayoutManager(linearLayoutManager);
        recyclerAttendancedetail.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();


        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);


                // date picker dialog
                picker = new DatePickerDialog(ActivityAttendancebyDate.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                // Setting Min Date to today date
                                String month[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"};

                                edtDate.setText(dayOfMonth + "-" + month[monthOfYear] + "-" + year);


                            }
                        }, year, month, day);
                picker.show();
            }
        });


        //getStudentData();


        String[] ACADMIC_YEAR = {Academicyear.Year.toString(),
                Academicyear.FIRST_YEAR.toString(), Academicyear.SECOND_YEAR.toString(), Academicyear.THIRD_YEAR.toString(),
                Academicyear.FOURTH_YEAR.toString(), Academicyear.FIFTH_YEAR.toString(), Academicyear.SIXTH_YEAR.toString(),
        };

        ArrayAdapter<String> arrayAdapter_shiftagefrom = new ArrayAdapter<String>(this, R.layout.spinner_item, ACADMIC_YEAR);

        spinAttendAcadmicYear.setAdapter(arrayAdapter_shiftagefrom);


        spinAttendAcadmicYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedYear = spinAttendAcadmicYear.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                studentList.clear();
                attendanceList.clear();
                getStudentAttendacedetails();


            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String key = edtDate.getText().toString() + "_" + selectedYear;

                deletePreviousAttendance(key);
            }
        });

    }


    private void deletePreviousAttendance(String searchKey) {
        Query query = FirebaseDatabase.getInstance().getReference().child("Attendance").orderByChild("attendDate_studentYear").equalTo(searchKey);

        query.addListenerForSingleValueEvent(valueEventListenerDelete);


    }

    ValueEventListener valueEventListenerDelete = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String formattedDate = df.format(c);

            SimpleDateFormat monthYear = new SimpleDateFormat("MMM-yyyy");
            String yearMonth = monthYear.format(c);


            for (DataSnapshot children : dataSnapshot.getChildren()) {
                children.getRef().removeValue();
            }


            for (int i = 0; i < studentList.size(); i++) {
                View v = recyclerAttendancedetail.getChildAt(i);

                TextView name = v.findViewById(R.id.txtStudentNameattendacedetail);
                TextView studentnodeid = v.findViewById(R.id.txtStudentNodeid);
                TextView studentacadmicyear = v.findViewById(R.id.txtStudentYearattendacedetail);
                String status = "";
                String sname = name.getText().toString();
                String stnoid = studentnodeid.getText().toString();
                String studentstudyyear = studentacadmicyear.getText().toString();
                CheckBox chk = v.findViewById(R.id.checkboxattendacedetail);

                if (chk.isChecked()) {
                    status = "Present";
                } else {
                    status = "Absent";
                }
                Attendance attendance = new Attendance();

                attendance.setAttendaceStatus(status);
                attendance.setStudentNodeId(stnoid);
                attendance.setAttendaceDate(edtDate.getText().toString());
                attendance.setAttendDate_nodeid(stnoid + "_" + edtDate.getText().toString());
                attendance.setAttendDate_studentYear(edtDate.getText().toString() + "_" + studentstudyyear);
                attendance.setStudentNodeid_MonthYear(stnoid+"_"+yearMonth);

                mDatabase = FirebaseDatabase.getInstance().getReference().child("Attendance");
                mDatabase.push().setValue(attendance);
            }

            Intent intent = new Intent(ActivityAttendancebyDate.this, WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    private void getStudentData() {
        {

            FirebaseDatabase.getInstance().getReference().child("Student").orderByChild("studentAcadmicyear").equalTo(selectedYear).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    try {

                        for (DataSnapshot children : dataSnapshot.getChildren()) {
                            Studentdata mediaItem = children.getValue(Studentdata.class);
                            String userid = children.getKey();

                            Studentdata student = new Studentdata();

                            student.setStudeNodeid(userid);
                            student.setStudentName(mediaItem.getStudentName());
                            student.setStudentPhone(mediaItem.getStudentPhone());
                            student.setStudentAcadmicyear(mediaItem.getStudentAcadmicyear());
                            student.setStudentBranch(mediaItem.getStudentBranch());
                            student.setStudentDepartment(mediaItem.getStudentDepartment());
                            student.setStudentDate(mediaItem.getStudentDate());
                            studentList.add(student);
                        }
                        studentAttendancedetaillist();

                    } catch (Exception e) {

                        String error = e.getMessage();

                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
    }

    private void getStudentAttendacedetails() {

        {

            String date = edtDate.getText().toString();

            String date_selectyear = date + "_" + selectedYear;

            FirebaseDatabase.getInstance().getReference().child("Attendance").orderByChild("attendDate_studentYear").equalTo(date_selectyear).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for (DataSnapshot children : dataSnapshot.getChildren()) {
                        Attendance mediaItem = children.getValue(Attendance.class);
                        //  String rideID = children.getKey();
                        Attendance attendance = new Attendance();

                        attendance.setAttendaceDate(mediaItem.getAttendaceDate());
                        attendance.setAttendaceStatus(mediaItem.getAttendaceStatus());
                        attendance.setStudentNodeId(mediaItem.getStudentNodeId());

                        attendanceList.add(attendance);


                    }

                    getStudentData();
                    // studentAttendancedetaillist();


                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
    }

    private void studentAttendancedetaillist() {

        index = 0;
        filterData.clear();

        for (int i = 0; i < studentList.size(); i++) {

            for (int k = 0; k < attendanceList.size(); k++) {
                temp_studentid = attendanceList.get(k).getStudentNodeId();
                if (studentList.get(i).getStudeNodeid().equals(temp_studentid)) {

                    studentList.get(i).setStudentDate(attendanceList.get(i).getAttendaceDate());
                    studentList.get(i).setSelected(attendanceList.get(i).getAttendaceStatus().equals("Present") ? true : false);

                }
            }
        }

        adapter.notifyDataSetChanged();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ActivityAttendancebyDate.this, WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
