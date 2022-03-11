package com.example.farmmanager.models;

public class EmployeesModel {
    String employeeID, employeeFirstName, employeeLastName, employeeContact, dateOfEmployment;
    public EmployeesModel(String employeeID, String employeeFirstName, String employeeLastName, String employeeContact, String dateOfEmployment) {
        this.employeeID = employeeID;
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.employeeContact = employeeContact;
        this.dateOfEmployment = dateOfEmployment;
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
}
