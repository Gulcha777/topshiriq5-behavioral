package vazifa1;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Konkret Subject: ob'ekt holati (ob-havo o'lchovlari) o'zgarganda
 * ro'yxatdagi barcha kuzatuvchilarni xabardor qiladi.
 *
 * CopyOnWriteArrayList ishlatilgan: xabar yuborish paytida kuzatuvchi
 * o'zini ro'yxatdan chiqarsa ham ConcurrentModificationException chiqmaydi.
 */
public class WeatherStation implements WeatherSubject {

    private final List<WeatherObserver> observers = new CopyOnWriteArrayList<>();
    private WeatherData current;

    @Override
    public boolean registerObserver(WeatherObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("Kuzatuvchi null bo'lishi mumkin emas");
        }
        if (observers.contains(observer)) {
            return false;
        }
        return observers.add(observer);
    }

    @Override
    public boolean removeObserver(WeatherObserver observer) {
        return observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        if (current == null) {
            return; // hali o'lchov yo'q - xabar yuborilmaydi
        }
        for (WeatherObserver observer : observers) {
            observer.update(current);
        }
    }

    /** Yangi o'lchov kiritadi va avtomatik ravishda barcha kuzatuvchilarni xabardor qiladi. */
    public void setMeasurements(double temperature, double humidity, double pressure) {
        this.current = new WeatherData(temperature, humidity, pressure);
        notifyObservers();
    }

    public WeatherData getCurrent() {
        return current;
    }

    public int getObserverCount() {
        return observers.size();
    }
}
