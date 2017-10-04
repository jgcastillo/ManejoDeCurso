package org.goyo.cursos.service;

import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.goyo.cursos.dao.EvaluacionFacade;
import org.goyo.cursos.model.Curso;
import org.goyo.cursos.model.Evaluacion;
import org.goyo.cursos.modelfx.CursoFx;
import org.goyo.cursos.modelfx.EvaluacionFx;
import org.goyo.cursos.utilities.Utilities;

/**
 *
 * @author jgcastillo
 */
public class EvaluacionesService {

    private final ObservableList<String> evaluacionesTipo;
    private final EvaluacionFacade evaluacionFacade;
    private final CursoService cursoService;

    public EvaluacionesService() {
        this.evaluacionesTipo = FXCollections.observableArrayList();
        this.evaluacionFacade = new EvaluacionFacade();
        this.cursoService = new CursoService();
    }

    public ObservableList<String> getEvaluacionesTipo() {
        evaluacionesTipo.add("Quiz");
        evaluacionesTipo.add("Parcial");
        evaluacionesTipo.add("Proyecto");
        evaluacionesTipo.add("Reparación");
        evaluacionesTipo.add("Tarea");
        return evaluacionesTipo;
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
        evaluacion.setNombre(evaluacionFx.getNombre());
        //evaluacion.setTipo(evaluacionFx.getTipo());
        evaluacion.setPeso(evaluacionFx.getPeso());
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
        //evaluacionFx.setTipo(evaluacion.getTipo());
        evaluacionFx.setPeso(evaluacion.getPeso());
        evaluacionFx.setStatus(getStatusString(evaluacion.getStatus()));
        evaluacionFx.setFecha(Utilities.parseTolocalDate(evaluacion.getFecha()));
        Curso curso = evaluacion.getCursoId();
        evaluacionFx.setCursoFx(cursoService.parseToCursoFx(curso));
        return evaluacionFx;
    }

}
