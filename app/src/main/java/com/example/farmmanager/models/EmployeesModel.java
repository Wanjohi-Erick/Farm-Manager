package com.example.farmmanager.models;

public class EmployeesModel {
    String employeeID, employeeFirstName, employeeLastName, employeeContact, dateOfEmployment, gender, role, salary;
    public EmployeesModel(String employeeID, String employeeFirstName, String employeeLastName, String employeeContact, String dateOfEmployment,
                          String gender, String role, String salary) {
        this.employeeID = employeeID;
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.employeeContact = employeeContact;
        this.dateOfEmployment = dateOfEmployment;
        this.gender = gender;
        this.role = role;
        this.salary = salary;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public String getEmployeeContact() {
        return employeeContact;
    }

    public String getDateOfEmployment() {
        return dateOfEmployment;
    }

    public String getGender() {
        return gender;
    }

    public String getRole() {
        return role;
    }

    public String getSalary() {
        return salary;
    }
}
