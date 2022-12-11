package model;

import com.google.gson.Gson;
import db.Connect;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cashier extends Employee implements ICashier {

    int transaction_handled;
    Statement st = null;
    ResultSet rs = null;

    public Cashier() {
    }

    public Cashier(String name, String username, String password, int age, float salary, int years_experienced, int transaction_handled) {
        super(name, username, password, age, salary, years_experienced);
        this.transaction_handled = transaction_handled;
    }

    public Cashier(int id_employee, String name, String username, String password, int age, float salary, int years_experienced, int transaction_handled) {
        super(id_employee, name, username, password, age, salary, years_experienced);
        this.transaction_handled = transaction_handled;
    }

//    @Override
//    public boolean login(Connection db, String username, String password) {
//        try {
//            st = db.createStatement();
//            String sql = String.format("SELECT COUNT(*) FROM employee e JOIN cashier m ON e.id_employee = m.id_employee WHERE username = '%s' AND password = '%s'", username, password);
//            if (st.execute(sql)) {
//                rs = st.getResultSet();
//                return rs.next() && rs.getInt(1) > 0;
//            } else {
//                return false;
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
//            return false;
//        }
//    }
    public Cashier login(Connection db, String username, String password) {
        try {
            st = db.createStatement();
            String sql = String.format("SELECT * FROM employee e JOIN cashier m ON e.id_employee = m.id_employee WHERE username = '%s' AND password = '%s'", username, password);
            st.execute(sql);
            rs = st.getResultSet();
            while (rs.next()) {
                setId_employee(rs.getInt(1));
                setName(rs.getString("name"));
                setUsername(rs.getString("username"));
                setPassword(rs.getString("password"));
                setAge(rs.getInt("age"));
                setSalary(rs.getFloat("salary"));
                setYears_experienced(rs.getInt("years_experienced"));
                setTransaction_handled(rs.getInt("transaction_handled"));
            }
            return this;
        } catch (SQLException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int getTransaction_handled() {
        return transaction_handled;
    }

    public void setTransaction_handled(int transaction_handled) {
        this.transaction_handled = transaction_handled;
    }

    public String getAll(Connection db) throws SQLException {
        st = db.createStatement();
        String sql = "SELECT * FROM employee";
        List<Cashier> c = new ArrayList<>();
        st.execute(sql);
        rs = st.getResultSet();
        while (rs.next()) {
            c.add(new Cashier(rs.getInt("id_employee"), rs.getString("name"), rs.getString("username"), rs.getString("password"), rs.getInt("age"), rs.getFloat("salary"), rs.getInt("years_experienced"), rs.getInt("transaction_handled")));
        }
        
        String jsonString = new Gson().toJson(c);
        System.out.println(jsonString);
        return jsonString;
    }

    @Override
    public void logout() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isAdult() {
        return super.age > 18;
    }

    @Override
    public boolean isLocalResident() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nAge: %d\nSalary: %f\nExperience: %d years\nTransaction Handled: %d times", name, age, salary, years_experienced, transaction_handled);
    }

}
