package inventoryapp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;

public class Inventory implements Serializable {

    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) { allParts.add(newPart); }
    public static void addProduct(Product newProduct) { allProducts.add(newProduct); }

    public static Part lookupPart(int partId) {
        ObservableList<Part> filteredPart = allParts.filtered(part -> part.getId() == partId);
        return filteredPart.isEmpty() ? null : filteredPart.get(0);
    }
    public static Product lookupProduct(int productId) {
        ObservableList<Product> filteredProduct = allProducts.filtered(part -> part.getId() == productId);
        return filteredProduct.isEmpty() ? null : filteredProduct.get(0);
    }
    public static ObservableList<Part> lookupPart(String partName) {
        return allParts.filtered(part -> part.getName().contains(partName));
    }
    public static ObservableList<Product> lookupProduct(String productName) {
        return allProducts.filtered(product -> product.getName().contains(productName));
    }

    public static int getPartIndex(int partId) {
        int index = 0;
        for (Part part : allParts) {
           if (part.getId() == partId) {
               return index;
           }
           index++;
        }
        return -1;
    }
    public static int getProductIndex(int productId) {
        int index = 0;
        for (Product product : allProducts) {
           if (product.getId() == productId) {
               return index;
           }
           index++;
        }
        return -1;
    }

    public static void updatePart(int index, Part selectedPart) { allParts.set(index, selectedPart); }
    public static void updateProduct(int index, Product selectedProduct) { allProducts.set(index, selectedProduct); }

    public static boolean deletePart(Part selectedPart) { return allParts.remove(selectedPart); }
    public static boolean deleteProduct(Product selectedProduct) { return allProducts.remove(selectedProduct); }

    public static ObservableList<Part> getAllParts() { return allParts; }
    public static ObservableList<Product> getAllProducts() { return allProducts; }
}
