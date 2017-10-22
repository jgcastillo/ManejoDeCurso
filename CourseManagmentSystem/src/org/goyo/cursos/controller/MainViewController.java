package org.goyo.cursos.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.goyo.cursos.modelfx.PeriodoFx;
import org.goyo.cursos.service.PeriodoService;

/**
 *
 * @author jgcastillo
 */
public class MainViewController implements Initializable {

    @FXML
    private VBox workArea;
    @FXML
    private AnchorPane statusPanel;
    @FXML
    private TextField periodoActivoTextField;
    @FXML
    private TextField cursoActivoTextField;

    private final PeriodoService periodoService;

    public MainViewController() {
        this.periodoService = new PeriodoService();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadEmptyPane();
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        updateActivePeriod();
    }

    @FXML
    private void handleSalirAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void loadEmptyPane() throws IOException {
        setWorkPane(fadeAnimate("/org/goyo/cursos/view/EmptyView.fxml"));
    }
    
    @FXML
    private void loadPeriodoView() throws IOException {
        setWorkPane(fadeAnimate("/org/goyo/cursos/view/PeriodoView.fxml"));
    }
    
    @FXML
    private void loadCursosView() throws IOException {
        setWorkPane(fadeAnimate("/org/goyo/cursos/view/CursosView.fxml"));
    }
    
    @FXML
    private void loadEstudiantesView() throws IOException {
        setWorkPane(fadeAnimate("/org/goyo/cursos/view/EstudiantesView.fxml"));
    }
    
    @FXML
    private void loadAsistenciaView() throws IOException {
        setWorkPane(fadeAnimate("/org/goyo/cursos/view/AsistenciaView.fxml"));
    }
    
    @FXML
    private void loadEvaluacionesView() throws IOException {
        setWorkPane(fadeAnimate("/org/goyo/cursos/view/EvaluacionesView.fxml"));
    }
    
    @FXML
    private void loadTipoEvaluacionesView() throws IOException {
        setWorkPane(fadeAnimate("/org/goyo/cursos/view/TipoEvaluacionesView.fxml"));
    }
    
    @FXML
    private void loadIntervencionesView() throws IOException {
        setWorkPane(fadeAnimate("/org/goyo/cursos/view/IntervencionesView.fxml"));
    }
    
    @FXML
    private void loadCargaNotasView() throws IOException {
        setWorkPane(fadeAnimate("/org/goyo/cursos/view/CargaNotasView.fxml"));
    }

    public void updateActivePeriod() {
        Optional<PeriodoFx> periodoFx = periodoService.getPeriodoActivo();
        if (periodoFx.isPresent()) {
            Context.getContext().put("periodoActivo", periodoFx.get());
            periodoActivoTextField.setText(periodoFx.get().getNombre());
        }
    }

    private void setWorkPane(Node node) {
        // actualiza el contenido dfel área de trabajo de acuerdo a la selección
        // que haga el usuario
        workArea.getChildren().setAll(node);
    }

    private VBox fadeAnimate(String url) throws IOException {
        VBox dataPane = (VBox) FXMLLoader.load(getClass().getResource(url));
        FadeTransition ft = new FadeTransition(Duration.millis(750));
        ft.setNode(dataPane);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
        return dataPane;
    }
}
