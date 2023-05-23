package InnovateMonopoly;

import java.util.ArrayList;

public class Board {
    private int numBoxParcel;
    private ArrayList<Square> squares;
    private int byDeparture;
    private boolean hasJudge;

    Board(int num) {
        if (num >= 1) {
            numBoxParcel = num;
        } else {
            numBoxParcel = 1;
        }
        squares = new ArrayList<>();
        squares.add(new Square("Out"));
        byDeparture = 0;
        hasJudge = false;
    }

    private boolean correct(){
        if(squares.size() > numBoxParcel && hasJudge ==true){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean correct(int num){
        if(correct()  && num < squares.size()){return true;
        }
        else{
            return false;
        }
    }

    int getParcel(){
        return numBoxParcel;
    }

    int getByDeparture(){
        if(byDeparture > 0){
            return byDeparture--;
        }
        else{
            return byDeparture;
        }
    }

    void addBox(Square num){
        if(squares.size() == numBoxParcel){
            squares.add(new Square("Parcel"));
        }

        squares.add(num);

        if(squares.size() == numBoxParcel){
            squares.add(new Square("Parcel"));
        }
    }

    void addJudge(){
        if(hasJudge == false){
            squares.add(new Square("Judge"));
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

    int newPosition(int currect, int roll){
        if(correct() == true){
            int position;

            position = currect + roll;

            if(position >= squares.size()){
                position = position% squares.size();
                ++byDeparture;
            }

            return position;

        }else {
            return -1;
        }
    }

    int calculateShot(int source, int destination){
            int numThrow;

            if(destination < source){
                numThrow = squares.size() - source + destination;
            }
            else{
                numThrow = destination - source;
            }

            return numThrow;
    }
}
