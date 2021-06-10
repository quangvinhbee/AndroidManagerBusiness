package com.nhom1.constants;

public class Constants {
    //
    public static final  String EMPLOYEE_TABLE = "Employee_tb";
    public static final  String EMPLOYEE_ID = "Employee_ID";
    public static final  String EMPLOYEE_NAME = "Employee_NAME";
    public static final  String EMPLOYEE_AVATAR = "Employee_AVATAR";
    public static final  String EMPLOYEE_CREATE_AT = "Employee_CREATE_AT";
    public static final  String EMPLOYEE_SALARY = "Employee_SALARY";
    public static final  String EMPLOYEE_ID_DEPARMENT = "Employee_ID_DEPARTMENT";
    public static final String CREATE_TABLE_EMPLOYEE = "CREATE TABLE " + Constants.EMPLOYEE_TABLE + "("
            +Constants.EMPLOYEE_ID+ " TEXT PRIMARY KEY, "
            +Constants.EMPLOYEE_NAME+" TEXT NOT NULL, "
            +Constants.EMPLOYEE_ID_DEPARMENT+ " TEXT NOT NULL, "
            +Constants.EMPLOYEE_CREATE_AT+ " TEXT, "
            +Constants.EMPLOYEE_AVATAR+ " TEXT NOT NULL, "
            +Constants.EMPLOYEE_SALARY+ " INTEGER NOT NULL"
            +")";



    public static final  String DEPARMENT_TABLE = "Department_tb";
    public static final  String DEPARMENT_ID = "Department_ID";
    public static final  String DEPARMENT_NAME = "Department_NAME";
    public static final  String DEPARMENT_AVATAR = "Department_AVATAR";
    public static final String CREATE_TABLE_DEPARTMENT = "CREATE TABLE " + Constants.DEPARMENT_TABLE + "("
            +Constants.DEPARMENT_ID+ " TEXT PRIMARY KEY, "
            +Constants.DEPARMENT_NAME+" TEXT NOT NULL, "
            +Constants.DEPARMENT_AVATAR+ " TEXT NOT NULL"
            +")";


    public static final  String TIMEKEEPING_TABLE = "Timekeeping_tb";
    public static final  String TIMEKEEPING_ID = "Timekeeping_ID";
    public static final  String TIMEKEEPING_DATE = "Timekeeping_DATE";
    public static final  String TIMEKEEPING_STARTAT = "Timekeeping_STARTAT";
    public static final  String TIMEKEEPING_ENDAT = "Timekeeping_ENDAT";
    public static final  String TIMEKEEPING_ID_EMPLOYEE = "Timekeeping_ID_EMPLOYEE";
    public static final String CREATE_TABLE_TIMEKEEPING = "CREATE TABLE " + Constants.TIMEKEEPING_TABLE + "("
            +Constants.TIMEKEEPING_ID+ " TEXT PRIMARY KEY, "
            +Constants.TIMEKEEPING_DATE+" TEXT, "
            +Constants.TIMEKEEPING_STARTAT+" TEXT, "
            +Constants.TIMEKEEPING_ENDAT+" TEXT, "
            +Constants.TIMEKEEPING_ID_EMPLOYEE+ " TEXT NOT NULL"
            +")";


    public static final  String USER_TABLE = "User_tb";
    public static final  String USER_USERNAME = "User_USERNAME";
    public static final  String USER_PASSWORD = "User_PASSWORD";
    public static final String CREATE_TABLE_USER = "CREATE TABLE " + Constants.USER_TABLE + "("
            +Constants.USER_USERNAME+ " TEXT PRIMARY KEY, "
            +Constants.USER_PASSWORD+" TEXT, "
            +")";


}
