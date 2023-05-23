package InnovateMonopoly;

import java.util.Random;

public class Dice {
    static final private Dice instance = new Dice();
    private static int outputPort = 5;
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

    int draw(){
        if(instance.debug == false){

            lastResult = (int) (Math.random() * 6 + 1);

            return lastResult;
        }else{
            return 1;
        }
    }

    Boolean getFromJailOut(){
        if(draw() >= 5){
            return true;
        }
        else{
            return false;
        }
    }

    int whoStart(int n){
        int first_player;

        first_player = (int)(Math.random()*(n)+1);

        return  first_player;
    }

    void setDebug(Boolean d){
        debug = d;
        if(debug)
        {
            Journal.getInstance().occurEvent("Debug is active");
        }
        else{
            Journal.getInstance().occurEvent("Debug is not active");
        }
    }

    int getLastResult(){
        return lastResult;
    }

}

