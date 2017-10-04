package org.goyo.cursos.view;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    
    private Stage mainStage;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.mainStage = primaryStage;
        
        Parent root = FXMLLoader.load(getClass().getResource("/org/goyo/cursos/view/MainView.fxml"));
        
        Scene scene = new Scene(root);
        primaryStage.setTitle("Administración de Cursos");
        //primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
