package org.goyo.cursos.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.goyo.cursos.dao.AsistenciaFacade;
import org.goyo.cursos.model.Asistencia;
import org.goyo.cursos.model.Curso;
import org.goyo.cursos.model.Estudiante;
import org.goyo.cursos.modelfx.AsistenciaDiariaFx;
import org.goyo.cursos.modelfx.AsistenciaFx;
import org.goyo.cursos.modelfx.CursoFx;
import org.goyo.cursos.modelfx.EstudianteFx;
import org.goyo.cursos.utilities.Utilities;

public class AsistenciaService {

    private final AsistenciaFacade asistenciaFacade;
    private final EstudianteService estudianteService;
    private final CursoService cursoService;

    public AsistenciaService() {
        this.asistenciaFacade = new AsistenciaFacade();
        this.estudianteService = new EstudianteService();
        this.cursoService = new CursoService();
    }

    public ObservableList<AsistenciaFx> findEstudiantesByCurso(CursoFx cursoFx) {
        List<EstudianteFx> estudiantes = estudianteService.getAllEstudianteFxByCursoFx(cursoFx);
        List<AsistenciaFx> asistencia = new ArrayList<>();
        estudiantes.stream().forEach(est -> {
            AsistenciaFx asist = new AsistenciaFx();
            asist.setAsistencia(Boolean.FALSE);
            asist.setCiEstudianteFx(est.getCi());
            asist.setApellidoEstudiante(est.getApellido());
            asist.setNombreEstudiante(est.getNombre());
            asistencia.add(asist);
        });
        return FXCollections.observableArrayList(asistencia);
    }
    
    public ObservableList<AsistenciaDiariaFx> findAsistentesByCurso(CursoFx cursoFx){
        Curso curso = cursoService.getCurso(cursoFx);
        List<Object[]> datos = asistenciaFacade.findAsistentesByCurso(curso);
        ObservableList<AsistenciaDiariaFx> lista = FXCollections.observableArrayList();
        datos.forEach(data -> {
            AsistenciaDiariaFx adFx = parseToAsistenciaDiariaFx(data);
            lista.add(adFx);
        });
        return lista;
    }
    
    public ObservableList<AsistenciaFx> findAsistentesByCursoAndDate(CursoFx cursoFx,
                        LocalDate fecha) {
        Curso curso = cursoService.getCurso(cursoFx);
        List<Asistencia> datos = asistenciaFacade
                .findAsistentesByCursoAndDate(curso, Utilities.parseToDate(fecha));
        ObservableList<AsistenciaFx> lista = FXCollections.observableArrayList();
        datos.forEach(data -> {
            AsistenciaFx adFx = parseToAsistenciaFx(data);
            lista.add(adFx);
        });
        return lista;
    }
    
    public void saveAsistencia(ObservableList<AsistenciaFx> lista, 
            final CursoFx cursoFx, final LocalDate fecha){
        List<Asistencia> asistencias = new ArrayList<>();
        lista.forEach(asisFx -> {
            Asistencia asist = parseToAsistencia(asisFx, cursoFx, fecha);
            asistencias.add(asist);
        });
        asistenciaFacade.batchSave(asistencias);
    }
    
    
    private Asistencia parseToAsistencia(AsistenciaFx asistFx, CursoFx cursoFx,
            LocalDate fecha){
        Curso curso = cursoService.parseToCurso(cursoFx);
        Asistencia asist = new Asistencia();
        asist.setFecha(Utilities.parseToDate(fecha));
        asist.setCursoId(curso);
        asist.setAsistencia(asistFx.asistenciaProperty().get()
                ?AsistenciaFx.ASISTIO:AsistenciaFx.NOASISTIO);
        Estudiante estudiante = estudianteService.getEstudianteByCi(asistFx.getCiEstudiante());
        asist.setEstudianteCi(estudiante);
        return asist;
    }
    
    private AsistenciaFx parseToAsistenciaFx(Asistencia asist){
        AsistenciaFx asistFx = new AsistenciaFx();
        asistFx.setCiEstudianteFx(asist.getEstudianteCi().getCi());
        asistFx.setApellidoEstudiante(asist.getEstudianteCi().getApellido());
        asistFx.setNombreEstudiante(asist.getEstudianteCi().getNombre());
        if(asist.getAsistencia() == AsistenciaFx.ASISTIO){
            asistFx.setAsistencia(Boolean.TRUE);
        } else {
            asistFx.setAsistencia(Boolean.FALSE);
        }
        return asistFx;
    }
    
    private AsistenciaDiariaFx parseToAsistenciaDiariaFx(Object[] data){
        AsistenciaDiariaFx adFx = new AsistenciaDiariaFx();
        LocalDate fecha = Utilities.parseTolocalDate((Date)data[0]);
        adFx.setFecha(fecha);
        adFx.setCantidad(((Long)data[1]).intValue());
        return adFx;
    }
}
