package innovateMonopoly;

public class TestP1 {
    public static void main(String[] args) {
        int player1 = 0;
        int player2 = 0;
        int player3 = 0;
        int player4 = 0;
        int start = 0;

        Dice dado = Dice.getInstance();

        for (int i = 0; i < 100; i++) {
            start = dado.whoStart(4);
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

        System.out.println("The value 1 has been repeated: " + player1 + " times\n");
        System.out.println("The value 2 has been repeated: " + player2 + " times\n");
        System.out.println("The value 3 has been repeated: " + player3 + " times\n");
        System.out.println("The value 4 has been repeated: " + player4 + " times\n");

        dado.setDebug(true);
        System.out.println("The dart throw has been: " + dado.throwDice());

        dado.setDebug(false);
        System.out.println("The dart throw has been: " + dado.throwDice());

        int lastResult;
        boolean exit_jail = false;
        lastResult = dado.getLastResult();
        System.out.println("The latest result has been: " + lastResult + "\n");

        exit_jail = dado.exitFromJail();
        if(exit_jail == true) {
            System.out.println("I can get out of prison\n");
        }
        else{
            System.out.println("I must stay in the prison\n");
        }

        System.out.println("Type of surprise chosen: \n" + SurpriseType.BYHOUSEHOTEL);
        System.out.println("Type of square selected: \n" + SquareType.STREET);

        Board board = new Board(2);

        board.addSquare(new Square("STREET"));
        board.addSquare((new Square("PARKING")));
        board.addSquare((new Square("SURPRISE")));
        board.addSquare(new Square("EXIT"));
        board.addSquare(new Square("TAXES"));
        board.addSquare((new Square("GO TO THE JAIL")));



        board.addJudge();

        int throwing;
        int new_throw;

        throwing= board.newPosition(0,dado.throwDice());

        System.out.println("The throwing was: " + throwing + "\n" + "And the new position is: " + dado.getLastResult());

        new_throw = dado.throwDice();

        System.out.println("Let's calculate how much the launch would be from " + throwing + " until the new throwing " + new_throw + "\n");

        System.out.println("For this to happen, the throwing must be of: " + board.calculateShot(throwing, new_throw));

    }
}

