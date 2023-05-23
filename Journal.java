package InnovateMonopoly;

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

    public boolean pendientEvents() {
        return !events.isEmpty();
    }

    public String readEvent() {
        String out = "";
        if (!events.isEmpty()) {
            out = events.remove(0);
        }
        return out;
    }
}
