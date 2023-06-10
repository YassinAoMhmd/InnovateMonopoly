package innovateMonopoly;

import java.util.ArrayList;
import java.util.Collections;

public class MonopolyGame {
    private int currentPlayerIndex;

    private ArrayList<Player> players;
    private SurpriseDeck deck;
    private Board board;
    private StatesGame state;
    private ManagerStates managerStates;


    public MonopolyGame(ArrayList<String> names) {
        players = new ArrayList<Player>();

        for (int i = 0; i < names.size(); i++) {
            players.add(new Player(names.get(i)));
        }

        managerStates = new ManagerStates();

        state = managerStates.statusInitial();

        currentPlayerIndex = Dice.getInstance().whoStart(players.size());

        deck = new SurpriseDeck();

        initializaBoard(deck);

        initializeSurpriseDeck(board);

    }

    public void updateInfo() {
        System.out.println("MonopolyGame{" +
                "currentPlayerIndex = " + currentPlayerIndex +
                ", is in the square = " + players.get(currentPlayerIndex).getNumCurrentSquare() +
                " its main characteristics are = " + players.get(currentPlayerIndex).toString() + '}');
        if (endGameEnd()) {
            ranking();
        }
    }

    private void initializaBoard(SurpriseDeck deck) {
        int jail = 3;

        board = new Board(jail);


        TitleProperty tp1 = new TitleProperty("Royal Street",20,1.10f,80,100,100);
        TitleProperty tp2 = new TitleProperty("Varela Street",30,1.10f,90,110,110 );
        TitleProperty tp3 = new TitleProperty("Juan Carlos Street",40,1.10f,100,120,120 );
        TitleProperty tp4 = new TitleProperty("Santaros Street",50,1.10f,110,130,130 );
        TitleProperty tp5 = new TitleProperty("Gedimino Street",60,1.10f,120,140,140 );
        TitleProperty tp6 = new TitleProperty("Naugarduko Street",70,1.10f,120,140,150 );
        TitleProperty tp7 = new TitleProperty("Zydu Street",80,1.10f,125,150,160 );
        TitleProperty tp8 = new TitleProperty("Kalvarijos Street",90,1.10f,130,160,170 );
        TitleProperty tp9 = new TitleProperty("Vilnius Street",100,1.10f,135,170,180 );
        TitleProperty tp10 = new TitleProperty("Kaunas Street",105,1.10f,140,180,190 );
        TitleProperty tp11 = new TitleProperty("Didlaukio Street",110,1.10f,145,190,200 );
        TitleProperty tp12 = new TitleProperty("Sauletekis Street",115,1.10f,150,200,210 );


        board.addSquare(new Square(tp1));
        board.addSquare(new Square(tp2));
        board.addSquare(new Square(tp3));
        board.addSquare(new Square(tp4));
        board.addSquare(new Square(tp5));
        board.addSquare(new Square(tp6));
        board.addSquare(new Square(tp7));
        board.addSquare(new Square(tp8));
        board.addSquare(new Square(tp9));
        board.addSquare(new Square(tp10));
        board.addSquare(new Square(tp11));
        board.addSquare(new Square(tp12));

        Square squareSurprise = new Square(deck,"Surprise");
        Square squareTax = new Square(0f,"Tax");
        Square squareParking = new Square("Parking");

        board.addSquare(squareSurprise);
        board.addSquare(squareTax);
        board.addSquare(squareParking);

        board.addJudge();

    }

    private void initializeSurpriseDeck(Board board) {
        SurpriseType type = SurpriseType.PAYCOLLECT;

        deck.toDeck(new Surprise(SurpriseType.GOJAIL, board));

        deck.toDeck(new Surprise(SurpriseType.GOBOX, board,0,toString()));

        deck.toDeck(new Surprise(SurpriseType.EXITJAIL, deck));

        deck.toDeck(new Surprise(type,0,toString()));

    }

    private void countPassThroughOut(Player currentPlayer){
        while (board.getByDeparture() > 0){
            players.get(currentPlayerIndex).passThroughDeparture();
        }
    }

    private void passTurn(){
        if(currentPlayerIndex == players.size()-1){
            currentPlayerIndex = 0;
        }else{
            currentPlayerIndex++;
        }
    }

    public void nextCompletedStep(OperationsGame operation){
        state = managerStates.siguienteEstado(players.get(currentPlayerIndex), state,operation);
    }

    public boolean buildHouse(int ip){
        return players.get(currentPlayerIndex).buildHouse(ip);
    }

    public boolean buildHotel(int ip){
        return players.get(currentPlayerIndex).buildHotel(ip);
    }

    public boolean sell(int ip){
        return players.get(currentPlayerIndex).sell(ip);
    }

    public boolean mortgage(int ip){
        return players.get(currentPlayerIndex).mortgage(ip);
    }

    public boolean cancelMortgage(int ip){
        return players.get(currentPlayerIndex).cancelMortgage(ip);
    }

    public boolean exitJailPaying(){
        return players.get(currentPlayerIndex).exitJailPaying();
    }

    public boolean exitJailThrowing(){
        return players.get(currentPlayerIndex).exitJailThrowing();
    }

    public boolean endGameEnd(){
        boolean endGame = false;

        for(int i = 0; i < players.size() && !endGame; i++){
            endGame = players.get(i).inBankrupt();
        }

        return endGame;
    }

    ArrayList<Player> ranking(){
        Collections.sort(players);
        return players;
    }

    private void advancePlayer(){
        Player currentPlayer;
        currentPlayer = players.get(currentPlayerIndex);

        int currentPosition;
        currentPosition = currentPlayer.getnumCurrentSquare();

        int throwing;
        throwing = Dice.getInstance().throwDice();

        int newPosition;
        newPosition = board.newPosition(currentPosition,throwing);

        Square square;
        square = board.getSquare(newPosition);

        countPassThroughOut(currentPlayer);

        currentPlayer.moveToSquare(newPosition);

        square.receivePlayer(currentPlayerIndex, players);

        countPassThroughOut(currentPlayer);
    }

    public OperationsGame nextCompletedStep(){
        Player currentPlayer;
        currentPlayer = players.get(currentPlayerIndex);

        OperationsGame operation;
        operation = managerStates.allowedOperations(currentPlayer, state);

        switch (operation){
            case SHIFT_PASS:
                    this.passTurn();
                    this.nextCompletedStep(operation);
            break;
            case ADVANCE:
                    this.advancePlayer();
                    this.nextCompletedStep(operation);
            break;
        }
        return operation;
    }

    public boolean buy(){
        boolean result = false;
        Player currentPlayer = players.get(currentPlayerIndex);
        int numCurrentSquare = currentPlayer.getNumCurrentSquare();
        Square square = board.getSquare(numCurrentSquare);
        TitleProperty title = square.getTitleProperty();
        result = currentPlayer.buy(title);

        return result;
    }


    public Square getCurrentSquare(){
        return board.getSquare(players.get(currentPlayerIndex).getNumCurrentSquare());
    }

    public Player getJugadorActual(){
        return players.get(currentPlayerIndex);
    }
}

