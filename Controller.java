package innovateMonopoly;

public class Controller {
    private MonopolyGame game;
    private TextualView view;

    Controller(MonopolyGame game, TextualView view){
        this.game = game;
        this.view = view;
    }

    void play(){
        view.setMonopolyGame(game);

        while(!game.endGameEnd()){
            view.updateView();
            view.pause();
            OperationsGame operation;
            operation = game.nextCompletedStep();
            view.showNextOperation(operation);
            if(operation != OperationsGame.SHIFT_PASS){
                view.showEvents();
            }
            if(!game.endGameEnd()){
                Answers answers;
                switch (operation){
                    case BUY:
                        answers = view.buy();
                        if(answers == Answers.YES){
                            game.buy();
                        }
                        game.nextCompletedStep(operation);
                    break;

                    case MANAGE:
                        view.manage();

                        PropertyManagment management = (PropertyManagment.values()[view.iManagement]);

                        RealEstatesOperations op = new RealEstatesOperations(management, view.getProperty());

                        if(management == PropertyManagment.CANCEL_MORTGAGE){
                            game.cancelMortgage(view.getProperty());
                        }else if(management == PropertyManagment.BUILD_HOME){
                            game.buildHouse(view.getProperty());
                        }else if(management == PropertyManagment.BUILD_HOTEL){
                            game.buildHotel(view.getProperty());
                        }else if(management == PropertyManagment.MORTGAGE){
                            game.mortgage(view.getProperty());
                        }else if (management == PropertyManagment.SELL){
                            game.sell(view.getProperty());
                        }else if(management == PropertyManagment.FINISH){
                            game.nextCompletedStep(operation);
                        }
                    break;

                        case EXIT_JAIL:
                        ExitJail exits;
                        exits = view.exitJail();
                        if(exits == ExitJail.PAYING){
                            game.exitJailPaying();
                        }
                        if(exits == ExitJail.THROWING){
                            game.exitJailThrowing();
                        }
                        game.nextCompletedStep(operation);
                    break;
                }
            }
        }
        game.ranking();
    }
}
