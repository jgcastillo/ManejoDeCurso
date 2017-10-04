package org.goyo.cursos.service;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.goyo.cursos.dao.IntervencionFacade;
import org.goyo.cursos.model.Curso;
import org.goyo.cursos.model.Estudiante;
import org.goyo.cursos.model.Intervencion;
import org.goyo.cursos.modelfx.CursoFx;
import org.goyo.cursos.modelfx.EstudianteFx;
import org.goyo.cursos.modelfx.IntervencionFx;

public class IntervencionService {

    private final IntervencionFacade intervencionFacade;
    private final EstudianteService estudianteService;
    private final CursoService cursoService;

    public IntervencionService() {
        this.intervencionFacade = new IntervencionFacade();
        this.estudianteService = new EstudianteService();
        this.cursoService = new CursoService();
    }

    public void updateIntervencion(IntervencionFx intrvFx) {
        Intervencion intrv = parseToIntervencion(intrvFx);
        intervencionFacade.edit(intrv);
    }

    public ObservableList<IntervencionFx> findAllIntervenciones(CursoFx cursoFx) {
        ObservableList<IntervencionFx> intervencionesFx = FXCollections.observableArrayList();
        Curso curso = cursoService.parseToCurso(cursoFx);
        List<Intervencion> intervenciones = intervencionFacade.findAllIntervencionesByCurso(curso);

        intervenciones.stream().sorted((intrv1, intrv2) -> {
            return intrv1.getEstudianteCi().getApellido().compareTo(intrv2.getEstudianteCi().getApellido());
        }).forEachOrdered(intrv -> {
            IntervencionFx intrvFx = parseToIntervencionFx(intrv);
            intervencionesFx.add(intrvFx);
        });
        return intervencionesFx;
    }

    private IntervencionFx parseToIntervencionFx(Intervencion intervencion) {
        IntervencionFx intrvFx = new IntervencionFx();
        EstudianteFx estudianteFx = estudianteService.parseToEstudianteFx(intervencion.getEstudianteCi());
        CursoFx cursoFx = estudianteFx.getCursoFx().get();
        intrvFx.setId(intervencion.getId());
        intrvFx.setEstudianteFx(estudianteFx);
        intrvFx.setCiEstudianteFx(estudianteFx.getCi());
        intrvFx.setApellidoEstudiante(estudianteFx.getApellido());
        intrvFx.setNombreEstudiante(estudianteFx.getNombre());
        intrvFx.setCursoFx(cursoFx);
        intrvFx.setCantidad(intervencion.getCantidad());
        return intrvFx;
    }

    private Intervencion parseToIntervencion(IntervencionFx intervencionFx) {
        Intervencion intrv = new Intervencion();
        intrv.setId(intervencionFx.getId());
        Curso curso = cursoService.parseToCurso(intervencionFx.getCursoFx());
        intrv.setCursoId(curso);
        Estudiante est = estudianteService.parseToEstudiante(intervencionFx.getEstudianteFx());
        intrv.setEstudianteCi(est);
        intrv.setCantidad(intervencionFx.getCantidad());
        return intrv;
    }
}
