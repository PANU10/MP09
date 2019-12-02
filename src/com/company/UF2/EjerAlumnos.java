package com.company.UF2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class Alumno implements Callable<Integer> {
    private int nota;

    public Alumno(int nota) {
        this.nota = nota;
    }
    public int marks() {
        nota = (int)(Math.random()*10);
        return nota;
    }
    @Override
    public Integer call() throws Exception {
        nota = (int)(Math.random()*10);
        return nota;
    }
}

public class EjerAlumnos {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
        List<Alumno> llistaAlumnos= new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Alumno calcula = new Alumno((int)(Math.random()*10));
            llistaAlumnos.add(calcula);
        }
        List <Future<Integer>> llistaResultats;
        llistaResultats = executor.invokeAll(llistaAlumnos);

        executor.shutdown();
        for (int i = 0; i < llistaResultats.size(); i++) {
            Future<Integer> resultat = llistaResultats.get(i);
            try {
                System.out.println("Nota de alumnos "+i+ " és:" + resultat.get());
            } catch (InterruptedException | ExecutionException e) {
            }
        }
    }
}

class AlumnosSeq {

    public static void main(String[] args) {
        List<Alumno> ejerAlumnosList = new ArrayList<>();
        List<Integer> resultado = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Alumno calcula = new Alumno((int) (Math.random() * 10));
            ejerAlumnosList.add(calcula);
        }
        for (int i = 0; i < 100; i++) {
            resultado.add(ejerAlumnosList.get(i).marks());
        }

        for(int i=0;i<100;i++) {
            System.out.println("Resultat " + i + " : " + resultado.get(i));
        }
    }
}




