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
import org.goyo.cursos.modelfx.TipoEvaluacionFx;
import org.goyo.cursos.service.CursoService;
import org.goyo.cursos.service.EvaluacionesService;
import org.goyo.cursos.service.TipoEvaluacionService;

/**
 * FXML Controller class
 *
 * @author jgcastillo
 */
public class EvaluacionesViewController implements Initializable {

    @FXML
    private ComboBox<CursoFx> cursosComboBox;
    @FXML
    private ComboBox<TipoEvaluacionFx> tipoComboBox;
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
    private final TipoEvaluacionService tipoEvaluacionService;
    private final CursoService cursoService;

    public EvaluacionesViewController() {
        this.evaluacionesService = new EvaluacionesService();
        this.cursoService = new CursoService();
        this.tipoEvaluacionService = new TipoEvaluacionService();
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
    private void handleCursoSelectedChange() {
        fillEvaluacionesTableView();
    }

    @FXML
    private void handleTipoSelectedChange() {
        TipoEvaluacionFx tipoSelected = tipoComboBox.getSelectionModel().getSelectedItem();
        if (tipoSelected.getAcumulado().equals(TipoEvaluacionFx.ACUMULADOSTR)) {
            acumuladoTextField.setDisable(false);
            acumuladoTextField.setPromptText("% Acumulado de estas evaluaciones");
            pesoTextField.setDisable(true);
        } else {
            acumuladoTextField.setDisable(true);
            acumuladoTextField.setPromptText("sólo evaluaciones acumuladas");
            pesoTextField.setDisable(false);
        }
    }

    @FXML
    private void handleCrearEvaluacionFx() {
        EvaluacionFx evaluacionFx = makeEvaluacionFxFromView();
        evaluacionFx = evaluacionesService.saveEvaluacionFx(evaluacionFx);
        fillEvaluacionesTableView();
        clearFields();
    }
    
    @FXML
    private void handleEliminarEvaluacionFx(){
        EvaluacionFx evaluacionFx = evaluacionesTableView.getSelectionModel()
                                                         .getSelectedItem();
        
        System.out.println("Va a eliminar a " + evaluacionFx.getNombre());
        evaluacionesService.deleteEvaluacionFx(evaluacionFx);
        
        
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
        TipoEvaluacionFx tipoEval = tipoComboBox.getSelectionModel().getSelectedItem();
        evaluacionFx.setTipoEvaluacionFx(tipoEval);
        evaluacionFx.setNombre(nombreTextField.getText());
        evaluacionFx.setFecha(datePicker.getValue());
        if(!pesoTextField.getText().isEmpty()){
            evaluacionFx.setPeso(Double.parseDouble(pesoTextField.getText()));
        } else {
            evaluacionFx.setPesoAcumulado(Double.parseDouble(acumuladoTextField.getText()));
        }
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
            fechaColumn.setCellValueFactory(cellData
                    -> cellData.getValue().fechaProperty());
            nombreColumn.setCellValueFactory(cellData
                    -> cellData.getValue().nombreProperty());
            pesoColumn.setCellValueFactory(cellData
                    -> cellData.getValue().pesoProperty());
            statusColumn.setCellValueFactory(cellData
                    -> cellData.getValue().statusProperty());
        }
    }
    
    private void clearFields(){
        tipoComboBox.getSelectionModel().clearSelection();
        datePicker.setValue(null);
        nombreTextField.clear();
        pesoTextField.clear();
        acumuladoTextField.clear();
    }
}
