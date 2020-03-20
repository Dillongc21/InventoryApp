package inventoryapp.util;

import inventoryapp.model.*;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.NumberFormat;

public class Formatter {

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    public static void bindPartTableColumns(TableColumn<Part, Integer> idCol, TableColumn<Part, String> nameCol,
                                     TableColumn<Part, Integer> invCol, TableColumn<Part, Double> priceCol) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    public static void bindProductTableColumns(TableColumn<Product, Integer> idCol, TableColumn<Product, String> nameCol,
                                     TableColumn<Product, Integer> invCol, TableColumn<Product, Double> priceCol) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public static void formatPartColAsCurrency(TableColumn<Part, Double> column) {
        column.setCellFactory(tableCell -> new TableCell<Part, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                }
                else {
                    setText(currencyFormat.format(price));
                }
            }
        });
    }
    public static void formatProductColAsCurrency(TableColumn<Product, Double> column) {
        column.setCellFactory(tableCell -> new TableCell<Product, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                }
                else {
                    setText(currencyFormat.format(price));
                }
            }
        });
    }

    public static int formatFieldToInt(TextField field) {
        return Integer.parseInt(field.getText().replaceAll(",", ""));
    }
    public static double formatFieldToDouble(TextField field) {
        return Double.parseDouble(field.getText().replaceAll(",", ""));
    }
}
