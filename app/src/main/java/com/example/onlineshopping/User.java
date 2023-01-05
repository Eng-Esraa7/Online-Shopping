package com.example.onlineshopping;

public class User {
    String fristName,LastName,Email,Password,Gender,Birthday,Job;
    String user_id="0";
    public User(String user_id,String fristName,String LastName,String Email,String Password,String Gender,String Birthday,String Job){
      this.user_id=user_id;
      this.fristName=fristName;
      this.LastName=LastName;
      this.Email=Email;
      this.Password=Password;
      this.Gender=Gender;
      this.Birthday=Birthday;
      this.Job=Job;
    }
    public User(String fristName,String LastName,String Email,String Gender,String Birthday,String Job,String user_id){
        this.fristName=fristName;
        this.LastName=LastName;
        this.Email=Email;
        this.Password=Password;
        this.Gender=Gender;
        this.Birthday=Birthday;
        this.Job=Job;
        this.user_id=user_id;
    }
    public User(){}

    public String getUser_id() {
        return user_id;
    }

    public String getFristName() {
        return fristName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getGender() {
        return Gender;
    }

    public String getBirthday() {
        return Birthday;
    }

    public String getJob() {
        return Job;
    }
}
