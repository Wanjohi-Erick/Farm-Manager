package com.example.farmmanager.models;

public class EmployeesModel {
    String employeeID, employeeName, employeeContact, dateOfEmployment;
    public EmployeesModel(String employeeID, String employeeName, String employeeContact, String dateOfEmployment) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.employeeContact = employeeContact;
        this.dateOfEmployment = dateOfEmployment;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeContact() {
        return employeeContact;
    }

    public String getDateOfEmployment() {
        return dateOfEmployment;
    }
}
