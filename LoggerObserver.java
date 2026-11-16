package vazifa1;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Konkret kuzatuvchi: har bir o'zgarishni tartib raqami bilan jurnalga yozadi. */
public class LoggerObserver implements WeatherObserver {

    private final PrintStream out;
    private final List<String> entries = new ArrayList<>();

    public LoggerObserver() {
        this(System.out);
    }

    public LoggerObserver(PrintStream out) {
        this.out = out;
    }

    @Override
    public void update(WeatherData data) {
        String entry = "#" + (entries.size() + 1) + " " + data;
        entries.add(entry);
        out.println("[Log] " + entry);
    }

    @Override
    public String getName() {
        return "LoggerObserver";
    }

    public List<String> getEntries() {
        return Collections.unmodifiableList(entries);
    }
}
