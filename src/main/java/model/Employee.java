package model;

import java.sql.Connection;

public abstract class Employee {

    int id_employee, age, years_experienced;
    String name, username, password;
    float salary;

    public Employee() {

    }

    public Employee(String name, String username, String password, int age, float salary, int years_experienced) { // intended for creating data
        this.age = age;
        this.years_experienced = years_experienced;
        this.name = name;
        this.username = username;
        this.password = password;
        this.salary = salary;
    }

    public Employee(int id_employee, String name, String username, String password, int age, float salary, int years_experienced) { // intended for retrieving data
        this.id_employee = id_employee;
        this.age = age;
        this.years_experienced = years_experienced;
        this.name = name;
        this.username = username;
        this.password = password;
        this.salary = salary;
    }

    public abstract Employee login(Connection db, String username, String password);

    public abstract void logout();

    public int getId_employee() {
        return id_employee;
    }

    public void setId_employee(int id_employee) {
        this.id_employee = id_employee;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getYears_experienced() {
        return years_experienced;
    }

    public void setYears_experienced(int years_experienced) {
        this.years_experienced = years_experienced;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

}
