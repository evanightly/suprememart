package main;

import db.Connect;
import java.sql.Connection;
import model.Manager;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;
import static spark.Spark.*;

public class HokiMart {

    public static void main(String[] args) {
        Connection db = Connect.getConnection();
        port(2022);

        get("/", (req, res) -> {
            return "Hello World";
        });

        post("/employee", (req, res) -> {
            MultiMap bodyParser = new MultiMap();
            UrlEncoded.decodeTo(req.body(), bodyParser, "UTF-8");
            int id_employee = Integer.parseInt(bodyParser.getString("id_employee"));
            String name = bodyParser.getString("name");
            String username = bodyParser.getString("username");
            String password = bodyParser.getString("password");
            int age = Integer.parseInt(bodyParser.getString("age"));
            float salary = Float.parseFloat(bodyParser.getString("salary"));
            int years_experienced = Integer.parseInt(bodyParser.getString("years_experienced"));
            return ServiceOperation.addCashier(id_employee, name, username, password, age, salary, years_experienced);
        });

        get("/employee", (req, res) -> {
            return ServiceOperation.getAllCashiers();
        });

        get("/employee/last", (req, res) -> {
            return ServiceOperation.getLastIdEmployee();
        });

        post("/employee/:id", (req, res) -> {
            System.out.println("Executed");
            return ServiceOperation.removeCashier(Integer.parseInt(req.params("id")));
        });

        post("/transaction", (req, res) -> {
            MultiMap bodyParser = new MultiMap();
            UrlEncoded.decodeTo(req.body(), bodyParser, "UTF-8");
            int id_transaction = Integer.parseInt(bodyParser.getString("id_transaction"));
            int id_employee = Integer.parseInt(bodyParser.getString("id_employee"));
            String customer_name = bodyParser.getString("customer_name");
            float total = Float.parseFloat(bodyParser.getString("total"));
            return ServiceOperation.addTransaction(id_transaction, id_employee, customer_name, total);
        });

        get("/transaction", (req, res) -> {
            return ServiceOperation.getAllTransactions();
        });

        get("/transaction/last", (req, res) -> {
            return ServiceOperation.getLastIdTransaction();
        });

        post("/detail_transaction", (req, res) -> {
            MultiMap bodyParser = new MultiMap();
            UrlEncoded.decodeTo(req.body(), bodyParser, "UTF-8");
            int id_transaction = Integer.parseInt(bodyParser.getString("id_transaction"));
            int id_item = Integer.parseInt(bodyParser.getString("id_item"));
            int quantity = Integer.parseInt(bodyParser.getString("quantity"));
            float subtotal = Float.parseFloat(bodyParser.getString("subtotal"));
            return ServiceOperation.addDetailTransaction(id_transaction, id_item, quantity, subtotal);
        });

        get("/item", (req, res) -> {
            return ServiceOperation.getAllItems();
        });

//        get("/test", "application/json", (req, res) -> {
//            System.out.println(ServiceOperation.login());
//            return ServiceOperation.login();
//        }, new JsonTransformer());
        post("/login", "application/json", (req, res) -> {
            MultiMap bodyParser = new MultiMap();
            UrlEncoded.decodeTo(req.body(), bodyParser, "UTF-8");
            String username = bodyParser.getString("username");
            String password = bodyParser.getString("password");
//            Manager isManagerLogged = new Manager().login(db, username, password);
//            Cashier isCashierLogged = new Cashier().login(db, username, password);
//            if (isManagerLogged != null) {
//                System.out.println(isManagerLogged.getName());
//                System.out.println();
//                System.out.println(new Manager(0, "Evan", "evan", "henderson", 0, 0, 0, "Manager"));
//                return isManagerLogged;
//            } else if (isCashierLogged != null) {
//                System.out.println(isCashierLogged);
//                return isCashierLogged;
//            } else {
//                return "False";
//            }
            return ServiceOperation.login(username, password);
        }, new JsonTransformer());

    }
}
