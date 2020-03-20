package inventoryapp.util;

import inventoryapp.model.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;

public class Sorter {
    public static void sortPartsList(ObservableList<Part> parts) {
        Comparator<Part> comparator = Comparator.comparingInt(Part::getId);
        FXCollections.sort(parts, comparator);
    }
}
