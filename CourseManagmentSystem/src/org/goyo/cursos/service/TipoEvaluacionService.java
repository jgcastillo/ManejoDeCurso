package org.goyo.cursos.service;

import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.goyo.cursos.dao.TipoEvaluacionFacade;
import org.goyo.cursos.model.TipoEvaluacion;
import org.goyo.cursos.modelfx.TipoEvaluacionFx;

public class TipoEvaluacionService {

    private final TipoEvaluacionFacade tipoEvaluacionFacade;
    
    public TipoEvaluacionService() {
        this.tipoEvaluacionFacade = new TipoEvaluacionFacade();
    }
    
    public TipoEvaluacionFx saveTipoEvaluacion(TipoEvaluacionFx tipoEvalFx){
        TipoEvaluacion te = tipoEvaluacionFacade.create(parseToTipoEvaluacion(tipoEvalFx));
        tipoEvalFx.setId(te.getId());
        return tipoEvalFx;
    }
    
    public void deletTipoEvaluacion(TipoEvaluacionFx tipoEvalFx){
        tipoEvaluacionFacade.remove(parseToTipoEvaluacion(tipoEvalFx));
    } 
    
    public ObservableList<TipoEvaluacionFx> findAllTipoEvaluacion(){
        List<TipoEvaluacion> tiposEvaluacion = tipoEvaluacionFacade.findAll();
        List<TipoEvaluacionFx> lista = tiposEvaluacion.stream()
                                            .map( tipoE -> parseToTipoEvaluacionFx(tipoE))
                                            .collect(Collectors.toList());
       return FXCollections.observableArrayList(lista);
    }
    
    private TipoEvaluacion parseToTipoEvaluacion(TipoEvaluacionFx tipoEvalFx){
        TipoEvaluacion tipoEval = new TipoEvaluacion();
        if(tipoEvalFx.getId() != null){
            tipoEval.setId(tipoEvalFx.getId());
        }
        tipoEval.setNombre(tipoEvalFx.getNombre());
        
        tipoEval.setAcumulado((tipoEvalFx.getAcumulado().equals(TipoEvaluacionFx.ACUMULADOSTR))
                ? TipoEvaluacionFx.ACUMULADO : TipoEvaluacionFx.NOACUMULADO);

        return tipoEval;
    }
    
    private TipoEvaluacionFx parseToTipoEvaluacionFx(TipoEvaluacion tipoEval) {
        TipoEvaluacionFx tipoEvalFx = new TipoEvaluacionFx();
        tipoEvalFx.setId(tipoEval.getId());
        tipoEvalFx.setNombre(tipoEval.getNombre());
        tipoEvalFx.setAcumulado((tipoEval.getAcumulado() == TipoEvaluacionFx.ACUMULADO)
                    ? TipoEvaluacionFx.ACUMULADOSTR: TipoEvaluacionFx.NOACUMULADOSTR);
        
        return tipoEvalFx;
    }
}
