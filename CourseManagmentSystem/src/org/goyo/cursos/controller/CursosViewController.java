package org.goyo.cursos.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.goyo.cursos.modelfx.CursoFx;
import org.goyo.cursos.modelfx.EstudianteFx;
import org.goyo.cursos.modelfx.PeriodoFx;
import org.goyo.cursos.service.CursoService;
import org.goyo.cursos.service.EstudianteService;
import org.goyo.cursos.service.PeriodoService;
import org.goyo.cursos.utilities.Utilities;

public class CursosViewController implements Initializable {

    @FXML
    private ComboBox<PeriodoFx> periodosComboBox;
    @FXML
    private TextField nombreCursoTextField;
    @FXML
    private TextField nrcTextField;
    @FXML
    private TableView<CursoFx> cursosTableView;
    @FXML
    private TableColumn<CursoFx, String> cursoTableColumn;
    @FXML
    private TableColumn<CursoFx, String> nrcTableColumn;
    @FXML
    private TableColumn<CursoFx, PeriodoFx> periodoTableColum;
    @FXML
    private TableColumn<CursoFx, String> statusTableColum;

    private final PeriodoService periodoService;
    private final CursoService cursoService;
    private final EstudianteService estudianteService;
    
    public CursosViewController() {
        this.periodoService = new PeriodoService();
        this.cursoService = new CursoService();
        this.estudianteService = new EstudianteService();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillPeriodosComboBox();
        fillCursosTableView();
    }    
    
    @FXML
    private void saveCurso(){
        CursoFx cursoFx = new CursoFx();
        cursoFx.setPeriodoFx(periodosComboBox.getSelectionModel().getSelectedItem());
        cursoFx.setNombre(nombreCursoTextField.getText());
        cursoFx.setNrc(nrcTextField.getText());
        cursoFx.setStatus(CursoFx.ACTIVOSTR);
        cursoService.saveCurso(cursoFx);
        clearFields();
        fillCursosTableView();
    }
    
    @FXML
    private void deleteCurso() {
        CursoFx selected = cursosTableView.getSelectionModel().getSelectedItem();
        List<EstudianteFx> estudiantes = estudianteService.getAllEstudianteFxByCursoFx(selected);
        if (estudiantes.isEmpty()) {
            cursoService.deleteCurso(selected);
            cursosTableView.getSelectionModel().clearSelection();
            fillCursosTableView();
        } else {
            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Advertencia",
                    "Advertencia", "El curso seleccionado no se puede eliminar"
                    + " ya que contiene alumnos definidos");
        }
    }
    
    @FXML
    private void changeCursoStatus() {
        CursoFx selected = cursosTableView.getSelectionModel().getSelectedItem();
        if (selected.getStatus().equals(CursoFx.ACTIVOSTR)) {
            selected.setStatus(CursoFx.INACTIVOSTR);
        } else {
            selected.setStatus(CursoFx.ACTIVOSTR);
        }

        cursoService.updateCurso(selected);
    }
    
    private void clearFields(){
        periodosComboBox.getSelectionModel().clearSelection();
        nombreCursoTextField.clear();
        nrcTextField.clear();
    }
    
    private void fillPeriodosComboBox(){
        List<PeriodoFx> periodos = periodoService.getAllPeriodoFx();
        periodosComboBox.getItems().addAll(periodos);
    }
    
    private void fillCursosTableView(){
        List<CursoFx> cursos = cursoService.getAllCursoFx();
        cursosTableView.getItems().clear();
        ObservableList<CursoFx> cursosFx = FXCollections
                .observableArrayList(cursos);

        cursosTableView.getItems().addAll(cursosFx);
        
        cursoTableColumn.setCellValueFactory(cellData ->
                cellData.getValue().nombreProperty());
        nrcTableColumn.setCellValueFactory(cellData ->
                cellData.getValue().nrcProperty());
        periodoTableColum.setCellValueFactory(cellData ->
                cellData.getValue().periodoFxProperty());
        statusTableColum.setCellValueFactory(cellData ->
                cellData.getValue().statusProperty());
    }
}
