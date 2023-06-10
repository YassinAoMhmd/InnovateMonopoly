package innovateMonopoly;

import java.util.ArrayList;

public class Board {
    private int numSquareJail;
    private ArrayList<Square> squares;
    private int byDeparture;
    private boolean hasJudge;

    Board(int square_jail) {
        if (square_jail >= 1) {
            numSquareJail = square_jail;
        } else {
            numSquareJail = 1;
        }
        squares = new ArrayList<>();
        squares.add(new Square("Exit"));
        byDeparture = 0;
        hasJudge = false;
    }

    private boolean correct(){
        if(squares.size() > numSquareJail && hasJudge ==true){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean correct(int numSquare){
        if(correct() == true && numSquare < squares.size()){return true;
        }
        else{
            return false;
        }
    }

    int getJail(){
        return numSquareJail;
    }

    int getByDeparture(){
        if(byDeparture > 0){
            return byDeparture--;
        }
        else{
            return byDeparture;
        }
    }

    void addSquare(Square square){
        if(squares.size() == numSquareJail){
            squares.add(new Square("Jail"));
        }

        squares.add(square);

        if(squares.size() == numSquareJail){
            squares.add(new Square("Jail"));
        }
    }

    void addJudge(){
        if(hasJudge == false){
            squares.add(new Square(numSquareJail,"Judge"));
            hasJudge = true;
        }
    }

    Square getSquare(int numSquare){
        if(correct(numSquare) == true){
            return squares.get(numSquare);
        }
        else{
            return null;
        }
    }

    int newPosition(int current, int throwing){
        if(correct() == true){
            int Position;

            Position = current + throwing;

            if(Position >= squares.size()){
                Position = Position% squares.size();
                ++byDeparture;
            }

            return Position;

        }else {
            return -1;
        }
    }

    int calculateShot(int source, int destination){
            int throwing;

            if(destination < source){
                throwing = squares.size() - source + destination;
            }
            else{
                throwing = destination - source;
            }

            return throwing;
    }

    public ArrayList<Square> getSquares(){
        return squares;
    }
}
