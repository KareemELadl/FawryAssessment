package services;

import interfaces.Shippable;
import java.util.List;

public class ShippingService {

    private static ShippingService instance;
    private double shippingCost = 30.0;

    private ShippingService() {}


    public static ShippingService getInstance() {
        if (instance == null) {
            instance = new ShippingService();
        }
        return instance;
    }



    public double processShipping(List<Shippable> shippableItems, List<Integer> quantities) {
        if (shippableItems.isEmpty()) {
            return 0.0;
        }

        double totalWeight = 0.0;

        System.out.println("** Shipment notice **");

        for (int i = 0; i < shippableItems.size(); i++) {
            Shippable item = shippableItems.get(i);
            int quantity = quantities.get(i);
            double itemWeight = item.getWeight();
            double totalItemWeight = itemWeight * quantity;
            totalWeight += totalItemWeight;

            String weightString = "";
            if (totalItemWeight < 1.0) {
                weightString = String.format("%.2fg", totalItemWeight * 1000);
            } else {
                weightString = String.format("%.2fkg", totalItemWeight);
            }

            System.out.println(quantity + "x " + item.getName() + " " + weightString);
        }

        String totalWeightString;
        if (totalWeight < 1.0) {
            totalWeightString = String.format("%.2fg", totalWeight * 1000);
        } else {
            totalWeightString = String.format("%.2fkg", totalWeight);
        }

        System.out.println("Total package weight " + totalWeightString);

        return shippingCost;
    }


    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }
}
