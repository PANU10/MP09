package com.company.UF2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class PruebaRunnable {

        public static void main(String[] args) throws InterruptedException, ExecutionException {

            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
            List llistaTasques= new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                Run run = new Run((int)(Math.random()*10));
                llistaTasques.add(run);
            }
            executor.shutdown();
            for (int i = 0; i < llistaTasques.size(); i++) {
                 //   System.out.println("Resultat tasca "+i+ " és:" + );
            }
        }
    }

class Run implements Runnable{
        private int nota;

    public int getNota() {
        nota = (int)(Math.random()*10);
        return nota;
    }
    public Run (int nota) {
        this.nota = nota;
    }

    @Override
    public void run() {
        return;
    }
}
