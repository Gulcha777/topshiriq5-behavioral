package vazifa1;

import java.io.PrintStream;

/** Konkret kuzatuvchi: veb-sahifa. To'liq ma'lumotni HTML ko'rinishida chiqaradi. */
public class WebDisplay implements WeatherObserver {

    private final PrintStream out;
    private WeatherData lastData;

    public WebDisplay() {
        this(System.out);
    }

    public WebDisplay(PrintStream out) {
        this.out = out;
    }

    @Override
    public void update(WeatherData data) {
        this.lastData = data;
        out.printf("[Veb] <div class=\"weather\">%s</div>%n", data);
    }

    @Override
    public String getName() {
        return "WebDisplay";
    }

    public WeatherData getLastData() {
        return lastData;
    }
}
