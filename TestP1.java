package InnovateMonopoly;

public class TestP1 {
    public static void main(String[] args) {

        int player1 = 0;
        int player2 = 0;
        int player3 = 0;
        int player4 = 0;
        int start = 0;

        Dice SquareType = Dice.getInstance();

        for (int i = 0; i < 100; i++) {
            start = SquareType.whoStart(4);
            if (start == 1) {
                player1++;
            } else if (start == 2) {
                player2++;
            } else if (start == 3) {
                player3++;
            } else if(start == 4){
                player4++;
            }
        }

        System.out.println("The value 1 repet: " + player1 + "time\n");
        System.out.println("The value 2 repet: " + player2 + "time\n");
        System.out.println("The value 3 repet: " + player3 + " time\n");
        System.out.println("The value 4 repet: " + player4 + " time\n");

        SquareType.setDebug(true);
        System.out.println("The throw has been: " + SquareType.draw());

        SquareType.setDebug(false);
        System.out.println("The throw has been: " + SquareType.draw());


        int lastResult;
        boolean out_prision = false;
        lastResult = SquareType.getLastResult();
        System.out.println("Last result has been: " + lastResult + "\n");

        out_prision = SquareType.getFromJailOut();
        if(out_prision == true) {
            System.out.println("I can go out from the jail\n");
        }
        else{
            System.out.println("I need to be in the jail\n");
        }


        System.out.println("Type surprise chosen: \n" + SurpriseType.BYHOUSEHOTEL);
        System.out.println("Type square chosen: \n" + InnovateMonopoly.SquareType.STREET);

        Board board = new Board(2);

        board.addBox(new Square("STREET"));
        board.addBox((new Square("PARKING")));
        board.addBox((new Square("SURPRISE")));
        board.addBox(new Square("REST"));
        board.addBox(new Square("TAX"));
        board.addBox((new Square("JUDGE")));



        board.addJudge();

        int nThrow;
        int newThrow;

        nThrow= board.newPosition(0, SquareType.draw());

        System.out.println("The throw has been: " + nThrow + "\n" + "and the new position is: " + SquareType.getLastResult());

        newThrow = SquareType.draw();

        System.out.println("We are going to calculate what is gonna be the throw beetwen " + nThrow + " to the new throw " + newThrow + "\n");

        System.out.println("To happen this the throw has to be: " + board.calculateShot(nThrow, newThrow));

    }
}

