import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        CommandListing cmdListing = new CommandListing(primaryStage);
        cmdListing.initializeComponents();
    }
    public static void main(String[] args) {
        launch(args);
    }
}