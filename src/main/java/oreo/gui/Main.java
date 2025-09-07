package oreo.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import oreo.Oreo;
import oreo.OreoException;

/**
 * A GUI for Oreo using FXML.
 */
public class Main extends Application {

    private Oreo oreo = new Oreo("./data/oreo.txt");

    public Main() throws OreoException {
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setOreo(oreo); // inject the Oreo instance

            stage.setTitle("Oreo");
            Image oreoIcon = new Image("/images/oreo.png");
            stage.getIcons().add(oreoIcon);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void end() {
        Platform.exit();
    }
}
