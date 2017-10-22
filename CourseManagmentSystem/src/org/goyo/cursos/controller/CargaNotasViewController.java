package org.goyo.cursos.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.goyo.cursos.modelfx.CargaNotaFx;
import org.goyo.cursos.modelfx.CursoFx;
import org.goyo.cursos.modelfx.EstudianteFx;
import org.goyo.cursos.modelfx.EvaluacionFx;
import org.goyo.cursos.modelfx.NotaFx;
import org.goyo.cursos.modelfx.PeriodoFx;
import org.goyo.cursos.service.CursoService;
import org.goyo.cursos.service.EstudianteService;
import org.goyo.cursos.service.EvaluacionesService;

/**
 * FXML Controller class
 *
 * @author jgcastillo
 */
public class CargaNotasViewController implements Initializable {

    @FXML
    private ComboBox<CursoFx> cursosComboBox;
    @FXML
    private ComboBox<EvaluacionFx> evaluacionesComboBox;
    @FXML
    private TableView<CargaNotaFx> notasTableView;
    @FXML
    private TableColumn<CargaNotaFx, Integer> ciColumn;
    @FXML
    private TableColumn<CargaNotaFx, EstudianteFx> alumnoColumn;
    @FXML
    private TableColumn<CargaNotaFx, Double> notaColumn;
    
    private final CursoService cursoService; 
    private final EvaluacionesService evaluacionesService;
    private final EstudianteService estudianteService;

    public CargaNotasViewController() {
        this.cursoService = new CursoService();
        this.evaluacionesService = new EvaluacionesService();
        this.estudianteService = new EstudianteService();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillCursosComboBox();
    }    
    
    @FXML
    private void handleCursosComboBoxAction(ActionEvent event){
        CursoFx cursoFx = cursosComboBox.getSelectionModel().getSelectedItem();
        fillEvaluacionesComboBox(cursoFx);
    }
    
    @FXML
    private void handleEvaluacionesComboBoxAction(ActionEvent event){
        EvaluacionFx evalFx = evaluacionesComboBox.getSelectionModel().getSelectedItem();
        ObservableList<CargaNotaFx> notasFx =  findAllAlumnosByEvaluacion(evalFx);
        fillNotasTableView(notasFx);
    }
    
    private void fillCursosComboBox() {
        PeriodoFx periodoFx = (PeriodoFx) Context.getContext().get("periodoActivo");
        List<CursoFx> cursosFx = cursoService.getAllCursoFxByPeriodoFx(periodoFx);
        cursosComboBox.getItems().addAll(cursosFx);
    }
    
    private void fillEvaluacionesComboBox(CursoFx cursoFx){
        ObservableList<EvaluacionFx> evaluacionesFx = evaluacionesService
                                        .getAllEvaluacionFxByCursoFx(cursoFx);
        evaluacionesComboBox.getItems().addAll(evaluacionesFx);
    }
    
    private void fillNotasTableView(ObservableList<CargaNotaFx> notasFx){
        notasTableView.setItems(notasFx);
        ciColumn.setCellValueFactory(cellData 
                -> cellData.getValue().ciProperty());
        alumnoColumn.setCellValueFactory(celldata
                -> celldata.getValue().alumnoProperty());
        notaColumn.setCellValueFactory(cellData
                -> cellData.getValue().notaProperty());
    }
    
    private ObservableList<CargaNotaFx> findAllAlumnosByEvaluacion(EvaluacionFx evalFx){
        CursoFx cursoFx = evalFx.getCursoFx();
        List<EstudianteFx> estudiantesFx = estudianteService
                .getAllEstudianteFxByCursoFx(cursoFx);
        ObservableList<CargaNotaFx> notasFx = FXCollections.observableArrayList();
        estudiantesFx.stream().forEach(est -> {
            CargaNotaFx cargaNota = new CargaNotaFx();
            cargaNota.setCi(est.getCi());
            cargaNota.setAlumno(est);
            cargaNota.setNota(0.0);
            notasFx.add(cargaNota);
        });
        return notasFx;
    }
}
