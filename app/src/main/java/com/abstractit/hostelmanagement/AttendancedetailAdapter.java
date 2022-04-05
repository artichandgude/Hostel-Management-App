package com.abstractit.hostelmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import static android.view.View.GONE;

public class AttendancedetailAdapter extends RecyclerView.Adapter<AttendancedetailAdapter.AttendaceDetailViewHolder> {

    private Context context;
    private List<Studentdata> attendancedeatailList;



    public AttendancedetailAdapter(Context context,List<Studentdata>allattendacelist) {
        this.context=context;
        this.attendancedeatailList=allattendacelist;

    }

    @NonNull
    @Override
    public AttendancedetailAdapter.AttendaceDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.customattendacedeatail, parent, false);
        return new AttendancedetailAdapter.AttendaceDetailViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendancedetailAdapter.AttendaceDetailViewHolder holder, int position) {
        final Studentdata allAttendancedetails = attendancedeatailList.get(position);

        holder.txtStudentAttendacename.setText(allAttendancedetails.getStudentName());
        holder.txtStudentBranch.setText(allAttendancedetails.getStudentBranch());
        holder.txtStudentAcadmicyear.setText(allAttendancedetails.getStudentAcadmicyear());
        holder.txtstudentAttendacedate.setText(allAttendancedetails.getStudentDate());
        holder.txtStudentDepartment.setText(allAttendancedetails.getStudentDepartment());
        holder.txtStudentAttendnodeid.setText(allAttendancedetails.getStudeNodeid());
        holder.checkeBoxAttendnace.setChecked(allAttendancedetails.isSelected);
    }

    @Override
    public int getItemCount() {
        return attendancedeatailList.size();
    }

    public class AttendaceDetailViewHolder extends RecyclerView.ViewHolder {

        TextView txtStudentAttendacename,txtStudentBranch,txtStudentAcadmicyear,txtstudentAttendacedate,txtStudentDepartment,txtStudentAttendnodeid;

        CheckBox checkeBoxAttendnace;

        public AttendaceDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            txtStudentAttendacename=itemView.findViewById(R.id.txtStudentNameattendacedetail);
            txtStudentBranch=itemView.findViewById(R.id.txtStudentBatchattendacedetail);
            txtStudentAcadmicyear=itemView.findViewById(R.id.txtStudentYearattendacedetail);
            txtstudentAttendacedate=itemView.findViewById(R.id.txtStudentAttendanceDatedetail);
            txtStudentDepartment=itemView.findViewById(R.id.txtStudentDepartmentattendacedetail);
            txtStudentAttendnodeid=itemView.findViewById(R.id.txtStudentNodeid);
            txtStudentAttendnodeid.setVisibility(GONE);
            checkeBoxAttendnace=itemView.findViewById(R.id.checkboxattendacedetail);

        }
    }
}
