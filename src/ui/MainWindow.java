package ui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;


public class MainWindow extends Application {


    @Override
    public void start(Stage stage) throws Exception {

        Group root = new Group();
        Scene scene = new Scene(root, 900, 600);

        Text text = new Text(10, 10, "hello world");

        Button btn = new Button("cc");

        root.getChildren().addAll(btn, text);




        stage.setScene(scene);

        stage.setTitle("Audio Processing Project");
        stage.show();

    }

    public static void main(String[] args) {

        launch(args);
    }
}
