
package com.example.financefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class App extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        try (InputStream is = App.class.getResourceAsStream("images/logo.png")) {
            if (is != null) {
                stage.getIcons().add(new Image(is));
            }
        } catch (Exception ignored) {}
        setRoot("view/login");
        stage.setTitle("FinanceFX");
        stage.show();
    }

    public static void setRoot(String fxmlBaseName) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlBaseName + ".fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(App.class.getResource("css/styles.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.sizeToScene();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar FXML: " + fxmlBaseName, e);
        }
    }

    public static Stage getPrimaryStage() { return primaryStage; }

    public static void main(String[] args) { launch(); }
}
