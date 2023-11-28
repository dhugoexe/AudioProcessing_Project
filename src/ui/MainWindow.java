package ui;

import audio.AudioIO;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow extends Application {


    private int app_length = 3440;
    private int app_wight = 1440;
    final Button button = new Button ("Connect sources");


    @Override public void start(Stage stage) {
        stage.setTitle("ComboBoxSample");
        Scene scene = new Scene(new Group(), app_length, app_wight);

        final ComboBox inputComboBox = new ComboBox();
        inputComboBox.getItems().addAll(AudioIO.getAudioMixers());

        final ComboBox outputComboBox = new ComboBox();
        outputComboBox.getItems().addAll(AudioIO.getAudioMixers());


        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("Input: "), 0, 0);
        grid.add(inputComboBox, 1, 0);
        grid.add(new Label("Output: "), 2, 0);
        grid.add(outputComboBox, 3, 0);
        grid.add(button, 4, 0);


        Group root = (Group)scene.getRoot();
        root.getChildren().add(grid);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
