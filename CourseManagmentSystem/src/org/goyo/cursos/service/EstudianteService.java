package org.goyo.cursos.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.goyo.cursos.dao.EstudianteFacade;
import org.goyo.cursos.dao.IntervencionFacade;
import org.goyo.cursos.model.Curso;
import org.goyo.cursos.model.Estudiante;
import org.goyo.cursos.model.Intervencion;
import org.goyo.cursos.modelfx.CursoFx;
import org.goyo.cursos.modelfx.EstudianteFx;

public class EstudianteService {

    private final EstudianteFacade estudianteFacade;
    private final IntervencionFacade intervencionFacade;
    private final CursoService cursoService;
    

    public EstudianteService() {
        this.estudianteFacade = new EstudianteFacade();
        this.intervencionFacade = new IntervencionFacade();
        this.cursoService = new CursoService();
    }

    public Estudiante getEstudiante(EstudianteFx estudianteFx) {
        return estudianteFacade.find(estudianteFx.getCi());
    }
    
    public Estudiante getEstudianteByCi(Integer ci){
        return estudianteFacade.find(ci);
    }

    public EstudianteFx saveEstudiante(EstudianteFx estudianteFx) {
        estudianteFx.setStatus(EstudianteFx.ACTIVOSTR);
        Estudiante estudiante = parseToEstudiante(estudianteFx);
        estudiante = estudianteFacade.create(estudiante);
        return estudianteFx;
    }

    public EstudianteFx updateEstudiante(EstudianteFx estudianteFx) {
        Estudiante estudiante = estudianteFacade.find(estudianteFx.getCi());
        if (estudianteFx.getStatus().equals(EstudianteFx.ACTIVOSTR)) {
            estudiante.setStatus(EstudianteFx.ACTIVO);
        } else {
            estudiante.setStatus(EstudianteFx.RETIRADO);
        }
        return parseToEstudianteFx(estudianteFacade.edit(estudiante));
    }

    public void deleteEstudiante(EstudianteFx estudianteFx) {
        Estudiante estudiante = estudianteFacade.find(estudianteFx.getCi());
        estudianteFacade.remove(estudiante);
    }

    public List<EstudianteFx> getAllEstudianteFx() {
        List<Estudiante> estudiantes = estudianteFacade.findAll();
        List<EstudianteFx> estudiantesFx = new ArrayList<>();
        for (Estudiante estudiante : estudiantes) {
            EstudianteFx estudianteFx = parseToEstudianteFx(estudiante);
            estudiantesFx.add(estudianteFx);
        }
        return estudiantesFx;
    }

    public List<EstudianteFx> getAllEstudianteFxByCursoFx(CursoFx cursoFx) {
        List<EstudianteFx> estudiantesFx = new ArrayList<>();
        Curso curso = cursoService.parseToCurso(cursoFx);
        Optional<List<Estudiante>> optEstudiantes = estudianteFacade.findAllEstudianteByCurso(curso);
        if (optEstudiantes.isPresent()) {
            for (Estudiante estudiante : optEstudiantes.get()) {
                EstudianteFx estudianteFx = parseToEstudianteFx(estudiante);
                estudiantesFx.add(estudianteFx);
            }
        }

        return estudiantesFx;
    }

    protected EstudianteFx parseToEstudianteFx(Estudiante estudiante) {
        EstudianteFx estudianteFx = new EstudianteFx();
        estudianteFx.setCi(estudiante.getCi());
        estudianteFx.setNombre(estudiante.getNombre());
        estudianteFx.setApellido(estudiante.getApellido());
        estudianteFx.setCorreo((estudiante.getCorreo() != null) ? estudiante.getCorreo() : "");
        estudianteFx.setTelefono((estudiante.getTelefono() != null) ? estudiante.getTelefono() : "");

        switch (estudiante.getStatus()) {
            case EstudianteFx.RETIRADO:
                estudianteFx.setStatus(EstudianteFx.RETIRADOSTR);
                break;
            case EstudianteFx.ACTIVO:
                estudianteFx.setStatus(EstudianteFx.ACTIVOSTR);
                break;
        }

        CursoFx cursoFx = cursoService.parseToCursoFx(estudiante.getCursoId());
        estudianteFx.setCursoFx(cursoFx);

        return estudianteFx;
    }

