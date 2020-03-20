package inventoryapp.controller;

import inventoryapp.util.ErrMessageInjector;
import inventoryapp.err.MaxLessThanMinException;
import inventoryapp.model.InHouse;
import inventoryapp.model.Inventory;
import inventoryapp.model.Outsourced;
import inventoryapp.util.Formatter;
import inventoryapp.util.Navigator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {

    @FXML
    private Toggle inHouseRadio;
    @FXML
    private Toggle outsourcedRadio;
    @FXML
    private ToggleGroup inOutGroup;
    @FXML
    private HBox machineRow;
    @FXML
    private HBox companyRow;
    @FXML
    private TextField nameField;
    @FXML
    private TextField invField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField minField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField machIdField;
    @FXML
    private TextField compNameField;
    @FXML
    private RowConstraints errRow;
    @FXML
    private Label errMessage;

    private boolean inHouse;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // initialize Toggle group
        inOutGroup = new ToggleGroup();
        inHouseRadio.setToggleGroup(inOutGroup);
        outsourcedRadio.setToggleGroup(inOutGroup);

        // hide companyName row by default
        companyRow.setVisible(false);

        // is in house by default
        inHouse = true;

    }

    // Event Handlers
    @FXML
    void handleInHouseRadio() {
        companyRow.setVisible(false);
        machineRow.setVisible(true);
        inHouse = true;
    }
    @FXML
    void handleOutsourcedRadio() {
        machineRow.setVisible(false);
        companyRow.setVisible(true);
        inHouse = false;
    }
    @FXML
    void handleCancelBtn() throws IOException {
        Navigator.goToMain();
    }
    @FXML
    void handleSaveBtn() throws IOException, MaxLessThanMinException {

        try {
            String name         = nameField.getText();
            double price        = Formatter.formatFieldToDouble(priceField);
            int inv             = Formatter.formatFieldToInt(invField);
            int min             = Formatter.formatFieldToInt(minField);
            int max             = Formatter.formatFieldToInt(maxField);
            int machineId       = Formatter.formatFieldToInt(machIdField);
            String companyName  = compNameField.getText();

            if(max < min) {
                throw new MaxLessThanMinException(ErrMessageInjector.MAX_LESS_THAN_MIN);
            }

            if (inHouse) {
                Inventory.addPart(new InHouse(name, price, inv, min, max, machineId));
            }
            else {
                Inventory.addPart(new Outsourced(name, price, inv, min, max, companyName));
            }
            Navigator.goToMain();
        }
        catch (MaxLessThanMinException ex) {
            errRow.setMaxHeight(Control.USE_COMPUTED_SIZE);
            errMessage.setText(ex.getMessage());
        }
    }
}
