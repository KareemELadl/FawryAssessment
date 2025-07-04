import models.*;
import services.CheckoutService;

import java.util.Date;

public class Main {

    public static void main(String[] args) throws Exception {
        ExpirableShippableProduct expiredCheese = new ExpirableShippableProduct("Cheese", 100.0, 10, new Date("01/01/2025"), 0.2);
        ExpirableShippableProduct goodCheese = new ExpirableShippableProduct("Cheese", 100.0, 10, new Date("01/01/2026"), 0.2);

        ShippableProduct tv = new ShippableProduct("TV", 5000.0, 5, 15.0);
        ShippableProduct cheapTV = new ShippableProduct("CHEAP TV", 500.0, 5, 15.0);

        NonShippableProduct scratchCard = new NonShippableProduct("Mobile Scratch Card", 25.0, 100);

        ExpirableProduct goodVineLeaves = new ExpirableProduct("Vine Leaves", 10.0, 100, new Date("01/01/2026")); // Assuming that vine leaves are made fresh so it cannot be shipped due to having sauce on it

        Customer customer = new Customer("Kareem Eladl", 4000);

        CheckoutService checkoutService = new CheckoutService();


        successfulCase(goodCheese, scratchCard, goodVineLeaves, cheapTV, customer, checkoutService ); // all conditions are met

        failedCaseExpired(expiredCheese, customer, checkoutService); // expired so it should fail
        failedCaseHigherQuantityOrder(goodCheese, customer, checkoutService); // higher quantity than available so it should fail
        failedCaseHighPrice(tv, customer, checkoutService); // higher price than balance so it should fail
        failedCaseEmptyCart(customer, checkoutService); // empty cart

    }

    private static void successfulCase(Product goodCheese, Product scratchCard, Product goodVineLeaves, Product cheapTV, Customer customer, CheckoutService checkoutService) {
        try {
            Cart cart = customer.getCart();
            cart.add(goodCheese, 1);
            cart.add(scratchCard, 1);
            cart.add(goodVineLeaves, 1);
            cart.add(cheapTV, 1);
            checkoutService.checkout(customer, cart);
        } catch (Exception e) {
            System.out.println("Failed case 1: " + e.getMessage());
        }
    }

    private static void failedCaseExpired(Product expiredCheese, Customer customer, CheckoutService checkoutService) {
        try {
            Cart cart = customer.getCart();
            cart.add(expiredCheese, 1);
            checkoutService.checkout(customer, cart);
        } catch (Exception e) {
            System.out.println("Failed case Expired: " + e.getMessage());
            customer.getCart().clear();

        }
    }


    private static void failedCaseHigherQuantityOrder(Product goodCheese, Customer customer, CheckoutService checkoutService) {
        try {
            Cart cart = customer.getCart();
            cart.add(goodCheese, 1000);
            checkoutService.checkout(customer, cart);
        } catch (Exception e) {
            System.out.println("Failed case Higher Quantity: " + e.getMessage());
            customer.getCart().clear();

        }
    }

    private static void failedCaseHighPrice(Product tv, Customer customer, CheckoutService checkoutService) {
        try {
            Cart cart = customer.getCart();
            cart.add(tv, 1);
            checkoutService.checkout(customer, cart);
        } catch (Exception e) {
            System.out.println("Failed case High Price: " + e.getMessage());
            customer.getCart().clear();

        }
    }

    private static void failedCaseEmptyCart(Customer customer, CheckoutService checkoutService) {
        try {
            checkoutService.checkout(customer, customer.getCart());
        } catch (Exception e) {
            System.out.println("Failed case Empty Cart: " + e.getMessage());
            customer.getCart().clear();
        }
    }



}