package model;

public class Detail_Transaction {

    int id_detail_transaction, id_transaction, id_item, quantity;
    float subtotal;
    
    int qty;
    float price, sbt;
    String item, category, description;

    public Detail_Transaction(int id_detail_transaction, int id_transaction, int id_item, int quantity, float subtotal) {
        this.id_detail_transaction = id_detail_transaction;
        this.id_transaction = id_transaction;
        this.id_item = id_item;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }
    
    public Detail_Transaction(String item, String category, String description, float price, int qty, float sbt) {
        this.item = item;
        this.category = category;
        this.description = description;
        this.price = price;
        this.qty = qty;
        this.sbt = sbt;
    }

}
