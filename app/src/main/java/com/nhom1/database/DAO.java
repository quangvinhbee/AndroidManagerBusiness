package com.nhom1.database;

import com.nhom1.models.Department;
import com.nhom1.models.Employee;
import com.nhom1.models.Timekeeping;

import java.util.List;

public class DAO {
    public interface EmployeeQuery{
        void addEmployee(Employee employee,QueryResponse<Boolean> response);
        void readEmployee(QueryResponse<Employee> response);
        void readAllEmployee(String idDepartment,QueryResponse<List<Employee>> response);
        void updateEmployee(Employee employee,QueryResponse<Boolean> response);
        void deleteEmployee(Employee employee,QueryResponse<Boolean> response);
        void readAllEmployeeInDepartmet(String idDepartment,QueryResponse<List<Employee>> response);
        int countAllEmployeeInDepartmet(String idDepartment);
    }

    public interface DepartmentQuery{
        void addDepartment(Department department,QueryResponse<Boolean> response);
        String readDepartment(String id_department);
        void readAllDepartment(QueryResponse<List<Department>> response);
        void updateDepartment(Department department, QueryResponse<Boolean> response);
        void deleteDepartment(Department department,QueryResponse<Boolean> response);
    }
    public interface TimeKeepingQuery{
        void addCheckInTimeKeeping(String idEmployee,QueryResponse<Boolean> response);
        void isCheckIn(String idEmployee,QueryResponse<Boolean> response);
        void addCheckOutTimeKeeping(String idEmployee,QueryResponse<Boolean> response);
        void isCheckOut(String idEmployee,QueryResponse<Boolean> response);
        void getIdTimeKeepingToday(String idEmployee,QueryResponse<Timekeeping> response);
        void checkTimeKeepingForEmployee(String idEmployee,QueryResponse<Boolean> response);
        int readDateCurrentMonthOfEmployee(String idEmployee);
    }
}
