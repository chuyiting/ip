package duke.ui.gui;

import duke.Duke;
import duke.DukeCommandMatcher;
import duke.storage.Storage;
import duke.ui.Printer;
import duke.utils.Constants;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private HBox userPanel;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DonaldTrump.jpg"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/Duke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(DialogBox.getDukeDialog(Constants.GREETING, dukeImage));

        userInput.focusedProperty().addListener((event, oldVal, newVal) -> {
            if (newVal) {
                userInput.setStyle("-fx-background-radius: 10");
                userPanel.setPadding(new Insets(3, 5, 3, 5));
                userPanel.setSpacing(10.0);
                userInput.setPrefWidth(325);
            } else {
                userInput.setStyle("-fx-background-radius: 30");
                userPanel.setPadding(new Insets(3, 5, 3, 25));
                userInput.setPrefWidth(275);
                userPanel.setSpacing(25.0);
            }
        });
    }


    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        //handle input
        if (!Objects.equals(input.trim(), "")) {
            dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));
            handleDukeOutput(input);
            userInput.clear();
        }
    }

    private void handleDukeOutput(String input) {
        try {
            Storage database = new Storage("data/tasksTable.csv");
            DukeCommandMatcher parser = new DukeCommandMatcher(database);
            String response = parser.matchCommand(input);
            DialogBox db;
            if (Objects.equals(response, "EXIT")) {
                db = DialogBox.getDukeDialog(Printer.printBye(), dukeImage);
            } else {
                db = DialogBox.getDukeDialog(response, dukeImage);
            }
            dialogContainer.getChildren().add(db);
        } catch (Exception e) {
            dialogContainer.getChildren().add(DialogBox.getDukeDialog(e.getMessage(), dukeImage));
        }
    }
}