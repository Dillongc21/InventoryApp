package inventoryapp;

import inventoryapp.util.Navigator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Parent root;
    private static Stage currStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Initialize currStage before opening main screen
        currStage = primaryStage;
        Navigator.goToMain();
    }

    public static void setScreen(String filename, int width, int height) throws IOException {
        try {
            root = FXMLLoader.load(App.class.getResource("view/" + filename));

            Scene scene = new Scene(root, width, height);
            scene.getStylesheets().add("inventoryapp/App.css");
            currStage.setTitle("Inventory App");
            currStage.setScene(scene);
            currStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
