package org.goyo.cursos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.goyo.cursos.dao.CursoFacade;
import org.goyo.cursos.model.Curso;
import org.goyo.cursos.model.Periodo;
import org.goyo.cursos.modelfx.CursoFx;
import org.goyo.cursos.modelfx.PeriodoFx;

public class CursoService {

    private final CursoFacade cursoFacade;
    private final PeriodoService periodoService;

    public CursoService() {
        this.cursoFacade = new CursoFacade();
        this.periodoService = new PeriodoService();
    }
    
    public Curso getCurso(CursoFx cursoFx) {
        return cursoFacade.find(cursoFx.getId());
    }
    
    public CursoFx saveCurso(CursoFx cursoFx){
        Curso curso = new Curso();
        curso.setNombre(cursoFx.getNombre());
        curso.setNrc(cursoFx.getNrc());
        Periodo periodo = periodoService.getPeriodo(cursoFx.getPeriodoFx());
        curso.setPeriodoId(periodo);
        curso.setStatus(CursoFx.ACTIVO);
        curso = cursoFacade.create(curso);
        return cursoFx;
    }
    
    public void deleteCurso(CursoFx cursoFx) {
        Curso curso = cursoFacade.find(cursoFx.getId());
        cursoFacade.remove(curso);
    }
    
    public CursoFx updateCurso(CursoFx cursoFx) {
        Curso curso = cursoFacade.find(cursoFx.getId());
        if (cursoFx.getStatus().equals(CursoFx.ACTIVOSTR)) {
            curso.setStatus(CursoFx.ACTIVO);
        } else {
            curso.setStatus(CursoFx.INACTIVO);
        }
        return parseToCursoFx(cursoFacade.edit(curso));
    }

    public List<CursoFx> getAllCursoFx() {
        List<Curso> cursos = cursoFacade.findAll();
        List<CursoFx> cursosFx = new ArrayList<>();
        for (Curso curso : cursos) {
            CursoFx cursoFx = parseToCursoFx(curso);
            cursosFx.add(cursoFx);
        }
        return cursosFx;
    }
    
    public List<CursoFx> getAllCursoFxByPeriodoFx(PeriodoFx periodoFx){
        List<CursoFx> cursosFx = new ArrayList<>();
        Periodo periodo = periodoService.parseToPeriodo(periodoFx);
        Optional<List<Curso>> optCursos = cursoFacade.findAllCursoByPeriodo(periodo);
        if(optCursos.isPresent()){
            for(Curso curso : optCursos.get()){
                CursoFx cursoFx = parseToCursoFx(curso);
                cursosFx.add(cursoFx);
            }
        }
        
        return cursosFx;
    }

    protected CursoFx parseToCursoFx(Curso curso) {
        CursoFx cursoFx = new CursoFx();
        cursoFx.setId(curso.getId());
        cursoFx.setNombre(curso.getNombre());
        cursoFx.setNrc(curso.getNrc());
        if (curso.getStatus() == CursoFx.ACTIVO) {
            cursoFx.setStatus(CursoFx.ACTIVOSTR);
        } else {
            cursoFx.setStatus(CursoFx.INACTIVOSTR);
        }
        PeriodoFx periodoFx = periodoService.parseToPeriodoFx(curso.getPeriodoId());
        cursoFx.setPeriodoFx(periodoFx);
        
        return cursoFx;
    }
    
    protected Curso parseToCurso(CursoFx cursoFx) {
        Curso curso = new Curso();
        curso.setId(cursoFx.getId());
        curso.setNombre(cursoFx.getNombre());
        curso.setNrc(cursoFx.getNrc());
        switch (cursoFx.getStatus()) {
            case CursoFx.INACTIVOSTR:
                curso.setStatus(CursoFx.INACTIVO);
                break;
            case CursoFx.ACTIVOSTR:
                curso.setStatus(CursoFx.ACTIVO);
                break;
        }
        return curso;
    }
}
