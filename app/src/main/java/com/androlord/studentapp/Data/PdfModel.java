package com.androlord.studentapp.Data;

import java.util.Date;

public class PdfModel {
    public String desc;
    public String imageurl;
    public String course;
    Date timestamp;
    String author;

    public PdfModel(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public PdfModel(){

    }


    public PdfModel(String desc, String imageurl, String course, Date timestamp) {
        this.desc = desc;
        this.imageurl = imageurl;
        this.course = course;
        this.timestamp = timestamp;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
