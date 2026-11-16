package vazifa1;

/**
 * Subject interfeysi: kuzatuvchilarni ro'yxatdan o'tkazish, bekor qilish va xabardor qilish.
 */
public interface WeatherSubject {

    /** @return true - kuzatuvchi qo'shildi; false - allaqachon ro'yxatda edi. */
    boolean registerObserver(WeatherObserver observer);

    /** @return true - kuzatuvchi olib tashlandi; false - ro'yxatda yo'q edi. */
    boolean removeObserver(WeatherObserver observer);

    void notifyObservers();
}
