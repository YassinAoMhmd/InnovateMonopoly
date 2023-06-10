package innovateMonopoly;

import java.util.ArrayList;

public class Surprise {
    private String text;
    private int value;

    private SurpriseDeck deck;
    private SurpriseType type;
    private Board board;


    Surprise(SurpriseType type, Board board){
        init();
        this.type = SurpriseType.GOJAIL;
        this.text = "You go to the jail";
        this.board = board;
        this.value = board.getJail();
    }

    Surprise(SurpriseType type, Board board, int value, String text){
        init();
        this.type = SurpriseType.GOBOX;
        this.board = board;
        this.value = value;
        this.text = text;
    }

    Surprise(SurpriseType type, int value, String text){
        init();
        this.type = type;
        this.value = value;
        this.text = text;
    }

    Surprise(SurpriseType type, SurpriseDeck deck){
        init();
        this.type = SurpriseType.EXITJAIL;
        this.text = "You are getting out of jail";
        this.deck = deck;
    }

    private void init(){
        value = -1;
        board = null;
        deck = null;
    }

    public boolean playerCorrect(int current, ArrayList<Player> all){
        if(current < all.size() && current >= 0){
            return true;
        }else{
            return false;
        }
    }

    private void report(int current, ArrayList<Player> all){
       Journal.getInstance().occurEvent("A surprise is being applied to the player " + all.get(current).getName());
    }

    void applyPlayer(int current, ArrayList<Player> all){
        if (this.type == SurpriseType.GOJAIL){
                applyPlayer_goJail(current, all);
        }
        else if(this.type == SurpriseType.GOBOX){
                applyPlayer_goSquare(current, all);
        }
        else if(this.type == SurpriseType.PAYCOLLECT){
                applyPlayer_payCharge(current, all);
        }
        else if(this.type == SurpriseType.BYHOUSEHOTEL){
                applyPlayer_forHouseHotel(current, all);
        }
        else if(this.type == SurpriseType.PERPLAYER){
                applyPlayer_byPlayer(current, all);
        }
        else if(this.type == SurpriseType.EXITJAIL){
                applyPlayer_exitJail(current, all);
        }
    }

    private void applyPlayer_goJail(int current, ArrayList<Player> all){
        if(playerCorrect(current, all)){

            report(current, all);

            all.get(current).incarcerate(board.getJail());
        }
    }

    private void applyPlayer_goSquare(int current, ArrayList<Player> all){
        int currentSquare,throwing,newPosition;

        if(playerCorrect(current, all)){
            report(current, all);

            currentSquare = all.get(current).getNumCurrentSquare();

            throwing = board.calculateShot(currentSquare, value);

            newPosition = board.newPosition(currentSquare,throwing);

            all.get(current).moveToSquare(newPosition);

            board.getSquare(newPosition).receivePlayer(current,all);
        }
    }

    private void applyPlayer_payCharge(int current, ArrayList<Player> all){
        if(playerCorrect(current,all)){
            report(current,all);

            all.get(current).changeBalance(value);
        }
    }

    private void applyPlayer_forHouseHotel(int current, ArrayList<Player> all){
        if(playerCorrect(current, all)){
            report(current, all);

            all.get(current).changeBalance(value * all.get(current).quantityHousesHotels());
        }
    }

    private void applyPlayer_byPlayer(int current, ArrayList<Player> all){
        if(playerCorrect(current, all)){
            report(current, all);

            Surprise surpriseGood, surpriseBad;

            surpriseBad = new Surprise(SurpriseType.PAYCOLLECT, -1* value, "all players except current must pay");

            surpriseGood = new Surprise(SurpriseType.PAYCOLLECT, (all.size()-1)* value, "current player receives money");

            for(int i=0 ; i<all.size(); i++){
                if(all.get(i) != all.get(current)){
                    surpriseBad.applyPlayer_payCharge(i,all);
                }
            }

            surpriseGood.applyPlayer_payCharge(current,all);
        }
    }

    private void applyPlayer_exitJail(int current, ArrayList<Player> all) {
        if(playerCorrect(current, all)){
            boolean find = false;

            report(current, all);

            for(int i=0; !find && all.size() > i;i++){
                if(all.get(i).haveSafeConduct()){
                    find = true;
                }
            }

            if(!find){
                all.get(current).obtainSafeConduct(this);

                exitDeck();
            }
        }

    }

    void exitDeck(){
        if(this.type == SurpriseType.EXITJAIL){
            deck.notenableSpecialCards(this);
        }
    }

    void used(){
        if (this.type == SurpriseType.EXITJAIL){
            deck.enableSpecialCards(this);
        }
    }

    public String toString() {
        return "Surprise: " + this;
    }
}
