package models;

public class Customer {
    private String name;
    private double balance;
    private Cart cart;

    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.cart = new Cart();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void reduceBalance(double amount) {
        this.balance -= amount;
    }

    public boolean hasSufficientBalance(double amount) {
        return this.balance >= amount;
    }

    public Cart getCart() {
        return cart;
    }

}
