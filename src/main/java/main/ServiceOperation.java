package main;

import com.google.gson.Gson;
import db.Connect;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Cashier;
import model.Employee;
import model.Manager;

public class ServiceOperation {

    static ResultSet rs;
    static Statement st;
    static Connection db = Connect.getConnection();

    public static Employee login(String username, String password) throws SQLException {
        db = Connect.getConnection();
        st = db.createStatement();
        String managerSql = String.format("SELECT * FROM employee e JOIN manager c ON e.id_employee = c.id_employee WHERE username = '%s' AND password = '%s' LIMIT 1", username, password);
        rs = st.executeQuery(managerSql);
        System.out.println(rs);
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
        String cashierSql = String.format("SELECT * FROM employee e JOIN cashier c ON e.id_employee = c.id_employee WHERE username = '%s' AND password = '%s' LIMIT 1", username, password);
        rs = st.executeQuery(cashierSql);
        while (rs.next()) {
            int id_employee = rs.getInt("id_employee");
            int age = rs.getInt("age");
            int years_experienced = rs.getInt("years_experienced");
            String name = rs.getString("name");
            float salary = rs.getFloat("Salary");
            int transaction_handled = rs.getInt("transaction_handled");
            String jsonString = new Gson().toJson(new Cashier(id_employee, name, username, password, age, salary, years_experienced, transaction_handled));
            System.out.println(jsonString);
            return new Cashier(id_employee, name, username, password, age, salary, years_experienced, transaction_handled);
        }
        return null;
    }

    public static String getAllCashiers() throws SQLException {
        st = db.createStatement();
        String sql = "SELECT * FROM employee e JOIN cashier c ON e.id_employee = c.id_employee";
        List<Cashier> c = new ArrayList<>();
        st.execute(sql);
        rs = st.getResultSet();
        while (rs.next()) {
            c.add(new Cashier(rs.getInt("id_employee"), rs.getString("name"), rs.getString("username"), rs.getString("password"), rs.getInt("age"), rs.getFloat("salary"), rs.getInt("years_experienced"), rs.getInt("transaction_handled")));
        }
        String jsonString = new Gson().toJson(c);
        return jsonString;
    }

    public static void main(Connection db) throws SQLException {
    }
}
