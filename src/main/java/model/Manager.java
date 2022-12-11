package model;

import com.google.gson.Gson;
import db.Connect;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Manager extends Employee implements IManager {

    String roleTitle;
    Statement st = null;
    ResultSet rs = null;

    public Manager() {
    }

    public Manager(int id_employee, String name, String username, String password, int age, float salary, int years_experienced, String roleTitle) {
        super(id_employee, name, username, password, age, salary, years_experienced);
        this.roleTitle = roleTitle;
    }

    @Override
    public Manager login(Connection db, String username, String password) {
        try {
            st = db.createStatement();
        String managerSql = String.format("SELECT * FROM employee e JOIN manager c ON e.id_employee = c.id_employee WHERE username = '%s' AND password = '%s' LIMIT 1", username, password);
        rs = st.executeQuery(managerSql);
        if (rs.next()) {
            while (rs.next()) {
                int id_employee = rs.getInt("id_employee");
                int age = rs.getInt("age");
                int years_experienced = rs.getInt("years_experienced");
                String name = rs.getString("name");
                float salary = rs.getFloat("Salary");
                String role_title = rs.getString("role_title");
                String jsonString = new Gson().toJson(new Manager(id_employee, name, username, password, age, salary, years_experienced, role_title));
                System.out.println(jsonString);
                return new Manager(id_employee, name, username, password, age, salary, years_experienced, role_title);
            }
        }
            return this;
        } catch (SQLException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void logout() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isExperienced() {
        return super.years_experienced > 20;
    }

    public void registerEmployee(Employee cashier) {

    }

    public String getRoleTitle() {
        return roleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        this.roleTitle = roleTitle;
    }
    
    public Manager getManager() {
        return this;
//        return String.format("Name: %s\nUsername: %s\nPassword: %d\nAge: %d", name, username, password, age);
    }

}
