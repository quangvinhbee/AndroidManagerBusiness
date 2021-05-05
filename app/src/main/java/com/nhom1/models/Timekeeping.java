package com.nhom1.models;

public class Timekeeping {
    private String date,idEmployee,_id,startAt,endAt;

    public Timekeeping() {
    }

    public Timekeeping(String date, String idEmployee, String _id, String startAt, String endAt) {
        this.date = date;
        this.idEmployee = idEmployee;
        this._id = _id;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public Timekeeping(String date, String idEmployee, String _id) {
        this.date = date;
        this.idEmployee = idEmployee;
        this._id = _id;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(String idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
