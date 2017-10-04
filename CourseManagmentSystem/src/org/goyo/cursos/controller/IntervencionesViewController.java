/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.goyo.cursos.controller;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.goyo.cursos.modelfx.CursoFx;
import org.goyo.cursos.modelfx.EstudianteFx;
import org.goyo.cursos.modelfx.IntervencionFx;
import org.goyo.cursos.modelfx.PeriodoFx;
import org.goyo.cursos.service.CursoService;
import org.goyo.cursos.service.EstudianteService;
import org.goyo.cursos.service.IntervencionService;

/**
 * FXML Controller class
 *
 * @author jgcastillo
 */
public class IntervencionesViewController implements Initializable {

    @FXML
    private ComboBox<CursoFx> cursosComboBox;

    @FXML
    private ComboBox<IntervencionFx> estudiantesComboBox;

    @FXML
    private TableView<IntervencionFx> intervencionesTableView;

    @FXML
    private TableColumn<IntervencionFx, Integer> ciColumn;

    @FXML
    private TableColumn<IntervencionFx, String> apellidoColumn;

    @FXML
    private TableColumn<IntervencionFx, String> nombreColumn;

    @FXML
    private TableColumn<IntervencionFx, Integer> intervencionesColumn;
    
    private final CursoService cursoService;
    private final EstudianteService estudianteService;
    private final IntervencionService intervencionService;

    public IntervencionesViewController() {
        this.cursoService = new CursoService();
        this.estudianteService = new EstudianteService();
        this.intervencionService = new IntervencionService();
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
        estudiantesComboBox.getItems().clear();
        CursoFx cursoFx = cursosComboBox.getSelectionModel().getSelectedItem();
        List<IntervencionFx> intervencionesFx = intervencionService.findAllIntervenciones(cursoFx);
        intervencionesFx.sort((IntervencionFx intr1,IntervencionFx intr2) ->{
            return intr1.getEstudianteFx().getApellido().compareTo(intr2.getEstudianteFx().getApellido());
        });
        estudiantesComboBox.getItems().addAll(intervencionesFx);
        fillIntervencionesTableView();
    }
    
    @FXML
    private void handleLoteriaButton(ActionEvent event){
        ObservableList<IntervencionFx> estudiantes = estudiantesComboBox.getItems();
        int max = estudiantes.size();
        Random r = new Random((new Date()).getTime());
        int winner = r.nextInt(max);
        try {
            Thread.sleep(2000);
            estudiantesComboBox.getSelectionModel().select(estudiantes.get(winner));
        } catch (InterruptedException e) {
        }
        
    }
    
    @FXML
    private void handlePlusButtonAction(ActionEvent event){
        IntervencionFx intrv = estudiantesComboBox.getSelectionModel().getSelectedItem();
        int value = intrv.getCantidad() + 1;
        intrv.setCantidad(value);
        intervencionService.updateIntervencion(intrv);
        fillIntervencionesTableView();
    }
    
    @FXML
    private void handleMinusButtonAction(ActionEvent event) {
        IntervencionFx intrv = estudiantesComboBox.getSelectionModel().getSelectedItem();
        int value = intrv.getCantidad() - 1;
        intrv.setCantidad(value);
        intervencionService.updateIntervencion(intrv);
        fillIntervencionesTableView();
    }
    
    private void fillCursosComboBox() {
        PeriodoFx periodoFx = (PeriodoFx) Context.getContext().get("periodoActivo");
        List<CursoFx> cursosFx = cursoService.getAllCursoFxByPeriodoFx(periodoFx);
        cursosComboBox.getItems().addAll(cursosFx);
    }
    
    private void fillIntervencionesTableView(){
        CursoFx cursoFx = cursosComboBox.getSelectionModel().getSelectedItem();
        ObservableList<IntervencionFx> intervenciones = intervencionService.findAllIntervenciones(cursoFx);
        intervencionesTableView.setItems(intervenciones);
        ciColumn.setCellValueFactory(cellData 
                -> cellData.getValue().ciEstudianteFxProperty());
        nombreColumn.setCellValueFactory(cellData 
                -> cellData.getValue().nombreEstudianteProperty());
        apellidoColumn.setCellValueFactory(cellData 
                -> cellData.getValue().apellidoEstudianteProperty());
        intervencionesColumn.setCellValueFactory(cellData 
                -> cellData.getValue().cantidadProperty());
    }
    
}
