package models;

import interfaces.Expirable;
import interfaces.Shippable;
import java.util.Date;

public class ExpirableShippableProduct extends Product implements Expirable, Shippable {
    
    private Date expirationDate;
    private double weight; // weight in kg

    public ExpirableShippableProduct(String name, double price, int quantity, Date expirationDate, double weight) {
        super(name, price, quantity);
        this.expirationDate = expirationDate;
        this.weight = weight;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    @Override
    public boolean isExpired() {
        return new Date().after(expirationDate);
    }

    @Override
    public double getWeight() {
        return weight;
    }
}
