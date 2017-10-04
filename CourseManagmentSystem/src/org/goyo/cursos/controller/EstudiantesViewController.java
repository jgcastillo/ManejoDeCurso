package org.goyo.cursos.controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.goyo.cursos.modelfx.CursoFx;
import org.goyo.cursos.modelfx.EstudianteFx;
import org.goyo.cursos.modelfx.PeriodoFx;
import org.goyo.cursos.service.CursoService;
import org.goyo.cursos.service.EstudianteService;
import org.goyo.cursos.utilities.Utilities;

/**
 * FXML Controller class
 *
 * @author jgcastillo
 */
public class EstudiantesViewController implements Initializable {

    @FXML
    private ComboBox<CursoFx> cursosComboBox;
    @FXML
    private TextField ciTextField;
    @FXML
    private TextField apellidosTextField;
    @FXML
    private TextField nombresTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField telefonoTextField;
    @FXML
    private Button agregarButton;
    @FXML
    private TableView<EstudianteFx> estudiantesTableView;
    @FXML
    private TableColumn<EstudianteFx, Integer> ciColumn;
    @FXML
    private TableColumn<EstudianteFx, String> apellidosColumn;
    @FXML
    private TableColumn<EstudianteFx, String> nombresColumn;
    @FXML
    private TableColumn<EstudianteFx, String> statusColumn;
    @FXML
    private Label totalLabel;

    private final EstudianteService estudianteService;
    private final CursoService cursoService;

    private CursoFx cursoFxSelected;

    public EstudiantesViewController() {
        this.estudianteService = new EstudianteService();
        this.cursoService = new CursoService();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillCursosComboBox();
        disableFields(true);
    }

    @FXML
    private void handleCargaIndividual(ActionEvent event) {
        disableFields(false);
    }

    @FXML
    private void handleSetCursoFxSelected() {
        cursoFxSelected = cursosComboBox.getSelectionModel().getSelectedItem();
        fillEstudianteTableView(cursoFxSelected);
    }

    @FXML
    private void handleCargaExcel() {
        if (cursosComboBox.getSelectionModel().getSelectedItem() != null) {
            FileChooser fileChooser = new FileChooser();
            configureFileChooser(fileChooser);
            Stage thisStage = (Stage) ciTextField.getScene().getWindow();
            File file = fileChooser.showOpenDialog(thisStage);
            if(estudianteService.saveExcelData(file, cursoFxSelected)){
                Utilities.showAlertDialog(Alert.AlertType.INFORMATION, "Información", 
                        "Datos de estudiantes guardados",
                        "Estudiantes guardados con éxito");
            }
            fillEstudianteTableView(cursoFxSelected);
        } else {
            Utilities.showAlertDialog(Alert.AlertType.ERROR, "Error", "Error", 
                    "Dese seleccionar primero un curso");
        }
    }

    @FXML
    private void handleAgregar(ActionEvent event) {
        if (checkFields()) {
            EstudianteFx estudianteFx = makeEstudianteFx();
            estudianteService.saveEstudiante(estudianteFx);
            fillEstudianteTableView(cursoFxSelected);
            clearFields();
            disableFields(true);
        } else {
            Utilities.showAlertDialog(Alert.AlertType.ERROR, "Error", "Error",
                    "Los campos Curso, C.I., nombres y apellidos son obligatorios");
        }
    }

    @FXML
    private void handleEliminar(ActionEvent event) {
        EstudianteFx estudianteFx = estudiantesTableView.getSelectionModel().getSelectedItem();
        if (estudianteFx != null) {
            estudianteService.deleteEstudiante(estudianteFx);
            fillEstudianteTableView(cursoFxSelected);
        } else {
            Utilities.showAlertDialog(Alert.AlertType.ERROR, "Error", "Error",
                    "Debe seleccionar un estudiante de la lista");
        }
    }

    @FXML
    private void handleCambiaStatus() {
        EstudianteFx estudianteFx = estudiantesTableView.getSelectionModel().getSelectedItem();
        if (estudianteFx != null) {
            if (estudianteFx.getStatus().equals(EstudianteFx.ACTIVOSTR)) {
                estudianteFx.setStatus(EstudianteFx.RETIRADOSTR);
            } else {
                estudianteFx.setStatus(EstudianteFx.ACTIVOSTR);
            }
            estudianteService.updateEstudiante(estudianteFx);
            fillEstudianteTableView(cursoFxSelected);
        } else {
            Utilities.showAlertDialog(Alert.AlertType.ERROR, "Error", "Error",
                    "Debe seleccionar un estudiante de la lista");
        }
    }

    private void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Buscar archivo excel");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLSX", "*.xlsx"),
                new FileChooser.ExtensionFilter("XLS", "*.xls"));
    }

    private EstudianteFx makeEstudianteFx() {
        EstudianteFx estudianteFx = new EstudianteFx();
        estudianteFx.setCi(Integer.parseInt(ciTextField.getText()));
        estudianteFx.setApellido(apellidosTextField.getText());
        estudianteFx.setNombre(nombresTextField.getText());
        estudianteFx.setCorreo((emailTextField.getText().isEmpty()) ? "" : emailTextField.getText());
        estudianteFx.setTelefono((telefonoTextField.getText().isEmpty()) ? "" : telefonoTextField.getText());
        CursoFx cursoFx = cursosComboBox.getSelectionModel().getSelectedItem();
        estudianteFx.setCursoFx(cursoFx);
        return estudianteFx;
    }

    private void fillCursosComboBox() {
        PeriodoFx periodoFx = (PeriodoFx) Context.getContext().get("periodoActivo");
        List<CursoFx> cursosFx = cursoService.getAllCursoFxByPeriodoFx(periodoFx);
        cursosComboBox.getItems().addAll(cursosFx);
    }

    private void fillEstudianteTableView(CursoFx cursoFx) {
        List<EstudianteFx> estudiantesFX = estudianteService.getAllEstudianteFxByCursoFx(cursoFx);
        ObservableList<EstudianteFx> estudiantes = FXCollections.observableArrayList(estudiantesFX);

        estudiantesTableView.setItems(estudiantes);
        ciColumn.setCellValueFactory(cellData
                -> cellData.getValue().ciProperty());
        apellidosColumn.setCellValueFactory(cellData
                -> cellData.getValue().apellidoProperty());
        nombresColumn.setCellValueFactory(cellData
                -> cellData.getValue().nombreProperty());
        statusColumn.setCellValueFactory(cellData
                -> cellData.getValue().statusProperty());
        
        countEstudiantesInCurso(estudiantesFX);
    }
    
    private void countEstudiantesInCurso(List<EstudianteFx> estudiantes){
        int count = estudiantes.size();
        totalLabel.setText("Total estudiantes en el curso: " + count);
    }

    private boolean checkFields() {
        boolean checked = false;
        if (!ciTextField.getText().isEmpty()
                && !nombresTextField.getText().isEmpty()
                && !apellidosTextField.getText().isEmpty()
                && cursosComboBox.getSelectionModel().getSelectedItem() != null) {
            checked = true;
        }
        return checked;
    }

    private void disableFields(boolean disable) {
        ciTextField.setDisable(disable);
        apellidosTextField.setDisable(disable);
        nombresTextField.setDisable(disable);
        emailTextField.setDisable(disable);
        telefonoTextField.setDisable(disable);
        agregarButton.setDisable(disable);
    }

    private void clearFields() {
        ciTextField.clear();
        apellidosTextField.clear();
        nombresTextField.clear();
        emailTextField.clear();
        telefonoTextField.clear();
    }
}
