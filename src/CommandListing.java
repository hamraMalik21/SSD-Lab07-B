// Salman Jahangir - Worked on CommandListing.java

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;         
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;           
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class CommandListing{
    private Stage stage;

    public CommandListing(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void initializeComponents() {
        stage.setTitle("Database Command Viewer");

        // 1. Create the TableView
        TableView<Command> table = new TableView<>();

        // 2. Define Columns (The string names must match the Model class getters)
        TableColumn<Command, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Command, String> cmdCol = new TableColumn<>("Command");
        cmdCol.setCellValueFactory(new PropertyValueFactory<>("command"));

        TableColumn<Command, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        table.getColumns().addAll(idCol, cmdCol, descCol);

        ObservableList<Command> commandsList = FXCollections.observableArrayList();

        // 3. Load data from DB and fill up the table
        try{
            Connection con = DBUtils.establishConnection();
            String query = "SELECT id, command, description FROM commands";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Command command = new Command(
                        rs.getInt("id"),
                        rs.getString("command"),
                        rs.getString("description")
                );
                commandsList.add(command);
            }

            DBUtils.closeConnection(con, stmt);
        }catch (SQLException e) {
            System.out.println("Error fetching data: " + e.getMessage());
        }

        table.setItems(commandsList);

        //4. Buttons (added below the table) new 
        Button addBtn = new Button("Add New Command");
        Button updateBtn = new Button("Update a Command");


        addBtn.setOnAction(e -> new CommandAddition(new Stage()).show());
        updateBtn.setOnAction(e -> new CommandUpdate(new Stage()).show());

        HBox buttonBox = new HBox(10, addBtn, updateBtn);
        buttonBox.setStyle("-fx-padding: 10;");

        // 5. Layout (minimal change: include buttonBox)
        VBox vbox = new VBox(table, buttonBox);
        Scene scene = new Scene(vbox, 750, 500);

        stage.setScene(scene);
        stage.show();
        return;
    }

}