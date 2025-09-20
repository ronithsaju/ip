package oreo.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Creates a DialogBox with the specified text and image.
     * @param text The text to be displayed.
     * @param img The image to be displayed.
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     * @param isError True if the dialog box represents an error message, false otherwise.
     */
    private void flip(boolean isError) {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);

        // red dialog label for error messages
        if (isError) {
            dialog.getStyleClass().add("error-reply-label");
            return;
        }
        dialog.getStyleClass().add("reply-label");
    }

    /**
     * Creates a dialog box for the user.
     * @param text The user text to be displayed.
     * @param img The user image to be displayed.
     * @return A dialog box for the user.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Creates a dialog box for Oreo.
     * @param text The Oreo text to be displayed.
     * @param img The Oreo image to be displayed.
     * @param isError True if the dialog box represents an error message, false otherwise.
     * @return A dialog box for Oreo.
     */
    public static DialogBox getOreoDialog(String text, Image img, boolean isError) {
        var db = new DialogBox(text, img);
        db.flip(isError);
        return db;
    }
}
