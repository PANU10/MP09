package com.company.UF2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class EjerAlumnos {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
        List<alumno> llistaAlumnos= new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            alumno calcula = new alumno((int)(Math.random()*10));
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

class alumno implements Callable<Integer> {
    private int nota;

    public alumno(int nota) {
        this.nota = nota;
    }
    @Override
    public Integer call() throws Exception {
        nota = (int)(Math.random()*10);
        return nota;
    }
}

