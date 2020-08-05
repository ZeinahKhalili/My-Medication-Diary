package com.example.MyMedicationDiary;


import java.util.Date;

public class Reminders {

    public int id;

    String message1;
    String message2;
    Date remindDate;

    public String getMessage1() {
        return message1;
    }

    public String getMessage2() {
        return message2;
    }


    public Date getRemindDate() {
        return remindDate;
    }

    public int getId() {
        return id;
    }

    public void setMessage1(String message) {
        this.message1 = message;
    }

    public void setMessage2(String message) {
        this.message2 = message;
    }


    public void setRemindDate(Date remindDate) {
        this.remindDate = remindDate;
    }

    public void setId(int id) {
        this.id = id;
    }
}
