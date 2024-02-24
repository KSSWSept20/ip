package tes;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tes.gui.MainWindow;

// Solution below adapted from https://se-education.org/guides/tutorials/javaFx.html
/**
 * A GUI for Tes using FXML.
 */
public class Main extends Application {

    private Tes tes = new Tes();
    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Tes");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            MainWindow controller = fxmlLoader.<MainWindow>getController();
            controller.setTes(tes);
            controller.setMainApp(this);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the Help Window.
     */
    public void showHelpWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/HelpWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Stage helpStage = new Stage();
            helpStage.setTitle("Help - A Simple Guide");
            helpStage.initModality(Modality.WINDOW_MODAL);
            helpStage.initOwner(primaryStage);
            Scene helpScene = new Scene(ap);
            helpStage.setScene(helpScene);
            helpStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
