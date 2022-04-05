package com.abstractit.hostelmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.view.View.GONE;

public class ActivityStudentAttendance extends AppCompatActivity {

    TextView txtShowattendacne,txtNodata;
    RecyclerView recyclerStudentAttendance;
    private ArrayList<Attendance> attendanceList;
    private AttendanceListAdapter adapter;

    Spinner spinSearchMarch;

    private LinearLayoutManager linearLayoutManager;
    String Key_ID;
    String selectedMonth;
    String selectedNode_monthyear;
    String formattedYear;
    String tempKeyid;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentattendance);

        Intent intent =getIntent();
        Key_ID=intent.getStringExtra("KEY");

        tempKeyid=intent.getStringExtra("KEY");


        recyclerStudentAttendance = (RecyclerView) findViewById(R.id.studentAttendance);
        txtShowattendacne = findViewById(R.id.textView);
        txtNodata=findViewById(R.id.txtNodata);
        txtNodata.setVisibility(GONE);

        spinSearchMarch=findViewById(R.id.spinSearchMarch);

        attendanceList = new ArrayList<>();
        adapter = new AttendanceListAdapter(getApplicationContext(), attendanceList);


        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerStudentAttendance.setHasFixedSize(true);
        recyclerStudentAttendance.setLayoutManager(linearLayoutManager);
        recyclerStudentAttendance.setAdapter(adapter);


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        SimpleDateFormat df2 = new SimpleDateFormat("MMM");


        formattedYear= df.format(c);
        String formattedMonth = df2.format(c);

        Key_ID=Key_ID+"_"+formattedMonth+"-"+formattedYear;

        getStudentAttendacedetails(Key_ID);
        getStudentdetails();




        final String[] SELECTED_MONTH = {Academicyear.Month.toString(),
                Academicyear.Month2.toString(), Academicyear.Month3.toString(), Academicyear.Month4.toString(),
                Academicyear.Month5.toString(), Academicyear.Month6.toString(), Academicyear.Month7.toString(),
                Academicyear.Month8.toString(), Academicyear.Month9.toString(), Academicyear.Month10.toString(),
                Academicyear.Month11.toString(),Academicyear.Month12.toString()
        };



        ArrayAdapter<String> arrayAdapter_shiftagefrom = new ArrayAdapter<String>(this, R.layout.spinner_item, SELECTED_MONTH);
        spinSearchMarch.setAdapter(arrayAdapter_shiftagefrom);


        spinSearchMarch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                attendanceList.clear();
                selectedMonth  = spinSearchMarch.getSelectedItem().toString();

                selectedNode_monthyear=tempKeyid+"_"+selectedMonth+"-"+formattedYear;


                getStudentAttendacedetails(selectedNode_monthyear);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    private void getStudentAttendacedetails(String keypass) {

        FirebaseDatabase.getInstance().getReference().child("Attendance").orderByChild("studentNodeid_MonthYear").equalTo(keypass).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot children : dataSnapshot.getChildren()) {
                    Attendance mediaItem = children.getValue(Attendance.class);
                  //  String rideID = children.getKey();
                    Attendance attendance = new Attendance();

                    attendance.setAttendaceDate(mediaItem.getAttendaceDate());
                    attendance.setAttendaceStatus(mediaItem.getAttendaceStatus());

                    attendanceList.add(attendance);

                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void getStudentdetails() {


        DatabaseReference student = FirebaseDatabase.getInstance().getReference("Student");
        DatabaseReference childnode = student.child(tempKeyid);
        DatabaseReference subchild = childnode.child("studentName");

        subchild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String data = dataSnapshot.getValue(String.class);

                txtShowattendacne.setText(data);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ActivityStudentAttendance.this, StudentLoginActivity.class);
        startActivity(intent);
        finish();
    }
}