    protected Estudiante parseToEstudiante(EstudianteFx estudianteFx) {
        Estudiante estudiante = new Estudiante();
        estudiante.setCi(estudianteFx.getCi());
        estudiante.setNombre(estudianteFx.getNombre());
        estudiante.setApellido(estudianteFx.getApellido());
        estudiante.setCorreo((estudianteFx.getCorreo() != null) ? estudianteFx.getCorreo() : "");
        estudiante.setTelefono((estudianteFx.getTelefono() != null) ? estudianteFx.getTelefono() : "");
        switch (estudianteFx.getStatus()) {
            case EstudianteFx.RETIRADOSTR:
                estudiante.setStatus(EstudianteFx.RETIRADO);
                break;
            case EstudianteFx.ACTIVOSTR:
                estudiante.setStatus(EstudianteFx.ACTIVO);
                break;
        }

        Curso curso = cursoService.getCurso(estudianteFx.getCursoFx().get());
        estudiante.setCursoId(curso);
        return estudiante;
    }

    public boolean saveExcelData(File file, CursoFx cursoFx) {
        Curso curso = cursoService.getCurso(cursoFx);
        boolean ok = false;
        List<Object[]> alumnos = new ArrayList<>();
        try {
            FileInputStream excelFile = new FileInputStream(file);
            Workbook wb = new XSSFWorkbook(excelFile);
            Sheet sheet = wb.getSheetAt(0);
            boolean eof = false;
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                Object[] alumno = new Object[3];
                for (Cell cell : row) {
                    int cellColumn = cell.getColumnIndex();
                    if (!eof) {
                        switch (cellColumn) {
                            case 0:
                                if (cell.getNumericCellValue() != 0.0) { // fin de archivo
                                    alumno[0] = cell.getNumericCellValue();
                                } else {
                                    eof = true;
                                }
                                break;
                            case 1:
                                alumno[1] = cell.getStringCellValue();
                                break;
                            case 2:
                                alumno[2] = cell.getStringCellValue();
                                break;
                        }
                    } else {
                        break;
                    }
                }
                if(!eof){
                    alumnos.add(alumno);
                }
            }
            ok = true;
            makeEstudianteFromFile(alumnos, curso);
        } catch (IOException e) {
            Logger.getLogger(EstudianteService.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        return ok;
    }

    private void makeEstudianteFromFile(List<Object[]> alumnos, Curso curso) {
        List<Estudiante> estudiantes = new ArrayList<>();

        alumnos.stream().map((alumno) -> {
            Estudiante estudiante = new Estudiante();
            Double ci = (Double) alumno[0];
            estudiante.setCi(ci.intValue());
            String[] nombreApellido = ((String)alumno[1]).split(",");
            estudiante.setApellido(nombreApellido[0].trim());
            estudiante.setNombre(nombreApellido[1].trim());
            estudiante.setCorreo((String) alumno[2]);
            return estudiante;
        }).map((estudiante) -> {
            estudiante.setCursoId(curso);
            estudiante.setStatus(EstudianteFx.ACTIVO);
            return estudiante;
        }).forEachOrdered((estudiante) -> {
            estudiantes.add(estudiante);
        });

        estudianteFacade.batchSave(estudiantes);
        initIntervenciones(estudiantes);
    }
    
    private void initIntervenciones(List<Estudiante> estudiantes){
        List<Intervencion> intervenciones = new ArrayList<>();
        estudiantes.stream().forEachOrdered(est ->{
            intervenciones.add(makeIntervencion(est));
        });
        intervencionFacade.batchSave(intervenciones);
    }
    
    private Intervencion makeIntervencion(Estudiante estudiante){
        Intervencion intervencion = new Intervencion();
        intervencion.setEstudianteCi(estudiante);
        intervencion.setCursoId(estudiante.getCursoId());
        intervencion.setCantidad(0);
        return intervencion;
    }
}
