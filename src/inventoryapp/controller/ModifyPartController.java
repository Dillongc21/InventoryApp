package inventoryapp.controller;

import inventoryapp.util.ErrMessageInjector;
import inventoryapp.err.MaxLessThanMinException;
import inventoryapp.model.InHouse;
import inventoryapp.model.Inventory;
import inventoryapp.model.Outsourced;
import inventoryapp.model.Part;
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


public class ModifyPartController implements Initializable {

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
    private TextField idField;
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

    // Parts to hold previous state if part is toggled between InHouse and Outsourced
    private InHouse inHouseHolder;
    private Outsourced outsourcedHolder;

    // Part selected from main screen; is replaced in handleSaveBtn method
    static Part modifyPart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // initialize Toggle group
        inOutGroup = new ToggleGroup();
        inHouseRadio.setToggleGroup(inOutGroup);
        outsourcedRadio.setToggleGroup(inOutGroup);

        // hide companyName row by default
        companyRow.setVisible(false);

        // initialize generic Part form fields
        idField.setText(Integer.toString(modifyPart.getId()));
        nameField.setText(modifyPart.getName());
        invField.setText(Integer.toString(modifyPart.getStock()));
        priceField.setText(Double.toString(modifyPart.getPrice()));
        minField.setText(Integer.toString(modifyPart.getMin()));
        maxField.setText(Integer.toString(modifyPart.getMax()));

        if (modifyPart instanceof InHouse) {
            // initialize inHouseHolder and machIdField
            inHouseHolder = (InHouse)modifyPart;
            String machineIdTxt = Integer.toString(((InHouse) modifyPart).getMachineId());
            machIdField.setText(machineIdTxt);
        }
        else {
            // initialize outsourcedHolder and compNameField
            outsourcedHolder = (Outsourced)modifyPart;
            String companyNameTxt = ((Outsourced) modifyPart).getCompanyName();
            compNameField.setText(companyNameTxt);

            // set radio button to "Outsourced" and change form fields to reflect an Outsourced Part
            outsourcedRadio.setSelected(true);
            machineRow.setVisible(false);
            companyRow.setVisible(true);
        }
    }

    // Event Handlers
    @FXML
    void handleInHouseRadio() {

        /*
         * if no inHouseHolder Part exists, create a new InHouse Part from the values in the outsourcedHolder Part and
         * assign it to inHouseHolder
         */
        if (inHouseHolder == null) {
            inHouseHolder = new InHouse(outsourcedHolder, 0);
        }

        // set modifyPart to InHouse Part and change form for InHouse values
        modifyPart = inHouseHolder;
        companyRow.setVisible(false);
        machineRow.setVisible(true);
    }
    @FXML
    void handleOutsourcedRadio() {

        /*
         * if no outsourcedHolder Part exists, create a new Outsourced Part from the values in the inHouseHolder Part and
         * assign it to outsourcedHolder
         */
        if (outsourcedHolder == null) {
            outsourcedHolder = new Outsourced(inHouseHolder, "");
        }

        // set modifyPart to Outsourced Part and change form for Outsourced values
        modifyPart = outsourcedHolder;
        machineRow.setVisible(false);
        companyRow.setVisible(true);
    }
    @FXML
    void handleCancelBtn() throws IOException {
        Navigator.goToMain();
    }
    @FXML
    void handleSaveBtn() throws IOException, MaxLessThanMinException {

        try {
            // set modifyPart generic Part values to the correctly formatted and casted values from the form
            modifyPart.setName(nameField.getText());
            modifyPart.setStock(Formatter.formatFieldToInt(invField));
            modifyPart.setPrice(Formatter.formatFieldToDouble(priceField));
            modifyPart.setMin(Formatter.formatFieldToInt(minField));
            modifyPart.setMax(Formatter.formatFieldToInt(maxField));

            // throw exception if max is less than min
            if(modifyPart.getMax() < modifyPart.getMin()) {
                throw new MaxLessThanMinException(ErrMessageInjector.MAX_LESS_THAN_MIN);
            }

            if (modifyPart instanceof InHouse) {
                // if part is InHouse, set machineId to the formatted field from form
                ((InHouse)modifyPart).setMachineId(Formatter.formatFieldToInt(machIdField));
            }
            else {
                // if part is Outsourced, set companyName to the formatted field from form
                ((Outsourced)modifyPart).setCompanyName(compNameField.getText());
            }

            // Update part in Inventory with new modifyPart
            int partIndex = Inventory.getPartIndex(modifyPart.getId());
            Inventory.updatePart(partIndex, modifyPart);

            // Go back to Main Screen
            Navigator.goToMain();
        }
        catch (MaxLessThanMinException ex) {
            errRow.setMaxHeight(Control.USE_COMPUTED_SIZE);
            errMessage.setText(ex.getMessage());
        }

    }
}
