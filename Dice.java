package innovateMonopoly;

import java.util.Random;

public class Dice {
    static final private Dice instance = new Dice();
    private static int exitJail = 5;
    private Random random;
    private int lastResult;
    private boolean debug;


    private Dice(){
        random = new Random();
        lastResult = 0;
        debug = false;
    }

    static public Dice getInstance() {
        return instance;
    }

    int throwDice(){
        if(instance.debug == false){

            lastResult = (int) (Math.random() * 6 + 1);

            return lastResult;
        }else{
            return 1;
        }
    }

    Boolean exitFromJail(){
        if(throwDice() >= 5){
            return true;
        }
        else{
            return false;
        }
    }

    int whoStart(int n){
        int first_throw;

        first_throw = (int)(Math.random()*(n)+1);

        return  first_throw;
    }

    void setDebug(Boolean d){
        debug = d;
        if(debug)
        {
            Journal.getInstance().occurEvent("Debug is activated");
        }
        else{
            Journal.getInstance().occurEvent("Debug is deactivated");
        }
    }

    int getLastResult(){
        return lastResult;
    }

}

