package testkit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Test ishga tushiruvchi: berilgan sinflardagi @Test metodlarini topadi, bajaradi
 * va natijani chiqaradi. Bitta test yiqilsa ham qolganlari davom etadi.
 * Yiqilgan test bo'lsa, dastur 1 kodi bilan tugaydi (CI uchun qulay).
 */
public final class TestRunner {

    private TestRunner() {
    }

    public static void main(String[] args) throws Exception {
        Class<?>[] testClasses = {
                tests.WeatherStationTest.class,
                tests.ShippingCalculatorTest.class,
                tests.EventBusTest.class,
                tests.CommandManagerTest.class
        };

        int passed = 0;
        int failed = 0;

        for (Class<?> testClass : testClasses) {
            System.out.println("\n" + testClass.getSimpleName());
            Method[] methods = testClass.getDeclaredMethods();
            Arrays.sort(methods, Comparator.comparing(Method::getName));

            for (Method method : methods) {
                if (!method.isAnnotationPresent(Test.class)) {
                    continue;
                }
                Object instance = testClass.getDeclaredConstructor().newInstance();
                try {
                    method.invoke(instance);
                    passed++;
                    System.out.println("  [OK]    " + method.getName());
                } catch (InvocationTargetException ex) {
                    failed++;
                    System.out.println("  [XATO]  " + method.getName() + " -> " + ex.getCause());
                }
            }
        }

        System.out.println("\nNatija: " + passed + " ta o'tdi, " + failed + " ta yiqildi, jami "
                + (passed + failed) + " ta test.");
        if (failed > 0) {
            System.exit(1);
        }
    }
}
