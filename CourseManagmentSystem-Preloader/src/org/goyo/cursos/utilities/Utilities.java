package org.goyo.cursos.utilities;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Utilities {
    
    public static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("CourseManagmentSystemPU");
    
}
