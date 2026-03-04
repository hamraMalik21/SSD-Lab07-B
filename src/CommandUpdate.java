//Hamra Nadeem; worked on Command Update
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class CommandUpdate{

    private Stage stage;

    public CommandUpdate(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        stage.setTitle("Update Command");
        Label id_lbl = new Label("Enter command ID: ");
        Label cmd_lbl = new Label("Enter new command: ");
        Label desc_lbl = new Label("Enter new command description: ");

        TextField id_input = new TextField();
        TextField cmd_input = new TextField();
        TextField desc_input = new TextField();

        Button cancel_button = new Button("Cancel");
        Button update_button = new Button("Update Command");

        GridPane grid = new GridPane();

        grid.setPadding(new Insets(20));
        grid.setHgap(15);
        grid.setVgap(15);

        grid.add(id_lbl, 0, 0);
        grid.add(id_input, 1, 0);

        grid.add(cmd_lbl, 0, 1);
        grid.add(cmd_input, 1, 1);

        grid.add(desc_lbl, 0, 2);
        grid.add(desc_input, 1, 2);

        grid.add(cancel_button, 0, 3);
        grid.add(update_button, 1, 3);

        cancel_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });

        update_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{

                    int old_id = Integer.parseInt(id_input.getText().trim());
                    String new_command = cmd_input.getText().trim();
                    String new_description = desc_input.getText().trim();

                    Connection con = DBUtils.establishConnection();

                    String query = "UPDATE commands SET command=?, description=? WHERE id=?";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setString(1, new_command);
                    ps.setString(2, new_description);
                    ps.setInt(3, old_id);

                    ps.executeUpdate();

                    ps.close();
                    DBUtils.closeConnection(con, null);

                    stage.close();


                } catch (NumberFormatException ex) {
                    System.out.println("Invalid ID. Please enter a number.");
                } catch (SQLException ex) {
                    System.out.println("DB Error: " + ex.getMessage());
                }
            }
        });

        Scene sc = new Scene(grid, 520, 220);

        stage.setScene(sc);
        stage.show();


    }
}
