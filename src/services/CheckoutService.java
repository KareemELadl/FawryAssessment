package services;

import exceptions.*;
import interfaces.Expirable;
import interfaces.Shippable;
import models.*;
import java.util.ArrayList;
import java.util.List;



public class CheckoutService {
    
    private final ShippingService shippingService;
    
    public CheckoutService() {
        this.shippingService = ShippingService.getInstance();
    }


    public void checkout(Customer customer, Cart cart) throws Exception {
        
        if (cart.isEmpty()) {
            throw new EmptyCartException("Cannot checkout with an empty cart");
        }
        validateProducts(cart);
        
        double subtotal = cart.getSubtotal();
        
        List<Shippable> shippableItems = new ArrayList<>();
        List<Integer> shippableQuantities = new ArrayList<>();
        collectShippableItems(cart, shippableItems, shippableQuantities);
        double shippingFee = shippingService.processShipping(shippableItems, shippableQuantities);
        double totalAmount = subtotal + shippingFee;
        
        if (!customer.hasSufficientBalance(totalAmount)) {
            throw new InsufficientBalanceException("Insufficient balance. Required: $" + 
                totalAmount + ", Available: $" +
                customer.getBalance());
        }

        customer.reduceBalance(totalAmount);
        updateProductQuantities(cart);
        printCheckoutReceipt(cart, subtotal, shippingFee, totalAmount, customer.getBalance());
        cart.clear();
    }
    

    private void validateProducts(Cart cart) throws OutOfStockException, ExpiredProductException {
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            int requestedQuantity = item.getQuantity();
            
            if (!product.isAvailable(requestedQuantity)) {
                throw new OutOfStockException(product.getName() + " is out of stock. Available: " + 
                    product.getQuantity() + ", Requested: " + requestedQuantity);
            }
            
            if (product instanceof Expirable) {
                Expirable expirableProduct = (Expirable) product;
                if (expirableProduct.isExpired()) {
                    throw new ExpiredProductException(product.getName() + " has expired");
                }
            }
        }
    }

    private void collectShippableItems(Cart cart, List<Shippable> shippableItems, List<Integer> quantities) {
        for (CartItem item : cart.getItems()) {
            if (item.getProduct() instanceof Shippable) {
                shippableItems.add((Shippable) item.getProduct());
                quantities.add(item.getQuantity());
            }
        }
    }

    private void updateProductQuantities(Cart cart) {
        for (CartItem item : cart.getItems()) {
            item.getProduct().reduceQuantity(item.getQuantity());
        }
    }

    private void printCheckoutReceipt(Cart cart, double subtotal, double shippingFee, 
                                    double totalAmount, double remainingBalance) {
        System.out.println("** Checkout receipt **");
        
        for (CartItem item : cart.getItems()) {
            System.out.println(item.getQuantity() + "x " + item.getProduct().getName() + 
                " " + String.format("%.2f", item.getTotalPrice()));
        }
        
        System.out.println("----------------------");
        System.out.println("Subtotal " + String.format("%.2f", subtotal));
        System.out.println("Shipping " + String.format("%.2f", shippingFee));
        System.out.println("Amount " + String.format("%.2f", totalAmount));
        System.out.println("Customer balance after payment: $" + String.format("%.2f", remainingBalance));
    }
}
