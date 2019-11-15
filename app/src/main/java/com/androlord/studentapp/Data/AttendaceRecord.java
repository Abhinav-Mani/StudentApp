package com.androlord.studentapp.Data;

public class AttendaceRecord {
    public String coursecode;
    public long present,total;
    public AttendaceRecord(String coursecode,int present,int total)
    {
        this.coursecode=coursecode;
        this.total=total;
        this.present=present;
    }
}
