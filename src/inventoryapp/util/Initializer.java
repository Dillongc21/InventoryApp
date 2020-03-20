package inventoryapp.util;

import inventoryapp.model.*;

public class Initializer {
    public static void setupInitialInventoryData() {
        Part part1 = new InHouse("Hinge",2.50,13,6,20,12);
        Part part2 = new InHouse("Socket",3.00,212,150,1000,27);
        Inventory.allParts.add(part1);
        Inventory.allParts.add(part2);
        Inventory.allParts.add(new Outsourced("6\" Ball Bearings",1.25,58,25,60,"Wei Ming"));
        Inventory.allParts.add(new Outsourced("8\" Ball Bearings", 1.50, 37, 20, 50, "Wei Ming"));
        Inventory.allProducts.add(new Product("ContraptionA", 4.99, 12, 5, 30));
        Inventory.allProducts.add(new Product("ContraptionB", 7.99, 8, 5, 30));
        Product productWithParts = new Product("Thing 1", 9.99, 8, 3, 20);
        productWithParts.addAssociatedPart(part1);
        productWithParts.addAssociatedPart(part2);
        Inventory.allProducts.add(productWithParts);
    }
}
