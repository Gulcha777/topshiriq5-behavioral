package vazifa1;

import java.io.PrintStream;

/** Konkret kuzatuvchi: telefon ekrani. Ixcham ma'lumot ko'rsatadi. */
public class PhoneDisplay implements WeatherObserver {

    private final PrintStream out;
    private WeatherData lastData;

    public PhoneDisplay() {
        this(System.out);
    }

    public PhoneDisplay(PrintStream out) {
        this.out = out;
    }

    @Override
    public void update(WeatherData data) {
        this.lastData = data;
        out.printf(java.util.Locale.ROOT, "[Telefon] %.1f C, namlik %.0f%%%n", data.getTemperature(), data.getHumidity());
    }

    @Override
    public String getName() {
        return "PhoneDisplay";
    }

    public WeatherData getLastData() {
        return lastData;
    }
}
