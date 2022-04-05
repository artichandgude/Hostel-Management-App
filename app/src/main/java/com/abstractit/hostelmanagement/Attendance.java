package com.abstractit.hostelmanagement;

public class Attendance {

    private String studentNodeId;
    private String attendaceStatus;
    private String attendaceDate;
    private String attendDate_nodeid;
    private String attendDate_studentYear;
    private String studentNodeid_MonthYear;


    public String getAttendDate_studentYear() {
        return attendDate_studentYear;
    }

    public void setAttendDate_studentYear(String attendDate_studentYear) {
        this.attendDate_studentYear = attendDate_studentYear;
    }

    public String getAttendDate_nodeid() {
        return attendDate_nodeid;
    }

    public void setAttendDate_nodeid(String attendDate_nodeid) {
        this.attendDate_nodeid = attendDate_nodeid;
    }

    public String getStudentNodeId() {
        return studentNodeId;
    }

    public void setStudentNodeId(String studentNodeId) {
        this.studentNodeId = studentNodeId;
    }

    public String getAttendaceStatus() {
        return attendaceStatus;
    }

    public void setAttendaceStatus(String attendaceStatus) {
        this.attendaceStatus = attendaceStatus;
    }

    public String getAttendaceDate() {
        return attendaceDate;
    }

    public void setAttendaceDate(String attendaceDate) {
        this.attendaceDate = attendaceDate;
    }

    public String getStudentNodeid_MonthYear() {
        return studentNodeid_MonthYear;
    }

    public void setStudentNodeid_MonthYear(String studentNodeid_MonthYear) {
        this.studentNodeid_MonthYear = studentNodeid_MonthYear;
    }
}
