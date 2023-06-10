package innovateMonopoly;

import java.util.ArrayList;
import java.util.Collections;

public class SurpriseDeck {
    private ArrayList<Surprise> surprises;
    private boolean shuffle;
    private int used;
    private boolean debug;
    private ArrayList<Surprise> specialCards;
    private Surprise lastSurprise;



    private void init(){
        surprises = new ArrayList<Surprise>();
        shuffle = false;
        specialCards = new ArrayList<Surprise>();
        used = 0;
    }

    SurpriseDeck(boolean _debug){
        debug = _debug;
        init();
        if(debug == true){
            Journal.getInstance().occurEvent("Debug mode is enabled");
        }

    }

    SurpriseDeck(){
        init();
        debug = false;
    }

    void toDeck(Surprise s){
        if(shuffle == false){
            surprises.add(s);
        }
    }

    Surprise next(){
        if((shuffle == false || used == surprises.size())) {
            if (debug == false) {
                used = 0;
                shuffle = true;
                Collections.shuffle(surprises);
            }


            used++;
            surprises.add(surprises.get(0));
            surprises.remove(0);

            lastSurprise = surprises.get(surprises.size() - 1);
        }
        return lastSurprise;
    }

    void notenableSpecialCards(Surprise surprise){
        for(int i = 0; i < surprises.size(); i++){
            if(surprise == surprises.get(i)){
                specialCards.add(surprises.get(i));
                surprises.remove(i);
                Journal.getInstance().occurEvent("The special card has been removed from the deck and added to Special cards.");
            }
        }

    }

    void enableSpecialCards(Surprise surprise){
        for(int i = 0; i < specialCards.size(); i++){
            if(surprise == specialCards.get(i)){
                surprises.add(specialCards.get(i));
                specialCards.remove(i);
                Journal.getInstance().occurEvent("Removed surprise from special cards and added to surprises");
            }
        }

    }

    Surprise getLastSurprise() {
        return lastSurprise;
    }
}
