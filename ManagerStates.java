package innovateMonopoly;

class ManagerStates {
    StatesGame statusInitial() {
        return (StatesGame.START_SHIFT);
    }

    OperationsGame allowedOperations(Player player, StatesGame state) {
        OperationsGame op = null;

        switch (state) {
            case START_SHIFT:
                if (player.incarcelated)
                    op = OperationsGame.EXIT_JAIL;
                else
                    op = OperationsGame.ADVANCE;
                break;

            case AFTER_JAIL:
                op = OperationsGame.SHIFT_PASS;
                break;

            case AFTER_ADVANCE:
                if (player.incarcelated)
                    op = OperationsGame.SHIFT_PASS;
                else if (player.canBuySquare())
                    op = OperationsGame.BUY;
                else if (player.hasSomethingToManage())
                    op = OperationsGame.MANAGE;
                else
                    op = OperationsGame.SHIFT_PASS;
                break;

            case AFTER_PURCHASE:
                if (player.hasSomethingToManage())
                    op = OperationsGame.MANAGE;
                else
                    op = OperationsGame.SHIFT_PASS;
                break;

            case AFTER_MANAGE:
                op = OperationsGame.SHIFT_PASS;
                break;
        }
        return op;
    }

    StatesGame siguienteEstado (Player player, StatesGame state, OperationsGame operation) {
        StatesGame next = null;

        switch (state) {
            case START_SHIFT:
                if (operation== OperationsGame.EXIT_JAIL)
                    next = StatesGame.AFTER_JAIL;
                else if (operation== OperationsGame.ADVANCE)
                    next = StatesGame.AFTER_ADVANCE;
                break;

            case AFTER_JAIL:
                if (operation== OperationsGame.SHIFT_PASS)
                    next = StatesGame.START_SHIFT;
                break;

            case AFTER_ADVANCE:
                switch (operation) {
                    case SHIFT_PASS:
                        next = StatesGame.START_SHIFT;
                        break;
                    case BUY:
                        next = StatesGame.AFTER_PURCHASE;
                        break;
                    case MANAGE:
                        next = StatesGame.AFTER_MANAGE;
                        break;
                }
                break;

            case AFTER_PURCHASE:
                if (operation== OperationsGame.MANAGE)
                    next = StatesGame.AFTER_MANAGE;
                else if (operation== OperationsGame.SHIFT_PASS)
                    next = StatesGame.START_SHIFT;
                break;

            case AFTER_MANAGE:
                if (operation== OperationsGame.SHIFT_PASS)
                    next = StatesGame.START_SHIFT;
                break;
        }

        Journal.getInstance().occurEvent("Of: "+state.toString()+ " with "+operation.toString()+ " go: "+next.toString());

        return next;
    }

}