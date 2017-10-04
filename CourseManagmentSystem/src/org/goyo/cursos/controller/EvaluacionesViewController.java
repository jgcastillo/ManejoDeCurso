package org.goyo.cursos.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.goyo.cursos.modelfx.CursoFx;
import org.goyo.cursos.modelfx.EvaluacionFx;
import org.goyo.cursos.modelfx.PeriodoFx;
import org.goyo.cursos.service.CursoService;
import org.goyo.cursos.service.EvaluacionesService;

/**
 * FXML Controller class
 *
 * @author jgcastillo
 */
public class EvaluacionesViewController implements Initializable {

    @FXML
    private ComboBox<CursoFx> cursosComboBox;
    @FXML
    private ComboBox<String> tipoComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField pesoTextField;
    @FXML
    private TextField acumuladoTextField;
    @FXML
    private TableView<EvaluacionFx> evaluacionesTableView;
    @FXML
    private TableColumn<EvaluacionFx, LocalDate> fechaColumn;
    @FXML
    private TableColumn<EvaluacionFx, String> nombreColumn;
    @FXML
    private TableColumn<EvaluacionFx, Double> pesoColumn;
    @FXML
    private TableColumn<EvaluacionFx, String> statusColumn;

    private final EvaluacionesService evaluacionesService;
    private final CursoService cursoService;

    public EvaluacionesViewController() {
        this.evaluacionesService = new EvaluacionesService();
        this.cursoService = new CursoService();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillCursosComboBox();
        fillTipoComboBox();
        acumuladoTextField.setDisable(true);
    }
    
    @FXML
    private void handleCursoSelectedChange(){
        fillEvaluacionesTableView();
    }
    
    @FXML
    private void handleTipoSelectedChange(){
        String tipoSelected = tipoComboBox.getSelectionModel().getSelectedItem();
        if(tipoSelected.equals("Quiz") || tipoSelected.equals("Tarea")){
            acumuladoTextField.setDisable(false);
            acumuladoTextField.setPromptText("% Acumulado de estas evaluaciones");
            pesoTextField.setDisable(true);
        } else {
            acumuladoTextField.setDisable(true);
            acumuladoTextField.setPromptText("solo evaluaciones acumuladas");
            pesoTextField.setDisable(false);
        }
    }

    @FXML
    private void handleCrearEvaluacionFx() {
        EvaluacionFx evaluacionFx = makeEvaluacionFxFromView();
        evaluacionFx = evaluacionesService.saveEvaluacionFx(evaluacionFx);
        fillEvaluacionesTableView();
    }

    private void fillCursosComboBox() {
        PeriodoFx periodoFx = (PeriodoFx) Context.getContext().get("periodoActivo");
        List<CursoFx> cursosFx = cursoService.getAllCursoFxByPeriodoFx(periodoFx);
        cursosComboBox.getItems().addAll(cursosFx);
    }

    private void fillTipoComboBox() {
        tipoComboBox.setItems(evaluacionesService.getEvaluacionesTipo());
    }

    private EvaluacionFx makeEvaluacionFxFromView() {
        EvaluacionFx evaluacionFx = new EvaluacionFx();
        int tipo = evaluacionesService.getTipoInt(tipoComboBox.getSelectionModel().getSelectedItem());
        evaluacionFx.setTipo(tipo);
        evaluacionFx.setNombre(nombreTextField.getText());
        evaluacionFx.setFecha(datePicker.getValue());
        evaluacionFx.setPeso(Double.parseDouble(pesoTextField.getText()));
        evaluacionFx.setCursoFx(cursosComboBox.getSelectionModel().getSelectedItem());
        evaluacionFx.setStatus(evaluacionesService.getStatusString(EvaluacionFx.PLANIFICADO));
        return evaluacionFx;
    }

    private void fillEvaluacionesTableView() {
        CursoFx cursoFx = cursosComboBox.getSelectionModel().getSelectedItem();
        if (cursoFx != null) {
            ObservableList<EvaluacionFx> evaluacionesFx
                    = evaluacionesService.getAllEvaluacionFxByCursoFx(cursoFx);
            evaluacionesTableView.setItems(evaluacionesFx);
            fechaColumn.setCellValueFactory(cellData ->
                    cellData.getValue().fechaProperty());
            nombreColumn.setCellValueFactory(cellData ->
                    cellData.getValue().nombreProperty());
            pesoColumn.setCellValueFactory(cellData
                    -> cellData.getValue().pesoProperty());
            statusColumn.setCellValueFactory(cellData ->
                    cellData.getValue().statusProperty());
        }

    }
}
