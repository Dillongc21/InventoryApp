package inventoryapp.controller;

import inventoryapp.err.ProductPriceLessThanPartsPrice;
import inventoryapp.util.ErrMessageInjector;
import inventoryapp.err.MaxLessThanMinException;
import inventoryapp.model.Inventory;
import inventoryapp.model.Part;
import inventoryapp.model.Product;
import inventoryapp.util.Formatter;
import inventoryapp.util.Navigator;
import inventoryapp.util.SearchHelper;
import inventoryapp.util.Sorter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField invField;
    @FXML
    private TextField minField;
    @FXML
    private TextField maxField;
    @FXML
    private Label errMessage;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Part> partPoolTable;
    @FXML
    private TableView<Part> productPartsTable;
    @FXML
    private TableColumn<Part, Integer> idPoolCol;
    @FXML
    private TableColumn<Part, String> namePoolCol;
    @FXML
    private TableColumn<Part, Integer> invPoolCol;
    @FXML
    private TableColumn<Part, Double> pricePoolCol;
    @FXML
    private TableColumn<Part, Integer> idProdPartCol;
    @FXML
    private TableColumn<Part, String> nameProdPartCol;
    @FXML
    private TableColumn<Part, Integer> invProdPartCol;
    @FXML
    private TableColumn<Part, Double> priceProdPartCol;

    static Product modifyProduct;

    private ObservableList<Part> partPool;      // List of Parts that can be added to current Product
    private Product tempProduct;  // Product for modifying parts while not saving state at cancel

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // initialize partPool and tempProduct
        tempProduct = new Product(modifyProduct);
        partPool = FXCollections.observableArrayList(Inventory.allParts);
        partPool.removeAll(tempProduct.getAllAssociatedParts());

        // Initialize product form fields
        idField.setText(Integer.toString(modifyProduct.getId()));
        nameField.setText(modifyProduct.getName());
        priceField.setText(Double.toString(modifyProduct.getPrice()));
        invField.setText(Integer.toString(modifyProduct.getStock()));
        minField.setText(Integer.toString(modifyProduct.getMin()));
        maxField.setText(Integer.toString(modifyProduct.getMax()));

        // Bind part & product objects fields to their correlated columns' cells
        Formatter.bindPartTableColumns(idPoolCol, namePoolCol, invPoolCol, pricePoolCol);
        Formatter.bindPartTableColumns(idProdPartCol, nameProdPartCol, invProdPartCol, priceProdPartCol);

        // Format price columns as currency
        Formatter.formatPartColAsCurrency(pricePoolCol);
        Formatter.formatPartColAsCurrency(priceProdPartCol);

        partPoolTable.setItems(partPool);
        productPartsTable.setItems(tempProduct.getAllAssociatedParts());
    }

    @FXML
    void handleSearchBtn() {
        String searchTxt = searchField.getText();
        SearchHelper.searchPartTable(searchTxt, partPoolTable);
    }
    @FXML
    void handleAddBtn() {
        // move selected part from pool into product then re-sort lists
        Part part = partPoolTable.getSelectionModel().getSelectedItem();
        partPool.remove(part);
        tempProduct.addAssociatedPart(part);
        sortTableLists();
    }
    @FXML
    void handleDeleteBtn() {
        // move selected part from product into pool then re-sort lists
        Part part = productPartsTable.getSelectionModel().getSelectedItem();
        tempProduct.deleteAssociatedPart(part);
        partPool.add(part);
        sortTableLists();
    }
    @FXML
    void handleSaveBtn() throws IOException, MaxLessThanMinException {

        try {
            // format text input fields for product constructor
            String name =   nameField.getText();
            double price =  Formatter.formatFieldToDouble(priceField);
            int inv =       Formatter.formatFieldToInt(invField);
            int min =       Formatter.formatFieldToInt(minField);
            int max =       Formatter.formatFieldToInt(maxField);

            // Check for exceptions
            if (max < min) {
                throw new MaxLessThanMinException(ErrMessageInjector.MAX_LESS_THAN_MIN);
            }
            double partsPriceTotal = tempProduct.getPartsPriceTotal();
            if (price < partsPriceTotal) {
                throw new ProductPriceLessThanPartsPrice(ErrMessageInjector.PROD_PRICE_LESS_THAN_PARTS);
            }

            // update modifyProduct fields
            modifyProduct.setName(name);
            modifyProduct.setPrice(price);
            modifyProduct.setStock(inv);
            modifyProduct.setMin(min);
            modifyProduct.setMax(max);

            // reset Associated parts and add the new associations
            modifyProduct.deleteAllAssociatedParts();
            modifyProduct.addListOfAssociatedParts(tempProduct.getAllAssociatedParts());


            int productIndex = Inventory.getProductIndex(modifyProduct.getId());
            Inventory.updateProduct(productIndex, modifyProduct);

            Navigator.goToMain();
        }
        catch (MaxLessThanMinException | ProductPriceLessThanPartsPrice ex) {
            errMessage.setText(ex.getMessage());
        }
    }
    @FXML
    void handleCancelBtn() throws IOException {
        Navigator.goToMain();
    }

    private void sortTableLists() {
        Sorter.sortPartsList(partPool);
        Sorter.sortPartsList(tempProduct.getAllAssociatedParts());
    }
}
