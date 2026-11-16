package vazifa1;

/**
 * Ob'ekt holati: ob'ektning o'zgarmas (immutable) ko'rinishi.
 * Push modelida Subject shu ob'ektni barcha kuzatuvchilarga yuboradi.
 */
public final class WeatherData {

    private final double temperature; // Selsiy darajasi
    private final double humidity;    // foiz: 0..100
    private final double pressure;    // gPa

    public WeatherData(double temperature, double humidity, double pressure) {
        if (humidity < 0 || humidity > 100) {
            throw new IllegalArgumentException("Namlik 0..100 oralig'ida bo'lishi kerak: " + humidity);
        }
        if (pressure <= 0) {
            throw new IllegalArgumentException("Bosim musbat bo'lishi kerak: " + pressure);
        }
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    @Override
    public String toString() {
        return String.format(java.util.Locale.ROOT, "harorat=%.1f C, namlik=%.0f%%, bosim=%.0f gPa",
                temperature, humidity, pressure);
    }
}
