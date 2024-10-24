module org.example.regex_javadoc {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.regex_javadoc to javafx.fxml;
    exports org.example.regex_javadoc;
}