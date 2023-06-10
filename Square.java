package innovateMonopoly;

import java.util.ArrayList;

public class Square {
    private String name;
    private static int jail;
    private float amount;

    private SquareType type;
    private TitleProperty titleProperty;
    private Surprise surprise;
    private SurpriseDeck deck;

    Square(String name){
        init();
        type = SquareType.REST;
        this.name = name;
    }

    Square(TitleProperty title){
        init();
        type = SquareType.STREET;
        titleProperty = title;
        name = title.getName();

    }

    Square(float quantity, String name) {
        init();
        type = SquareType.TAX;
        this.name = name;
        amount = quantity;

    }

    Square(int numSquareJail, String name){
        init();
        type = SquareType.JUDGE;
        jail = numSquareJail;
        this.name = name;

    }

    Square(SurpriseDeck deck, String name){
        init();
        type = SquareType.SURPRISE;
        this.name = name;
        this.deck = deck;

    }

    private void init (){
        amount = 0f;
        type = null;
        titleProperty = null;
        surprise = null;
        deck = null;
    }

    private void report(int current, ArrayList<Player> all){
        Journal.getInstance().occurEvent("The player " + all.get(current).getName() + " has fallen into this box" + name + "Box Information: \n" + toString());
    }

    private void receivePlayer_tax(int current, ArrayList<Player> all){
        if(playerCorrect(current,all)){
            report(current, all);

            all.get(current).payTax(amount);
        }
    }

    private void receivePlayer_judge(int current, ArrayList<Player> all){
        if(playerCorrect(current, all)){
            report(current, all);

            all.get(current).incarcerate(jail);
        }
    }

    @Override
    public String toString() {
        return "Square{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", type=" + type +
                ", titleProperty=" + titleProperty +
                ", surprise=" + surprise +
                ", deck=" + deck +
                '}';
    }

    public boolean playerCorrect(int current, ArrayList<Player> all){
        if(current >= 0 && current < all.size()){
            return true;
        }
        else{
            return false;
        }
    }

    void receivePlayer(int icurrent, ArrayList<Player> all){
        switch (type){
            case STREET -> this.receivePlayer_street(icurrent, all);
            case TAX -> this.receivePlayer_tax(icurrent, all);
            case JUDGE -> this.receivePlayer_judge(icurrent, all);
            case SURPRISE -> this.receivePlayer_surprise(icurrent,all);
            default -> report(icurrent, all);
        }
    }

    private void receivePlayer_surprise(int icurrent, ArrayList<Player> all){
        if(playerCorrect(icurrent, all)){
            surprise = deck.next();

            this.report(icurrent, all);

            surprise.applyPlayer(icurrent, all);
        }
    }

    private void receivePlayer_street(int icurrent, ArrayList<Player> all){
        if(playerCorrect(icurrent, all)){
            this.report(icurrent, all);
            Player player;
            player = all.get(icurrent);

            if(!titleProperty.haveProprietor()){
                player.canBuySquare();
            }else {
                titleProperty.transferRent(player);
            }
        }
    }

    public String getName(){
        return name;
    }

    TitleProperty getTitleProperty(){
        return titleProperty;
    }
}
