package inventoryapp.util;

import inventoryapp.model.Inventory;
import inventoryapp.model.Part;
import inventoryapp.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class SearchHelper {
    public static void searchPartTable(String searchTxt, TableView<Part> partTable) {

        String intCheck = "^\\d+$";

        // If text entry is integer, return by Id
        if (searchTxt.matches(intCheck)) {
            int searchId = Integer.parseInt(searchTxt);
            ObservableList<Part> list;

            // if id is found, return list with just that part, otherwise return empty list
            if (Inventory.lookupPart(searchId) != null) {
                list = FXCollections.observableArrayList(Inventory.lookupPart(searchId));
            }
            else {
                list = FXCollections.observableArrayList();
            }
            partTable.setItems(list);
        }
        else {
            partTable.setItems(Inventory.lookupPart(searchTxt));
        }
    }
    public static void searchProductTable(String searchTxt, TableView<Product> productTable) {

        String intCheck = "^\\d+$";

        // If text entry is integer, return by Id
        if (searchTxt.matches(intCheck)) {
            int searchId = Integer.parseInt(searchTxt);
            ObservableList<Product> list;

            // if id is found, return list with just that product, otherwise return empty list
            if (Inventory.lookupProduct(searchId) != null) {
                list = FXCollections.observableArrayList(Inventory.lookupProduct(searchId));
            }
            else {
                list = FXCollections.observableArrayList();
            }
            productTable.setItems(list);
        }
        else {
            productTable.setItems(Inventory.lookupProduct(searchTxt));
        }
    }
}
