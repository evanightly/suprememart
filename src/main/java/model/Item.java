package model;

public class Item {

    int id_item, id_category, in_stock;
    String title, description, category;
    float price;

    public Item(int id_item, int id_category, String title, String description, float price, int in_stock) {
        this.id_item = id_item;
        this.id_category = id_category;
        this.in_stock = in_stock;
        this.title = title;
        this.description = description;
        this.price = price;
    }
    
    public Item(int id_item, String category, String title, String description, float price, int in_stock) {
        this.id_item = id_item;
        this.category = category;
        this.in_stock = in_stock;
        this.title = title;
        this.description = description;
        this.price = price;
    }


    public Item searchItem(String title) {
        return this;
    }

    public boolean isAvailable() {
        return in_stock > 0;
    }
}
