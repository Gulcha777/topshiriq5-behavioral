package vazifa1;

/** Vazifa 1 demosi: Observer andozasi (WeatherStation). */
public class Main {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        System.out.println("=== VAZIFA 1: Observer (WeatherStation) ===");

        WeatherStation station = new WeatherStation();
        PhoneDisplay phone = new PhoneDisplay();
        WebDisplay web = new WebDisplay();
        LoggerObserver logger = new LoggerObserver();

        station.registerObserver(phone);
        station.registerObserver(web);
        station.registerObserver(logger);
        System.out.println("Ro'yxatdagi kuzatuvchilar soni: " + station.getObserverCount());

        System.out.println("\n-- 1-o'lchov (3 ta kuzatuvchi xabardor bo'ladi) --");
        station.setMeasurements(23.5, 60, 1013);

        System.out.println("\n-- WebDisplay bekor qilindi --");
        station.removeObserver(web);
        System.out.println("Ro'yxatdagi kuzatuvchilar soni: " + station.getObserverCount());

        System.out.println("\n-- 2-o'lchov (faqat 2 ta kuzatuvchi) --");
        station.setMeasurements(25.0, 55, 1010);

        System.out.println("\nLoggerObserver yozuvlari: " + logger.getEntries().size() + " ta");
    }
}
