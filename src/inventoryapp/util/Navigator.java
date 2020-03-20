package inventoryapp.util;

import inventoryapp.App;
import java.io.IOException;

public class Navigator {

    public static void goToMain() throws IOException {
        App.setScreen("main.fxml", 1000, 375);
    }
    public static void goToAddPart() throws IOException {
        App.setScreen("addPart.fxml", 450, 400);
    }
    public static void goToModifyPart() throws IOException {
        App.setScreen("modifyPart.fxml", 450, 400);
    }
    public static void goToAddProduct() throws IOException {
        App.setScreen("addProduct.fxml", 905, 520);
    }
    public static void goToModifyProduct() throws IOException {
        App.setScreen("modifyProduct.fxml", 905, 520);
    }
}
