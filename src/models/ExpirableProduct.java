package models;

import interfaces.Expirable;
import java.util.Date;


public class ExpirableProduct extends Product implements Expirable {

    private final Date expirationDate;

    public ExpirableProduct(String name, double price, int quantity, Date expirationDate) {
        super(name, price, quantity);
        this.expirationDate = expirationDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    @Override
    public boolean isExpired() {
        return new Date().after(expirationDate);
    }
}
