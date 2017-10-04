package org.goyo.cursos.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.goyo.cursos.modelfx.CursoFx;
import org.goyo.cursos.modelfx.PeriodoFx;
import org.goyo.cursos.service.CursoService;
import org.goyo.cursos.service.PeriodoService;
import org.goyo.cursos.utilities.Utilities;

public class PeriodoViewController implements Initializable {
    
    @FXML
    private TextField nombreTextField;
    @FXML
    private TableView<PeriodoFx> periodosTableView;
    @FXML
    private TableColumn<PeriodoFx, String> nombreTableColumn;
    @FXML
    private TableColumn<PeriodoFx, String> statusTableColumn;

    private final PeriodoService periodoService;
    private final CursoService cursoService;

    public PeriodoViewController() {
        this.periodoService = new PeriodoService();
        this.cursoService = new CursoService();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillPeriodosTable();
    }    
    
    @FXML
    private void savePeriodo(){
        PeriodoFx periodoFx = new PeriodoFx();
        periodoFx.setNombre(nombreTextField.getText());
        periodoFx.setStatus(PeriodoFx.ACTIVOSTR);
        periodoService.savePeriodo(periodoFx);
        clearFields();
        fillPeriodosTable();
    }
    
    @FXML
    private void deletePeriodo(){
        PeriodoFx selected = periodosTableView.getSelectionModel().getSelectedItem();
        List<CursoFx> cursos = cursoService.getAllCursoFxByPeriodoFx(selected);
        if(cursos.isEmpty()){
            periodoService.deletePeriod(selected);
            fillPeriodosTable();
        } else {
            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Advertencia", 
                    "Advertencia", "El período seleccionado no se puede eliminar"
                            + " ya que contiene cursos definidos");
        }
    }
    
    @FXML
    private void changePeriodoStatus(){
        PeriodoFx selected = periodosTableView.getSelectionModel().getSelectedItem();
        if(selected.getStatus().equals(PeriodoFx.ACTIVOSTR)){
            selected.setStatus(PeriodoFx.INACTIVOSTR);
        } else {
            selected.setStatus(PeriodoFx.ACTIVOSTR);
        }
        
        periodoService.updatePeriod(selected);
        
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(PeriodoViewController.class.getResource("/org/goyo/cursos/view/MainView.fxml"));
//        System.out.println(loader.getLocation());
//        mainViewController = loader.getController();
//        mainViewController.updateActivePeriod();
    }
    
    private void clearFields(){
        nombreTextField.clear();
    }
    
    private void fillPeriodosTable(){
        List<PeriodoFx> periodoFxList = periodoService.getAllPeriodoFx();
        ObservableList<PeriodoFx> periodosFx = FXCollections
                                        .observableArrayList(periodoFxList);
        periodosTableView.setItems(periodosFx);
        nombreTableColumn.setCellValueFactory(cellData -> 
                cellData.getValue().nombreProperty());
        statusTableColumn.setCellValueFactory(cellData ->
                cellData.getValue().statusProperty());
    }
    
}
