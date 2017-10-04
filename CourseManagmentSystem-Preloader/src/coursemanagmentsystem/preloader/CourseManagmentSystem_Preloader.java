/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursemanagmentsystem.preloader;

import java.util.List;
import javafx.application.Preloader;
import javafx.application.Preloader.ProgressNotification;
import javafx.application.Preloader.StateChangeNotification;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.goyo.cursos.dao.PeriodoFacade;
import org.goyo.cursos.model.Periodo;

/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author jgcastillo
 */
public class CourseManagmentSystem_Preloader extends Preloader {
    
    ProgressBar bar;
    Stage stage;
    
    private PeriodoFacade periodoFacade = new PeriodoFacade();
    
    private Scene createPreloaderScene() {
        bar = new ProgressBar();
        BorderPane p = new BorderPane();
        p.setCenter(bar);
        return new Scene(p, 300, 150);        
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setScene(createPreloaderScene());        
        stage.show();
        
        //initDatabase();
    }
    
    @Override
    public void handleStateChangeNotification(StateChangeNotification scn) {
        if (scn.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    }
    
    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        bar.setProgress(pn.getProgress());
    }    
    
    private void initDatabase(){
        List<Periodo> facade = periodoFacade.findAll();
    }
}
