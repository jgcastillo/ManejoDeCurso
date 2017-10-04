package org.goyo.cursos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.goyo.cursos.dao.PeriodoFacade;
import org.goyo.cursos.model.Periodo;
import org.goyo.cursos.modelfx.PeriodoFx;

public class PeriodoService {

    private final PeriodoFacade periodoFacade;
    
    public PeriodoService() {
        this.periodoFacade = new PeriodoFacade();
    }
    
    public Periodo getPeriodo(PeriodoFx periodoFx){
        return periodoFacade.find(periodoFx.getId());
    }
    
    public PeriodoFx savePeriodo(PeriodoFx periodoFx){
        Periodo periodo = new Periodo();
        periodo.setNombre(periodoFx.getNombre());
        if(periodoFx.getStatus().equals(PeriodoFx.ACTIVOSTR)){
            periodo.setStatus(PeriodoFx.ACTIVO);
        } else {
            periodo.setStatus(PeriodoFx.INACTIVO);
        }
        periodo = periodoFacade.create(periodo);
        periodoFx.setId(periodo.getId());
        return periodoFx;
    }
    
    public PeriodoFx updatePeriod(PeriodoFx periodoFx){
        Periodo periodo = periodoFacade.find(periodoFx.getId());
        if (periodoFx.getStatus().equals(PeriodoFx.ACTIVOSTR)) {
            periodo.setStatus(PeriodoFx.ACTIVO);
        } else {
            periodo.setStatus(PeriodoFx.INACTIVO);
        }
        return parseToPeriodoFx(periodoFacade.edit(periodo));
    }
    
    public void deletePeriod(PeriodoFx periodoFx){
        Periodo periodo = periodoFacade.find(periodoFx.getId());
        periodoFacade.remove(periodo);
    }
    
    public Optional<PeriodoFx> getPeriodoActivo(){
       Optional<PeriodoFx> optPeriodoFx = Optional.empty();
       Optional<Periodo> optPeriodo = periodoFacade.findPeriodoActivo();
       if(optPeriodo.isPresent()){
           optPeriodoFx = Optional.of(parseToPeriodoFx(optPeriodo.get()));
       }
       return optPeriodoFx;
    }
    
    public List<PeriodoFx> getAllPeriodoFx(){
        List<Periodo> periodos = periodoFacade.findAll();
        List<PeriodoFx> periodosFx = new ArrayList<>();
        for(Periodo periodo : periodos){
            PeriodoFx periodoFx = parseToPeriodoFx(periodo);
            periodosFx.add(periodoFx);
        }    
        return periodosFx;    
    }
    
    public PeriodoFx parseToPeriodoFx(Periodo periodo){
        PeriodoFx periodoFx = new PeriodoFx();
        periodoFx.setId(periodo.getId());
        periodoFx.setNombre(periodo.getNombre());
        if(periodo.getStatus() == PeriodoFx.ACTIVO){
            periodoFx.setStatus("Activo");
        } else {
            periodoFx.setStatus("Inactivo");
        }
        return periodoFx;
    }
    
    public Periodo parseToPeriodo(PeriodoFx periodoFx) {
        Periodo periodo = new Periodo();
        periodo.setId(periodoFx.getId());
        periodo.setNombre(periodoFx.getNombre());
        if (periodoFx.getStatus() == PeriodoFx.ACTIVOSTR) {
            periodo.setStatus(PeriodoFx.ACTIVO);
        } else {
            periodo.setStatus(PeriodoFx.INACTIVO);
        }
        return periodo;
    }
}
