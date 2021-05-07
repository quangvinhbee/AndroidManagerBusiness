package com.nhom1.models;

import android.net.Uri;

import java.io.Serializable;

public class Employee implements Serializable {
    private String _id,name,position,_idDepartment,avatar,name_department,createAt;
    private Uri avatarUri;
    private int lateWork,workontime;

    public int getLateWork() {
        return lateWork;
    }

    public void setLateWork(int lateWork) {
        this.lateWork = lateWork;
    }

    public int getWorkontime() {
        return workontime;
    }

    public void setWorkontime(int workontime) {
        this.workontime = workontime;
    }

    public Employee(String _id, String name, String position, String _idDepartment, String avatar, String name_department, String createAt, Uri avatarUri, int lateWork, int workontime, int workdays, int salary) {
        this._id = _id;
        this.name = name;
        this.position = position;
        this._idDepartment = _idDepartment;
        this.avatar = avatar;
        this.name_department = name_department;
        this.createAt = createAt;
        this.avatarUri = avatarUri;
        this.lateWork = lateWork;
        this.workontime = workontime;
        this.workdays = workdays;
        this.salary = salary;
    }

    private int workdays;
    private int salary;

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public Employee(String _id, String name, String position, String _idDepartment, String avatar, String name_department, String createAt, Uri avatarUri, int workdays, int salary) {
        this._id = _id;
        this.name = name;
        this.position = position;
        this._idDepartment = _idDepartment;
        this.avatar = avatar;
        this.name_department = name_department;
        this.createAt = createAt;
        this.avatarUri = avatarUri;
        this.workdays = workdays;
        this.salary = salary;
    }

    public String getName_department() {
        return name_department;
    }

    public Employee(String _id, String name, String position, String _idDepartment, String avatar, String name_department, Uri avatarUri, int workdays, int salary) {
        this._id = _id;
        this.name = name;
        this.position = position;
        this._idDepartment = _idDepartment;
        this.avatar = avatar;
        this.name_department = name_department;
        this.avatarUri = avatarUri;
        this.workdays = workdays;
        this.salary = salary;
    }

    public void setName_department(String name_department) {
        this.name_department = name_department;
    }



    public Employee(String _id, String name, String _idDepartment, String avatar, String createAt, int workdays, int salary) {
        this._id = _id;
        this.name = name;
        this._idDepartment = _idDepartment;
        this.avatar = avatar;
        this.workdays = workdays;
        this.createAt = createAt;
        this.salary = salary;
    }

    public Employee() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String get_idDepartment() {
        return _idDepartment;
    }

    public void set_idDepartment(String _idDepartment) {
        this._idDepartment = _idDepartment;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Uri getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(Uri avatarUri) {
        this.avatarUri = avatarUri;
    }

    public int getWorkdays() {
        return workdays;
    }

    public void setWorkdays(int workdays) {
        this.workdays = workdays;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
