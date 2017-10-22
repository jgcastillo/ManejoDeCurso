package org.goyo.cursos.service;

import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.goyo.cursos.dao.EvaluacionFacade;
import org.goyo.cursos.model.Curso;
import org.goyo.cursos.model.Evaluacion;
import org.goyo.cursos.model.TipoEvaluacion;
import org.goyo.cursos.modelfx.CursoFx;
import org.goyo.cursos.modelfx.EvaluacionFx;
import org.goyo.cursos.modelfx.TipoEvaluacionFx;
import org.goyo.cursos.utilities.Utilities;

/**
 *
 * @author jgcastillo
 */
public class EvaluacionesService {

    private final ObservableList<TipoEvaluacionFx> evaluacionesTipo;
    private final EvaluacionFacade evaluacionFacade;
    private final CursoService cursoService;
    private final TipoEvaluacionService tipoEvaluacionService;

    public EvaluacionesService() {
        this.evaluacionesTipo = FXCollections.observableArrayList();
        this.evaluacionFacade = new EvaluacionFacade();
        this.cursoService = new CursoService();
        this.tipoEvaluacionService = new TipoEvaluacionService();
    }

    public ObservableList<TipoEvaluacionFx> getEvaluacionesTipo() {
        return tipoEvaluacionService.findAllTipoEvaluacion();
    }

    public int getTipoInt(String tipoStr) {
        switch (tipoStr) {
            case "Quiz":
                return EvaluacionFx.QUIZ;
            case "Parcial":
                return EvaluacionFx.PARCIAL;
            case "Proyecto":
                return EvaluacionFx.PROYECTO;
            case "Reparacion":
                return EvaluacionFx.REPARACION;
            case "Tarea":
                return EvaluacionFx.TAREA;
        }
        return 0;
    }

    public int getStatusInt(String statusStr) {
        switch (statusStr) {
            case "Planificado":
                return EvaluacionFx.PLANIFICADO;
            case "Realizado":
                return EvaluacionFx.REALIZADO;
            case "Evaluado":
                return EvaluacionFx.EVALUADO;
        }
        return 99;
    }

    public String getStatusString(int status) {
        switch (status) {
            case EvaluacionFx.PLANIFICADO:
                return "Planificado";
            case EvaluacionFx.REALIZADO:
                return "Realizado";
            case EvaluacionFx.EVALUADO:
                return "Evaluado";
        }
        return "";
    }

    public EvaluacionFx saveEvaluacionFx(EvaluacionFx evaluacionFx) {
        Evaluacion evaluacion = parseToEvaluacion(evaluacionFx);
        evaluacion = evaluacionFacade.create(evaluacion);
        evaluacionFx.setId(evaluacion.getId());
        return evaluacionFx;
    }
    
    public void deleteEvaluacionFx(EvaluacionFx evaluacionFx){
        Evaluacion evaluacion = parseToEvaluacion(evaluacionFx);
        System.out.println("La evaluacion a eliminar es: " + evaluacion.getId());
        evaluacionFacade.remove(evaluacion);
    }

    public ObservableList<EvaluacionFx> getAllEvaluacionFxByCursoFx(CursoFx cursoFx) {
        ObservableList<EvaluacionFx> evaluacionesFx = FXCollections.observableArrayList();
        Curso curso = cursoService.getCurso(cursoFx);
        Optional<List<Evaluacion>> optEvaluaciones = evaluacionFacade.findAllByCurso(curso);
        if (optEvaluaciones.isPresent()) {
            optEvaluaciones.get().stream()
                    .map((eval) -> parseToEvaluacionFx(eval))
                        .forEachOrdered((evalFx) -> {
                evaluacionesFx.add(evalFx);
            });
        }
        return evaluacionesFx;
    }

    private Evaluacion parseToEvaluacion(EvaluacionFx evaluacionFx) {
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setId(evaluacionFx.getId());
        evaluacion.setNombre(evaluacionFx.getNombre());
        TipoEvaluacion tipoEval = tipoEvaluacionService
                                .parseToTipoEvaluacion(evaluacionFx.getTipoEvaluacionFx());
        evaluacion.setTipoEvaluacionId(tipoEval);
        if(null != evaluacionFx.getPeso()){
            evaluacion.setPeso(evaluacionFx.getPeso());
        }
        if(null != evaluacionFx.getPesoAcmulado()){
            evaluacion.setPesoAcumulado(evaluacionFx.getPesoAcmulado());
        }
        evaluacion.setStatus(getStatusInt(evaluacionFx.getStatus()));
        evaluacion.setFecha(Utilities.parseToDate(evaluacionFx.getFecha()));
        CursoFx cursoFx = evaluacionFx.getCursoFx();
        evaluacion.setCursoId(cursoService.parseToCurso(cursoFx));
        return evaluacion;
    }

    private EvaluacionFx parseToEvaluacionFx(Evaluacion evaluacion) {
        EvaluacionFx evaluacionFx = new EvaluacionFx();
        evaluacionFx.setId(evaluacion.getId());
        evaluacionFx.setNombre(evaluacion.getNombre());
        TipoEvaluacion tipoEval = evaluacion.getTipoEvaluacionId();
        TipoEvaluacionFx tipoEvalFx = tipoEvaluacionService.parseToTipoEvaluacionFx(tipoEval);
        evaluacionFx.setTipoEvaluacionFx(tipoEvalFx);
        evaluacionFx.setPeso(evaluacion.getPeso());
        evaluacionFx.setStatus(getStatusString(evaluacion.getStatus()));
        evaluacionFx.setFecha(Utilities.parseTolocalDate(evaluacion.getFecha()));
        Curso curso = evaluacion.getCursoId();
        evaluacionFx.setCursoFx(cursoService.parseToCursoFx(curso));
        return evaluacionFx;
    }

}
