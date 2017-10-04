/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.goyo.cursos.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.goyo.cursos.modelfx.AsistenciaDiariaFx;
import org.goyo.cursos.modelfx.AsistenciaFx;
import org.goyo.cursos.modelfx.CursoFx;
import org.goyo.cursos.modelfx.PeriodoFx;
import org.goyo.cursos.service.AsistenciaService;
import org.goyo.cursos.service.CursoService;
import org.goyo.cursos.utilities.Utilities;

/**
 * FXML Controller class
 *
 * @author jgcastillo
 */
public class AsistenciaViewController implements Initializable {

    @FXML
    private ComboBox<CursoFx> cursosComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<AsistenciaFx> asistenciaTableView;
    @FXML
    private TableColumn<AsistenciaFx, Integer> ciColumn;
    @FXML
    private TableColumn<AsistenciaFx, String> apellidoColumn;
    @FXML
    private TableColumn<AsistenciaFx, String> nombreColumn;
    @FXML
    private TableColumn<AsistenciaFx, Boolean> asistenciaColumn;
    @FXML
    private Label totalEstudiantesLabel;
    @FXML
    private TableView<AsistenciaDiariaFx> asistentesXFechaTableView;
    @FXML
    private TableColumn<AsistenciaDiariaFx, LocalDate> fechaColumn;
    @FXML
    private TableColumn<AsistenciaDiariaFx, Integer> asistentesColumn;

    private final AsistenciaService asistenciaService;
    private final CursoService cursoService;

    public AsistenciaViewController() {
        this.asistenciaService = new AsistenciaService();
        this.cursoService = new CursoService();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillCursosComboBox();
        datePicker.setValue(LocalDate.now());
        asistentesXFechaTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> handleAsistenciaTableChange(newValue));
    }

    @FXML
    void handleCursosComboBoxAction() {
        CursoFx cursoFx = cursosComboBox.getSelectionModel().getSelectedItem();
        asistenciaTableView.setEditable(true);
        ObservableList<AsistenciaFx> lista = asistenciaService.findEstudiantesByCurso(cursoFx);
        fillAsistenciaTableView(lista);
        fillAsistentesXFechaTableView(cursoFx);
        totalEstudiantesLabel.setText("Alumnos en el curso: " + asistenciaTableView.getItems().size());
    }

    @FXML
    private void fillAsistenciaTableView(ObservableList<AsistenciaFx> lista) {
        asistenciaTableView.setItems(lista);
        ciColumn.setCellValueFactory(cellData
                -> cellData.getValue().ciEstudianteFxProperty());
        apellidoColumn.setCellValueFactory(cellData
                -> cellData.getValue().apellidoEstudianteProperty());
        nombreColumn.setCellValueFactory(cellData
                -> cellData.getValue().nombreEstudianteProperty());
        asistenciaColumn.setCellFactory(CheckBoxTableCell.forTableColumn(asistenciaColumn));
        asistenciaColumn.setCellValueFactory(new PropertyValueFactory<>("asistencia"));
    }

    private void fillAsistentesXFechaTableView(CursoFx cursoFx) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        asistentesXFechaTableView.setItems(asistenciaService.findAsistentesByCurso(cursoFx));
        fechaColumn.setCellValueFactory(cellData
                -> cellData.getValue().fechaProperty());
        fechaColumn.setCellFactory(col -> new TableCell<AsistenciaDiariaFx, LocalDate>() {

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format(item.format(formatter)));
                }
            }

        });
        asistentesColumn.setCellValueFactory(cellData
                -> cellData.getValue().cantidadProperty());
    }

    private void handleAsistenciaTableChange(AsistenciaDiariaFx asisFx) {
        if (asisFx != null) {
            asistenciaTableView.setEditable(false);
            LocalDate ld = asisFx.getFecha();
            CursoFx cursoFx = cursosComboBox.getSelectionModel().getSelectedItem();
            ObservableList<AsistenciaFx> lista = asistenciaService.findAsistentesByCursoAndDate(cursoFx, ld);
            fillAsistenciaTableView(lista);
            totalEstudiantesLabel.setText("Alumnos en el curso: " + asistenciaTableView.getItems().size());
        }
    }

    @FXML
    private void saveAsistencia() {
        CursoFx cursoFx = cursosComboBox.getSelectionModel().getSelectedItem();
        LocalDate fecha = datePicker.getValue();
        asistenciaService.saveAsistencia(asistenciaTableView.getItems(),
                cursoFx, fecha);
        Utilities.showAlertDialog(Alert.AlertType.INFORMATION,
                "Información", "Asistencia tomada", "Asistencia tomada con éxito");
    }

    private void fillCursosComboBox() {
        PeriodoFx periodoFx = (PeriodoFx) Context.getContext().get("periodoActivo");
        List<CursoFx> cursosFx = cursoService.getAllCursoFxByPeriodoFx(periodoFx);
        cursosComboBox.getItems().addAll(cursosFx);
    }
}
