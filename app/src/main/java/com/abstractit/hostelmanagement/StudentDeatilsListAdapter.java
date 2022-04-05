package com.abstractit.hostelmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class StudentDeatilsListAdapter extends RecyclerView.Adapter<StudentDeatilsListAdapter.OnlineViewHolder> {

    ArrayList<Studentdata> lstChecked= new ArrayList<>();




    private Context context;
    private List<Studentdata> studentlist;
  //  private List<Studentdata> newattendList;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    Attendance attendance = new Attendance();
    String temp = "";
    String globeid;

    public StudentDeatilsListAdapter(Context context, List<Studentdata> studentlist) {
        this.context = context;
        this.studentlist = studentlist;
    }




    @Override
    public OnlineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_chatlist, parent, false);
        return new OnlineViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final OnlineViewHolder holder, final int position) {


        final Studentdata studentdata = studentlist.get(position);

        holder.txtstudentName.setText(studentdata.getStudentName());
        holder.txtstudentPhone.setText(studentdata.getStudentPhone());
        holder.txtstudentYear.setText(studentdata.getStudentAcadmicyear());
        holder.txtStudentDepartment.setText(studentdata.getStudentDepartment());
        holder.txtstudentNodeid.setText(studentdata.getStudeNodeid());

        holder.checkedBox.setChecked(studentdata.isSelected());

        holder.checkedBox.setTag(studentlist.get(position));

        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onItemClick(View view, int layoutPosition) {

                CheckBox chk= (CheckBox)view;
                if(chk.isChecked())
                {

                    lstChecked.add(studentlist.get(position));
                }
                else  if(!chk.isChecked())
                {

                    lstChecked.remove(studentlist.get(position));
                }


            }
        });





//        holder.checkedBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//
//
//
//            }
//        });


    }


    @Override
    public int getItemCount() {
        return studentlist.size();
    }


    public class OnlineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemClickListner itemClickListner;

        TextView txtstudentName, txtstudentEmail, txtstudentPhone, txtstudentbranch, txtstudentYear, txtStudentDepartment, txtstudentNodeid;

        CheckBox checkedBox;

        public void setItemClickListner(ItemClickListner itemClickListner)
        {
            this.itemClickListner=itemClickListner;
        }

        public OnlineViewHolder(View itemView) {
            super(itemView);
//            txtStudentDepartment

            txtstudentName = (TextView) itemView.findViewById(R.id.txtStudentName);
            //  txtstudentEmail = (TextView) itemView.findViewById(R.id.txtStudentEmail);
            txtstudentPhone = (TextView) itemView.findViewById(R.id.txtStudentPhone);
            //txtstudentbranch = (TextView) itemView.findViewById(R.id.txtStudentBranch);
            txtstudentYear = (TextView) itemView.findViewById(R.id.txtStudentYear);
            txtStudentDepartment = (TextView) itemView.findViewById(R.id.txtStudentDepartment);
            //txtstudentAddress = (TextView) itemView.findViewById(R.id.txtStudentAddress);

            txtstudentNodeid=(TextView)itemView.findViewById(R.id.txtNodeid);
            txtstudentNodeid.setVisibility(View.GONE);

            checkedBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            checkedBox.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            this.itemClickListner.onItemClick(view,getLayoutPosition());
        }
    }

}
