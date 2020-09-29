package com.pvhn.studentsmanager.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class StudentModel extends RealmObject {
    @PrimaryKey
    String studentID;
    String classID;
    String studentName;
    String studentAge;
    String studentAddress;
    String studentSex;

    public StudentModel() {
    }

    public StudentModel(String studentID, String classID, String studentName, String studentAge, String studentAddress, String studentSex) {
        this.studentID = studentID;
        this.classID = classID;
        this.studentName = studentName;
        this.studentAge = studentAge;
        this.studentAddress = studentAddress;
        this.studentSex = studentSex;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(String studentAge) {
        this.studentAge = studentAge;
    }

    public String getStudentAddress() {
        return studentAddress;
    }

    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }

    public String getStudentSex() {
        return studentSex;
    }

    public void setStudentSex(String studentSex) {
        this.studentSex = studentSex;
    }
}
