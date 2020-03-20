package inventoryapp.controller;

import inventoryapp.model.*;

import inventoryapp.util.Initializer;
import inventoryapp.util.Formatter;
import inventoryapp.util.Navigator;
import inventoryapp.util.SearchHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TextField partsSearchText;
    @FXML
    private TextField productsSearchText;
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInvCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;

    private static boolean entered = false;

    // FXML referenced components/controls
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Initialize inventory data
        if(!entered) {
            Initializer.setupInitialInventoryData();
            entered = true;
        }

        // Bind part & product objects fields to their correlated columns' cells
        Formatter.bindPartTableColumns(partIdCol, partNameCol, partInvCol, partPriceCol);
        Formatter.bindProductTableColumns(productIdCol, productNameCol, productInvCol, productPriceCol);

        // Format price columns as currency
        Formatter.formatPartColAsCurrency(partPriceCol);
        Formatter.formatProductColAsCurrency(productPriceCol);

        // populate tables
        partTable.setItems(Inventory.allParts);
        productTable.setItems(Inventory.allProducts);
    }

    // Parts buttons
    @FXML
    void handleAddPartBtn() throws IOException {
        Navigator.goToAddPart();
    }
    @FXML
    void handleModifyPartBtn() throws IOException {
        ModifyPartController.modifyPart = partTable.getSelectionModel().getSelectedItem();
        Navigator.goToModifyPart();
    }
    @FXML
    void handleDeletePartBtn() {
        Part part = partTable.getSelectionModel().getSelectedItem();
        Inventory.deletePart(part);
    }
    @FXML
    void handlePartsSearch() {
        String searchTxt = partsSearchText.getText();
        SearchHelper.searchPartTable(searchTxt, partTable);
    }

    // Product buttons
    @FXML
    void handleAddProductBtn() throws IOException {
        Navigator.goToAddProduct();
    }
    @FXML
    void handleModifyProductBtn() throws IOException {
        ModifyProductController.modifyProduct = productTable.getSelectionModel().getSelectedItem();
        Navigator.goToModifyProduct();
    }
    @FXML
    void handleDeleteProductBtn() {
        Product product = productTable.getSelectionModel().getSelectedItem();
        Inventory.deleteProduct(product);
    }
    @FXML
    void handleProductsSearch() {
        SearchHelper.searchProductTable(productsSearchText.getText(), productTable);
    }


    @FXML
    void handleExitBtn() {
        System.exit(0);
    }
}