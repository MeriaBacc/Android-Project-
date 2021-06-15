package com.example.project;


public class User {

    public String fullname, age, email ;
    public double totalebilan , fond_roulement , besoin_roulement ;

    public User(){

    }

    public User(String fullname, String age, String email){
        this.fullname = fullname;
        this.age = age;
        this.email = email;

    }

    public  User(double totalebilan , double fond_roulement , double besoin_roulement){

        this.totalebilan = totalebilan ;
        this.fond_roulement = fond_roulement;
        this.besoin_roulement = besoin_roulement;
    }


}
