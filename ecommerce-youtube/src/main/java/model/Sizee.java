package model;
//no need to make entity bcz already embedded it is
public class Sizee {
    private String name;
    private int quantity;

    public Sizee(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
