package model;

public class Transaction {

    int id_transaction;
    String customer_name;
    String transaction_date;
    float total;

    public Transaction(int id_transaction, String customer_name, String transaction_date, float total) {
        this.id_transaction = id_transaction;
        this.customer_name = customer_name;
        this.transaction_date = transaction_date;
        this.total = total;
    }
}
