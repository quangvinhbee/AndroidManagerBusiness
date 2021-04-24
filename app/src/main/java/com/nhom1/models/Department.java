package com.nhom1.models;

import java.io.Serializable;

public class Department implements Serializable {
    private String _id,name,avatar;
    private int count_employee;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Department(String _id, String name, String avatar, int count_employee) {
        this._id = _id;
        this.name = name;
        this.avatar = avatar;
        this.count_employee = count_employee;
    }

    public Department() {
    }



    public Department(String _id, String name, int count_employee) {
        this._id = _id;
        this.name = name;
        this.count_employee = count_employee;
    }

    @Override
    public String toString() {
        return "Department{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", count_employee=" + count_employee +
                '}';
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

    public int getCount_employee() {
        return count_employee;
    }

    public void setCount_employee(int count_employee) {
        this.count_employee = count_employee;
    }
}
