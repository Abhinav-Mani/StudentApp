package com.androlord.studentapp.Data;

import java.util.ArrayList;

public class SubjectMarks {
    public ArrayList<String> examnames;
    public ArrayList<Long> examscores;
    public SubjectMarks(ArrayList<String> examname,ArrayList<Long> examscores){
        this.examnames=examname;
        this.examscores=examscores;
    }
}
