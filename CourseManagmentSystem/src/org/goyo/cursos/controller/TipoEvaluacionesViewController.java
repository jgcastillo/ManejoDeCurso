/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.goyo.cursos.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.goyo.cursos.model.TipoEvaluacion;
import org.goyo.cursos.modelfx.TipoEvaluacionFx;
import org.goyo.cursos.service.TipoEvaluacionService;
import org.goyo.cursos.utilities.Utilities;

/**
 * FXML Controller class
 *
 * @author jgcastillo
 */
public class TipoEvaluacionesViewController implements Initializable {

    @FXML
    private TextField nombreTextField;

    @FXML
    private CheckBox acumulativoCheckBox;

    @FXML
    private TableView<TipoEvaluacionFx> tiposTableView;

    @FXML
    private TableColumn<TipoEvaluacionFx, String> nombreColumn;

    @FXML
    private TableColumn<TipoEvaluacionFx, String> acumulativoColumn;

    private final TipoEvaluacionService tipoEvaluacionService;
    
    public TipoEvaluacionesViewController() {
        this.tipoEvaluacionService = new TipoEvaluacionService();
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTipoEvaluacionTableView();
    }    
    
    @FXML
    private void saveTipoEvaluacion(){
        TipoEvaluacionFx tipoEvalFx = tipoEvaluacionService
                                    .saveTipoEvaluacion(makeTipoEvaluacionFx());
        nombreTextField.clear();
        acumulativoCheckBox.setSelected(false);
        fillTipoEvaluacionTableView();
    }
    
    @FXML
    private void deleteTipoEvaluacion(){
        TipoEvaluacionFx te = tiposTableView.getSelectionModel().getSelectedItem();
        tipoEvaluacionService.deletTipoEvaluacion(te);
        Utilities.showAlertDialog(Alert.AlertType.INFORMATION, "Información", 
                "Tipo de Evalaución Eliminado", 
                "El tipo de evaluación " + te.getNombre() + " ha sido eliminado con éxito");
        fillTipoEvaluacionTableView();
    }
    
    private TipoEvaluacionFx makeTipoEvaluacionFx(){
        TipoEvaluacionFx tipoEvalFx = new TipoEvaluacionFx();

        tipoEvalFx.setNombre(nombreTextField.getText());
        tipoEvalFx.setAcumulado(acumulativoCheckBox.isSelected()
                ? TipoEvaluacionFx.ACUMULADOSTR: TipoEvaluacionFx.NOACUMULADOSTR);
        return tipoEvalFx;
    }
    
    private void fillTipoEvaluacionTableView(){
        ObservableList<TipoEvaluacionFx> tipos = tipoEvaluacionService.findAllTipoEvaluacion();
        tiposTableView.setItems(tipos);
        nombreColumn.setCellValueFactory(cellData ->
                cellData.getValue().nombreProperty());
        acumulativoColumn.setCellValueFactory(cellData ->
                cellData.getValue().acumuladoProperty());
    }
}
