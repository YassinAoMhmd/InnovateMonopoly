package innovateMonopoly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class TextualView {
  
  MonopolyGame gameModel;
  int iManagement =-1;
  int iProperty =-1;
  private static String separator = "=====================";
  
  private Scanner in;
  
  TextualView() {
    in = new Scanner (System.in);
  }
  
  void showState(String state) {
    System.out.println (state);
  }
              
  void pause() {
    System.out.print ("Press a key");
    in.nextLine();
  }

  int readInteger(int max, String msg1, String msg2) {
    Boolean ok;
    String string;
    int number = -1;
    do {
      System.out.print (msg1);
      string = in.nextLine();
      try {  
        number = Integer.parseInt(string);
        ok = true;
      } catch (NumberFormatException e) {
        System.out.println (msg2);
        ok = false;  
      }
      if (ok && (number < 0 || number >= max)) {
        System.out.println (msg2);
        ok = false;
      }
    } while (!ok);

    return number;
  }

  int menu (String title, ArrayList<String> list) {
    String tab = "  ";
    int option;
    System.out.println (title);
    for (int i = 0; i < list.size(); i++) {
      System.out.println (tab+i+"-"+list.get(i));
    }

    option = readInteger(list.size(),
                          "\n"+tab+"Choose an option: ",
                          tab+"Wrong value");
    return option;
  }

  ExitJail exitJail() {
    int option = menu ("Choose the way to try to get out of jail",
      new ArrayList<> (Arrays.asList("Paying","Rolling the dice")));
    return (ExitJail.values()[option]);
  }

  Answers buy() {
    ArrayList<String> options = new ArrayList<String>(Arrays.asList("NO","YES"));

    int option = menu("Do you want to buy the street?",options);

    return (Answers.values()[option]);
  }

  void manage() {
    ArrayList<String> options = new ArrayList<>(Arrays.asList(
            "Sell","Mortgage", "Cancellation of mortgage",
            "Build House", "Build Hotel", "Finish"
    ));
    iManagement = menu("Property Management",options);

    ArrayList<String> propertysL = new ArrayList<>();

    if(iManagement != options.size() -1){
      for(TitleProperty t: gameModel.getJugadorActual().getProperties()){
        propertysL.add(t.getName());
      }
      iProperty = menu("Properties",propertysL);
    }
  }
  
  public int getManagement(){
    return iManagement;
  }
  
  public int getProperty(){
    return iProperty;
  }

  void showNextOperation(OperationsGame operation) {
    System.out.println(separator);
    System.out.println("Next Operation: " + operation);
    System.out.println(separator);
  }


  void showEvents() {
    System.out.println(separator);
    System.out.println("Loggin of events");
    System.out.println(separator);
    while(Journal.getInstance().PendingsEvents()){
        System.out.println(Journal.getInstance().readEvent());
    }
    System.out.println(separator);
  }
  
  public void setMonopolyGame(MonopolyGame monopoly){
        gameModel =monopoly;
        this.updateView();
  }

  void updateView(){

  }
}
