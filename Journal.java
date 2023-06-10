package innovateMonopoly;

import java.util.ArrayList;

public class Journal {
    static final private Journal instance = new Journal();

    private ArrayList<String> events;

    static public Journal getInstance() {
        return instance;
    }

    private Journal() {
        events = new ArrayList<>();
    }

    void occurEvent(String e) {
        events.add (e);
    }

    public boolean PendingsEvents() {
        return !events.isEmpty();
    }

    public String readEvent() {
        String exit = "";
        if (!events.isEmpty()) {
            exit = events.remove(0);
        }
        return exit;
    }
}
