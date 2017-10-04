package org.goyo.cursos.controller;

import java.util.HashMap;
import java.util.Map;

public class Context {

    private static Context instance = null;
    private static Map<String, Object> context = null;
    
    private Context(){}
    
    public static Context getInstance(){
        if(instance == null){
            instance = new Context();
        }
        
        return instance;
    }
    
    public static Map<String, Object> getContext(){
        if(context == null){
            context = new HashMap<>();
        }
        return context;
    }
}
