// Abdulrehman - Worked on CommandAddition.java

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommandAddition {

    private Stage stage;

    public CommandAddition(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        stage.setTitle("Add New Command");

        Label idLbl = new Label("Command ID");
        Label cmdLbl = new Label("Command Syntax");
        Label descLbl = new Label("Command Description");

        TextField idTf = new TextField();
        TextField cmdTf = new TextField();
        TextField descTf = new TextField();

        Button cancelBtn = new Button("Cancel");
        Button addBtn = new Button("Add Command");

        // Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(15);
        grid.setVgap(15);

        grid.add(idLbl, 0, 0);
        grid.add(idTf, 1, 0);

        grid.add(cmdLbl, 0, 1);
        grid.add(cmdTf, 1, 1);

        grid.add(descLbl, 0, 2);
        grid.add(descTf, 1, 2);

        grid.add(cancelBtn, 0, 3);
        grid.add(addBtn, 1, 3);

        // Button actions
        cancelBtn.setOnAction(e -> stage.close());

        addBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idTf.getText().trim());
                String command = cmdTf.getText().trim();
                String description = descTf.getText().trim();

                Connection con = DBUtils.establishConnection();
                String sql = "INSERT INTO commands (id, command, description) VALUES (?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ps.setString(2, command);
                ps.setString(3, description);

                ps.executeUpdate();

                // close resources
                ps.close();
                DBUtils.closeConnection(con, null);

                stage.close();

            } catch (NumberFormatException ex) {
                System.out.println("Invalid ID. Please enter a number.");
            } catch (SQLException ex) {
                System.out.println("DB Error: " + ex.getMessage());
            }
        });

        Scene scene = new Scene(grid, 520, 220);
        stage.setScene(scene);
        stage.show();
    }
}