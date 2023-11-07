import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Window extends Application {


    @Override
    public void start(Stage stage) throws Exception {

        Group root = new Group();
        Scene scene = new Scene(root, 900, 600);

        Text text = new Text(10, 10, "hello world");

        root.getChildren().add(text);


        stage.setScene(scene);

        stage.setTitle("Audio Processing Project");
        stage.show();

    }

    public static void main(String[] args) {

        launch(args);
    }
}
