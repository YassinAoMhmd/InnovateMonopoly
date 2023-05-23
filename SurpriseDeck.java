package InnovateMonopoly;

import java.util.ArrayList;
import java.util.Collections;

public class SurpriseDeck {
    private ArrayList<Surprise> surprises;
    private boolean shuffle;
    private int used;
    private boolean debug;
    private ArrayList<Surprise> specialCarts;
    private Surprise lastSurprise;



    private void init(){
        surprises = new ArrayList<Surprise>();
        specialCarts = new ArrayList<Surprise>();
        shuffle = false;
        used = 0;
    }

    SurpriseDeck(boolean _debug){
        debug = _debug;
        init();
        if(debug == true){
            Journal.getInstance().occurEvent("Debug mode is active");
        }

    }

    SurpriseDeck(){
        init();
        debug = false;
    }

    void alMazo(Surprise s){
        if(shuffle == false){
            surprises.add(s);
        }
    }

    Surprise next(){
        if(shuffle == false || used == surprises.size()) {
            if(debug == false) {
                used = 0;
                shuffle = true;
                Collections.shuffle(surprises);
            }
            used++;
            lastSurprise = surprises.get(0);
            surprises.add(lastSurprise);
            surprises.remove(0);
        }

        return lastSurprise;
    }

    void inhabilitateSpecialCart(Surprise surprise){
        for(int i = 0; i < surprises.size(); i++){
            if(surprise == surprises.get(i)){
                specialCarts.add(surprises.get(i));
                surprises.remove(i);
                Journal.getInstance().occurEvent("Special cart has been removed in the deck and added to special carts");
            }
        }

    }

    void habilitateSpecialCart(Surprise surprise){
        for(int i = 0; i < specialCarts.size(); i++){
            if(surprise == specialCarts.get(i)){
                surprises.add(specialCarts.get(i));
                specialCarts.remove(i);
                Journal.getInstance().occurEvent("Surprise in special carts has been removed and added to surprise");
            }
        }

    }



}
