package com.pvhn.studentsmanager.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ClassModel extends RealmObject {
    @PrimaryKey
    String classID;
    String className;

    public ClassModel() {
    }

    public ClassModel(String classID, String className) {
        this.classID = classID;
        this.className = className;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
