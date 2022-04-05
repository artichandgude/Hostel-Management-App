package com.abstractit.hostelmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AttendanceListAdapter extends RecyclerView.Adapter<AttendanceListAdapter.AttendanceViewHolder> {

    private Context context;
    private List<Attendance> aatendancelist;



    public AttendanceListAdapter(Context context, List<Attendance> attendanceList) {
        this.context = context;
        this.aatendancelist = attendanceList;
    }


    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.custom_attendance, parent, false);
        return new AttendanceViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceListAdapter.AttendanceViewHolder holder, int position) {

        final Attendance attendance = aatendancelist.get(position);

        holder.txtAttendacneStatus.setText(attendance.getAttendaceStatus());
        holder.txtAttendacneDate.setText(attendance.getAttendaceDate());


    }

    @Override
    public int getItemCount() {
        return aatendancelist.size();
    }

    public class AttendanceViewHolder extends RecyclerView.ViewHolder {
        TextView txtAttendacneDate, txtAttendacneStatus;


        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);

            txtAttendacneDate=(TextView)itemView.findViewById(R.id.txtAttendanceDate);
            txtAttendacneStatus=(TextView)itemView.findViewById(R.id.txtAttendanceStatus);
        }
    }
}
