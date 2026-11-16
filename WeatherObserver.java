package vazifa1;

/**
 * Observer interfeysi (ISP: faqat bitta metod).
 * Har qanday kuzatuvchi shu shartnomaga bo'ysunadi.
 */
public interface WeatherObserver {

    /** Subject holati o'zgarganda chaqiriladi (push modeli). */
    void update(WeatherData data);

    /** Kuzatuvchining nomi (chiqarish va loglar uchun). */
    String getName();
}
