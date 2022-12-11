package main;

import db.Connect;
import java.sql.Connection;
import model.Manager;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;
import static spark.Spark.*;

public class HokiMart {

    private static MultiMap bodyParser = new MultiMap();

    public static void main(String[] args) {
        Connection db = Connect.getConnection();
        port(2022);

        get("/", (req, res) -> {
            return "Hello World";
        });

        get("/employee", (req, res) -> {
            return ServiceOperation.getAllCashiers();
        });

//        get("/test", "application/json", (req, res) -> {
//            System.out.println(ServiceOperation.login());
//            return ServiceOperation.login();
//        }, new JsonTransformer());
        post("/login", "application/json", (req, res) -> {
            System.out.println("Triggered");
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
