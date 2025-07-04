package models;

import exceptions.OutOfStockException;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void add(Product product, int quantity) throws OutOfStockException {
        if (!product.isAvailable(quantity)) {
            throw new OutOfStockException("Insufficient stock for " + product.getName() + 
                ". Available: " + product.getQuantity() + ", Needed: " + quantity);
        }

        for (CartItem item : items) {
            if (item.getProduct().getName().equals(product.getName())) {
                int newQuantity = item.getQuantity() + quantity;
                if (!product.isAvailable(newQuantity)) {
                    throw new OutOfStockException("Insufficient stock for " + product.getName() + 
                        ". Available: " + product.getQuantity() + ", Needed: " + newQuantity);
                }
                item.setQuantity(newQuantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    public List<CartItem> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getSubtotal() {
        double subtotal = 0.0;
        for (CartItem item : items) {
            subtotal += item.getTotalPrice();
        }
        return subtotal;
    }

    public void clear() {
        items.clear();
    }
}
