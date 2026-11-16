package testkit;

import java.util.Objects;

/** Minimal assertion to'plami (JUnit'dagi Assertions'ning soddalashtirilgan analogi). */
public final class Assert {

    private Assert() {
    }

    public interface ThrowingRunnable {
        void run() throws Exception;
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void assertFalse(boolean condition, String message) {
        assertTrue(!condition, message);
    }

    public static void assertEquals(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("Kutilgan: <" + expected + ">, haqiqiy: <" + actual + ">");
        }
    }

    public static void assertEquals(long expected, long actual) {
        if (expected != actual) {
            throw new AssertionError("Kutilgan: <" + expected + ">, haqiqiy: <" + actual + ">");
        }
    }

    public static void assertNull(Object actual) {
        if (actual != null) {
            throw new AssertionError("Kutilgan: null, haqiqiy: <" + actual + ">");
        }
    }

    public static void assertSame(Object expected, Object actual) {
        if (expected != actual) {
            throw new AssertionError("Ob'ektlar bir xil emas: <" + expected + "> va <" + actual + ">");
        }
    }

    /** Berilgan turdagi xato chiqishini tekshiradi. */
    public static <T extends Throwable> T assertThrows(Class<T> type, ThrowingRunnable action) {
        try {
            action.run();
        } catch (Throwable thrown) {
            if (type.isInstance(thrown)) {
                return type.cast(thrown);
            }
            throw new AssertionError("Kutilgan xato: " + type.getSimpleName()
                    + ", haqiqiy: " + thrown.getClass().getSimpleName(), thrown);
        }
        throw new AssertionError("Kutilgan xato chiqmadi: " + type.getSimpleName());
    }
}
