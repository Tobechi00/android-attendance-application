package com.assignment.attendance;

public class ModelValues {
    private String firstname;
    private String lastname;
    private String department;
    private String id;
    private String date;
    private String time;

    public ModelValues(String firstname,String lastname,String department,String id,String date,String time){
        this.firstname = firstname;
        this.lastname = lastname;
        this.department = department;
        this.id = id;
        this.date = date;
        this.time = time;

    }
    public String getFirstname(){
        return firstname;
    }
    public String getLastname(){
        return lastname;
    }
    public String getDepartment(){
        return department;
    }
    public String getId(){
        return id;
    }
    public String getDate(){
        return date;
    }
    public String getTime(){
        return time;
    }
}
