package cz.czechitas.kockamyssyr;

import dev.czechitas.java1.kockamyssyr.api.*;

import java.awt.*;
import java.util.Random;

/**
 * Hlaví třída pro hru Kočka–myš–sýr.
 */
public class HlavniProgram {
    private final Random random = new Random();

    private final int VELIKOST_PRVKU = 50;
    private final int SIRKA_OKNA = 1000 - VELIKOST_PRVKU;
    private final int VYSKA_OKNA = 600 - VELIKOST_PRVKU;

    private Cat tom;
    private Mouse jerry;

    /**
     * Spouštěcí metoda celé aplikace.
     *
     * @param args
     */
    public static void main(String[] args) {
        new HlavniProgram().run();
    }

    /**
     * Hlavní metoda obsahující výkonný kód.
     */
    public void run() {
        tom = vytvorKocku();
        //tom.setBrain(new KeyboardBrain(KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D));

        jerry = vytvorMys();
        jerry.setBrain(new KeyboardBrain());

        vytvorVeci(4);
        chytMys();
    }

    public void chytMys() {
        // [0,0] vlevo, nahoře
        // začátek lovu
       while (jerry.isAlive() && tom.isAlive()) {
           int mouseX = jerry.getX();
           int mouseY = jerry.getY();

           // HORIZONTÁLNĚ
            if (tom.getX() < mouseX){ // kočka je víc vlevo než myš
                otocKockuSmerem(PlayerOrientation.RIGHT);
                //Pohybuj se doprava dokud nedosáhneš mouseX
                while (tom.getX() < mouseX && jerry.isAlive() && tom.isAlive()) {
                    if (tom.isPossibleToMoveForward()){
                        tom.moveForward();
                    } else {
                        obejdiPrekazku();
                    }
                }
            }else if (tom.getX() > mouseX){ // kočka je víc vpravo
                otocKockuSmerem(PlayerOrientation.LEFT);
                //Pohybuj se doleva dokud nedosáhneš mouseX
                while (tom.getX() > mouseX && jerry.isAlive() && tom.isAlive()) {
                    if (tom.isPossibleToMoveForward()){
                        tom.moveForward();
                    } else {
                        obejdiPrekazku();
                    }
                }
            }

            // VERTIKÁLNĚ
           if (tom.getY() < mouseY){ // kočka je výš než myš
               otocKockuSmerem(PlayerOrientation.DOWN);
               //Pohybuj se dolů dokud nedosáhneš mouseY
               while (tom.getY() < mouseY && jerry.isAlive() && tom.isAlive()) {
                   if (tom.isPossibleToMoveForward()){
                       tom.moveForward();
                   } else {
                       obejdiPrekazku();
                   }
               }
           }else if (tom.getY() > mouseY){ // kočka je níž než myš
               otocKockuSmerem(PlayerOrientation.UP);
               //Pohybuj se nahoru dokud nedosáhneš mouseY
               while (tom.getY() > mouseY && jerry.isAlive() && tom.isAlive()) {
                   if (tom.isPossibleToMoveForward()){
                       tom.moveForward();
                   } else {
                       obejdiPrekazku();
                   }
               }
           }
       }
    }

    private void otocKockuSmerem (PlayerOrientation orientation) {
        while(tom.getOrientation() != orientation){
            tom.turnRight();
        }
    }

    private void obejdiPrekazku() {
        PlayerOrientation puvodniOrientace = tom.getOrientation();

        // jdu doprava
        tom.turnRight();
        if(tom.isPossibleToMoveForward()){
            tom.moveForward();
            otocKockuSmerem(puvodniOrientace);
            return;
        }

        // jdu doleva
        tom.turnLeft();
        tom.turnLeft();
        if(tom.isPossibleToMoveForward()){
            tom.moveForward();
            otocKockuSmerem(puvodniOrientace);
            return;
        }

        // jdu dozadu
        tom.turnLeft();
        if (tom.isPossibleToMoveForward()) {
            tom.moveForward();
            otocKockuSmerem(puvodniOrientace);
            return;
        }
    }

    public void vytvorVeci(int pocetStromu) {
        for (int i = 0; i < pocetStromu; i++) {
            vytvorStrom();
        }
        vytvorSyr();
        vytvorJitrnici();
    }

    public Tree vytvorStrom() {
        return new Tree(vytvorNahodnyBod());
    }

    public Cat vytvorKocku() {
        return new Cat(vytvorNahodnyBod());
    }

    public Mouse vytvorMys() {
        return new Mouse(vytvorNahodnyBod());
    }

    public Cheese vytvorSyr() {
        return new Cheese(vytvorNahodnyBod());
    }

    public Meat vytvorJitrnici() {
        return new Meat(vytvorNahodnyBod());
    }

    private Point vytvorNahodnyBod() {
        return new Point(random.nextInt(SIRKA_OKNA), random.nextInt(VYSKA_OKNA));
    }

}
