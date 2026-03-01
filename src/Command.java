import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Command {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty command;
    private final SimpleStringProperty description;

    public Command(int id, String command, String description) {
        this.id = new SimpleIntegerProperty(id);
        this.command = new SimpleStringProperty(command);
        this.description = new SimpleStringProperty(description);
    }

    // Getters are required for the TableView to find the data
    public int getId() { 
        return id.get(); 
    }
    public String getCommand() {
        return command.get(); 
    }
    public String getDescription() { 
        return description.get(); 
    }
}