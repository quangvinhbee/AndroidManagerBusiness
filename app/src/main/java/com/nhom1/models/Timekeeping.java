package com.nhom1.models;

public class Timekeeping {
    private String startAt,endAt,idEmployee;

    public Timekeeping() {
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

    public String getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(String idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Timekeeping(String startAt, String endAt, String idEmployee) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.idEmployee = idEmployee;
    }
}
