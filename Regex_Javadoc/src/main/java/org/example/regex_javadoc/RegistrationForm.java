package org.example.regex_javadoc;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class RegistrationForm extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        //Title for Primary Stage
        primaryStage.setTitle("Registration Form");

        //Grid Pane is layout manager in Javafx
        GridPane gridPane = new GridPane();
        //The side padding which is the edge of application
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        //Gaps between cells in the grid
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        //UI Controls
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField emailField = new TextField();
        TextField dobField = new TextField();
        TextField zipCodeField = new TextField();

        //Error Display Messages
        Label firstNameError = new Label();
        Label lastNameError = new Label();
        Label emailError = new Label();
        Label dobError = new Label();
        Label zipCodeError = new Label();

        //Button
        Button addButton = new Button("Add");
        //disable button from start
        addButton.setDisable(true);

        //setUp validation and event listeners
        setupValidation(firstNameField,firstNameError,"First Name","[A-Za-z]{2,25}");
        setupValidation(lastNameField,lastNameError,"Last Name","[A-Za-z]{2,25}");
        setupValidation(emailField,emailError,"Email","^[\\w.+\\-]+@farmingdale\\.edu$");
        setupValidation(dobField,dobError,"Date of Birth","(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\\d{4}");
        setupValidation(zipCodeField,zipCodeError,"Zip Code","\\d{5}");

        //Adding fields and error labels to gridpane
        gridPane.add(new Label("First Name"), 0, 0);
        gridPane.add(firstNameField, 1, 0);
        gridPane.add(firstNameError, 2, 0);

        gridPane.add(new Label("Last Name"), 0, 1);
        gridPane.add(lastNameField, 1, 1);
        gridPane.add(lastNameError, 2, 1);

        gridPane.add(new Label("Email"), 0, 2);
        gridPane.add(emailField, 1, 2);
        gridPane.add(emailError, 2, 2);

        gridPane.add(new Label("Date Of Birth: "), 0, 3);
        gridPane.add(dobField, 1, 3);
        gridPane.add(dobError, 2, 3);

        gridPane.add(new Label("Zip Code"), 0, 4);
        gridPane.add(zipCodeField, 1, 4);
        gridPane.add(zipCodeError, 2, 4);

        gridPane.add(addButton, 1, 5);

        addButton.disableProperty().bind(
                Bindings.createBooleanBinding(()->
                        !isValidField(firstNameField, "[A-Za-z]{2,25}")||
                                !isValidField(lastNameField, "[A-Za-z]{2,25}")||
                                !isValidField(emailField,"[\\w.+\\-]+@farmingdale\\.edu$")||
                                !isValidField(dobField,"(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01]/\\d{4}")||
                                !isValidField(zipCodeField,"\\d{5}"),
                        firstNameField.textProperty(), lastNameField.textProperty(),
                        emailField.textProperty(), dobField.textProperty(),
                        zipCodeField.textProperty()
                        )
        );
        //add button event handler
        addButton.setOnAction(e ->handleAddButton(primaryStage));
        //Setting size of App
        Scene scene = new Scene(gridPane, 600, 400);
        //putting scene into Primary Stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Setup validation for text fields using regex and show error messages
     * @param textField the field to validate
     * @param errorLabel The label to display validation errors
     * @param fieldName The name of the field
     * @param regex The regex pattern for validation
     */
    private void setupValidation(TextField textField, Label errorLabel, String fieldName, String regex) {
        //Setting text to red for label
    errorLabel.setStyle("-fx-text-fill: red;");
    //Helps handle if user is interacting with field
    textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
        //newValue current state
        if (!newValue) { //!newValue when focus is Lost
            //Pattern.matches uses Regex to check the textfields input
            if(!Pattern.matches(regex, textField.getText())) {
                errorLabel.setText(fieldName + " is invalid");
            }else{
                //if it does match error label is set to
                errorLabel.setText("");
            }
        }

    });
    }

    /**
     * Returns true or false depending if matching the RegEx
     * @param textfield
     * @param regex
     * @return
     */
    private boolean isValidField(TextField textfield, String regex) {
        return Pattern.matches(regex, textfield.getText());
    }

    /**
     * opens new scene with message of all inputs are approved 5
     * @param primaryStage
     */
    private void handleAddButton(Stage primaryStage) {
        primaryStage.setScene(new Scene(new Label("Registration successful"), 200, 200));
    }

    public static void main(String[] args) {
        launch(args);
    }
}