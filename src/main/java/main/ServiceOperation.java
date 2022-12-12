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
import model.Item;
import model.Manager;
import model.Transaction;

public class ServiceOperation {

    static ResultSet rs;
    static Statement st;
    static Connection db = Connect.getConnection();

    public static Employee login(String username, String password) throws SQLException {
        st = db.createStatement();
        String managerSql = String.format("SELECT * FROM employee e JOIN manager c ON e.id_employee = c.id_employee WHERE username = '%s' AND password = '%s' LIMIT 1", username, password);
        rs = st.executeQuery(managerSql);
        Employee result = null;
        while (rs.next()) {
            int id_employee = rs.getInt("id_employee");
            int age = rs.getInt("age");
            int years_experienced = rs.getInt("years_experienced");
            String name = rs.getString("name");
            float salary = rs.getFloat("Salary");
            String role_title = rs.getString("role_title");
            String jsonString = new Gson().toJson(new Manager(id_employee, name, username, password, age, salary, years_experienced, role_title));
            System.out.println(jsonString);
            result = new Manager(id_employee, name, username, password, age, salary, years_experienced, role_title);
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
            result = new Cashier(id_employee, name, username, password, age, salary, years_experienced, transaction_handled);
        }
        return result;
    }

    public static boolean addCashier(int id_employee, String name, String username, String password, int age, float salary, int years_experienced)  {
        try {
            st = db.createStatement();
            String sql = String.format("INSERT INTO employee (id_employee, name, username, password, age, salary, years_experienced) VALUES (%d, '%s', '%s', '%s', %d, %f, %d)", id_employee, name, username, password, age, salary, years_experienced);
            st.executeUpdate(sql);
            sql = String.format("INSERT INTO cashier (id_employee, transaction_handled) VALUES(%d, %d)", id_employee, 0);
            st.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }

    public static boolean removeCashier(int id_employee) {
        try {
            st = db.createStatement();
            String sql = "SELECT * FROM transaction t JOIN employee e ON t.id_employee = e.id_employee WHERE t.id_employee = " + id_employee;
            st.execute(sql);
            rs = st.getResultSet();
            while (rs.next()) {
                System.out.println(rs.getInt("id_employee"));
                sql = String.format("DELETE FROM transaction WHERE id_employee = ", rs.getInt("id_employee"));
                st.executeUpdate(sql);
            }
            sql = "DELETE FROM cashier WHERE id_employee = " + id_employee;
            st.executeUpdate(sql);
            sql = "DELETE FROM employee WHERE id_employee = " + id_employee;
            st.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }

    public static int getLastIdEmployee() throws SQLException {
        int id = 0;
        st = db.createStatement();
        String sql = "SELECT id_employee FROM employee ORDER BY id_employee ASC";
        st.execute(sql);
        rs = st.getResultSet();
        while (rs.next()) {
            id = rs.getInt(1);
        }
        return id;
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

    public static String getAllTransactions() throws SQLException {
        st = db.createStatement();
        String sql = "SELECT * FROM transaction t JOIN detail_transaction dt ON t.id_transaction = dt.id_transaction JOIN employee e ON t.id_employee = e.id_employee GROUP BY t.id_transaction";
        List<Transaction> c = new ArrayList<>();
        st.execute(sql);
        rs = st.getResultSet();
        while (rs.next()) {
            c.add(new Transaction(rs.getInt("id_transaction"), rs.getString("name"), rs.getString("customer_name"), rs.getString("transaction_date"), rs.getFloat("total")));
        }
        String jsonString = new Gson().toJson(c);
        return jsonString;
    }

    public static int getLastIdTransaction() throws SQLException {
        int id = 0;
        st = db.createStatement();
        String sql = "SELECT id_transaction FROM transaction ORDER BY id_transaction ASC";
        st.execute(sql);
        rs = st.getResultSet();
        while (rs.next()) {
            id = rs.getInt(1);
        }
        return id;
    }

    public static Boolean addTransaction(int id_transaction, int id_employee, String customer_name, float total) {
        try {
            st = db.createStatement();
            String transactionSql = String.format("INSERT INTO transaction (id_transaction, id_employee, customer_name, total) VALUES(%d, %d, '%s', %f)", id_transaction, id_employee, customer_name, total);
            st.executeUpdate(transactionSql);
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }

    public static Boolean addDetailTransaction(int id_transaction, int id_item, int quantity, float subtotal) {
        try {
            st = db.createStatement();
            System.out.println(id_transaction);
            System.out.println(id_item);
            System.out.println(quantity);
            System.out.println(subtotal);
            String detailTransactionSql = String.format("INSERT INTO detail_transaction (id_transaction, id_item, quantity, subtotal) VALUES (%d, %d, %d, %f)", id_transaction, id_item, quantity, subtotal);
            System.out.println(detailTransactionSql);
            st.executeUpdate(detailTransactionSql);
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }

    public static String getAllItems() throws SQLException {
        st = db.createStatement();
        String sql = "SELECT i.*, c.title category FROM item i JOIN category c ON i.id_category = c.id_category GROUP BY i.id_item";
        List<Item> c = new ArrayList<>();
        st.execute(sql);
        rs = st.getResultSet();
        while (rs.next()) {
            c.add(new Item(rs.getInt("id_item"), rs.getString("category"), rs.getString("title"), rs.getString("description"), rs.getFloat("price"), rs.getInt("in_stock")));
        }
        String jsonString = new Gson().toJson(c);
        return jsonString;
    }

    public static void main(Connection db) throws SQLException {
    }
}
