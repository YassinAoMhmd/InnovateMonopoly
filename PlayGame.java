package innovateMonopoly;

import java.util.ArrayList;

public class PlayGame {
    public static void main(String[] args) {
        TextualView view = new TextualView();
        ArrayList<String> names = new ArrayList<>();
        names.add("Yassin");
        names.add("Zhan Yong");
        names.add("Bad Bunny");
        names.add("Irmantas");
        names.add("Zelinsky");
        names.add("Adele");
        names.add("Gilberto");

        MonopolyGame begin = new MonopolyGame(names);
        Dice.getInstance().setDebug(false);
        Controller controller = new Controller(begin, view);

        controller.play();
    }
}
