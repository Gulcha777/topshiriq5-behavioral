package tests;

import testkit.Test;
import vazifa1.LoggerObserver;
import vazifa1.PhoneDisplay;
import vazifa1.WebDisplay;
import vazifa1.WeatherData;
import vazifa1.WeatherObserver;
import vazifa1.WeatherStation;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static testkit.Assert.assertEquals;
import static testkit.Assert.assertFalse;
import static testkit.Assert.assertNull;
import static testkit.Assert.assertSame;
import static testkit.Assert.assertThrows;
import static testkit.Assert.assertTrue;

/** Vazifa 1: Observer andozasi testlari. */
public class WeatherStationTest {

    private static PrintStream silent() {
        return new PrintStream(new ByteArrayOutputStream());
    }

    @Test
    public void allRegisteredObserversAreNotified() {
        WeatherStation station = new WeatherStation();
        PhoneDisplay phone = new PhoneDisplay(silent());
        WebDisplay web = new WebDisplay(silent());
        LoggerObserver logger = new LoggerObserver(silent());
        station.registerObserver(phone);
        station.registerObserver(web);
        station.registerObserver(logger);

        station.setMeasurements(20.0, 50, 1000);

        assertSame(station.getCurrent(), phone.getLastData());
        assertSame(station.getCurrent(), web.getLastData());
        assertEquals(1, logger.getEntries().size());
    }

    @Test
    public void removedObserverIsNotNotifiedAnymore() {
        WeatherStation station = new WeatherStation();
        PhoneDisplay phone = new PhoneDisplay(silent());
        WebDisplay web = new WebDisplay(silent());
        station.registerObserver(phone);
        station.registerObserver(web);
        station.setMeasurements(20.0, 50, 1000);

        assertTrue(station.removeObserver(web), "WebDisplay ro'yxatdan olib tashlanishi kerak");
        station.setMeasurements(30.0, 40, 990);

        assertEquals(30.0, phone.getLastData().getTemperature());
        assertEquals(20.0, web.getLastData().getTemperature()); // eski qiymatda qoldi
        assertEquals(1, station.getObserverCount());
    }

    @Test
    public void duplicateRegistrationIsIgnored() {
        WeatherStation station = new WeatherStation();
        LoggerObserver logger = new LoggerObserver(silent());

        assertTrue(station.registerObserver(logger), "birinchi qo'shish muvaffaqiyatli");
        assertFalse(station.registerObserver(logger), "takroriy qo'shish rad etilishi kerak");
        station.setMeasurements(10.0, 10, 1000);

        assertEquals(1, station.getObserverCount());
        assertEquals(1, logger.getEntries().size()); // ikki marta xabar kelmadi
    }

    @Test
    public void removingUnknownObserverReturnsFalse() {
        WeatherStation station = new WeatherStation();
        assertFalse(station.removeObserver(new PhoneDisplay(silent())),
                "ro'yxatda yo'q kuzatuvchini olib tashlash false qaytarishi kerak");
    }

    @Test
    public void noNotificationBeforeFirstMeasurement() {
        WeatherStation station = new WeatherStation();
        LoggerObserver logger = new LoggerObserver(silent());
        station.registerObserver(logger);

        station.notifyObservers(); // o'lchov yo'q

        assertEquals(0, logger.getEntries().size());
        assertNull(station.getCurrent());
    }

    @Test
    public void invalidHumidityIsRejectedAndObserversNotNotified() {
        WeatherStation station = new WeatherStation();
        LoggerObserver logger = new LoggerObserver(silent());
        station.registerObserver(logger);

        assertThrows(IllegalArgumentException.class, () -> station.setMeasurements(20.0, 120, 1000));

        assertEquals(0, logger.getEntries().size());
        assertThrows(IllegalArgumentException.class, () -> new WeatherData(20.0, 50, 0));
    }

    @Test
    public void nullObserverIsRejected() {
        WeatherStation station = new WeatherStation();
        assertThrows(IllegalArgumentException.class, () -> station.registerObserver(null));
    }

    @Test
    public void observerMayUnregisterItselfDuringNotification() {
        WeatherStation station = new WeatherStation();
        final int[] calls = {0};
        WeatherObserver oneShot = new WeatherObserver() {
            @Override
            public void update(WeatherData data) {
                calls[0]++;
                station.removeObserver(this); // xabar paytida o'zini chiqarib tashlaydi
            }

            @Override
            public String getName() {
                return "oneShot";
            }
        };
        LoggerObserver logger = new LoggerObserver(silent());
        station.registerObserver(oneShot);
        station.registerObserver(logger);

        station.setMeasurements(1.0, 1, 1000);
        station.setMeasurements(2.0, 2, 1000);

        assertEquals(1, calls[0]);                    // faqat bir marta chaqirildi
        assertEquals(2, logger.getEntries().size());  // logger esa ikkalasini oldi
    }
}
