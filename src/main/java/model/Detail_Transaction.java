package model;

public class Detail_Transaction {

    int id_detail_transaction, id_transaction, id_item, quantity;
    float subtotal;

    public Detail_Transaction(int id_detail_transaction, int id_transaction, int id_item, int quantity, float subtotal) {
        this.id_detail_transaction = id_detail_transaction;
        this.id_transaction = id_transaction;
        this.id_item = id_item;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

}
