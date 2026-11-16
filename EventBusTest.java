package tests;

import testkit.Test;
import vazifa3.eventbus.EventBus;
import vazifa3.eventbus.PriorityDelivery;
import vazifa3.eventbus.ReverseDelivery;
import vazifa3.eventbus.SequentialDelivery;
import vazifa3.eventbus.Subscription;

import java.util.ArrayList;
import java.util.List;

import static testkit.Assert.assertEquals;
import static testkit.Assert.assertFalse;
import static testkit.Assert.assertThrows;
import static testkit.Assert.assertTrue;

/** Vazifa 3: EventBus (Observer + Strategy) testlari. */
public class EventBusTest {

    /** Kuzatuvchi chaqirilish tartibini ro'yxatga yozib boradi. */
    private static void recordInto(EventBus bus, List<String> log, String name, int priority) {
        bus.subscribe("t", priority, e -> log.add(name));
    }

    @Test
    public void listenerReceivesOnlyMatchingTopic() {
        EventBus bus = new EventBus();
        List<Object> received = new ArrayList<>();
        bus.subscribe("a", e -> received.add(e.getPayload()));

        assertEquals(1, bus.publish("a", "birinchi"));
        assertEquals(0, bus.publish("b", "ikkinchi"));

        assertEquals(1, received.size());
        assertEquals("birinchi", received.get(0));
    }

    @Test
    public void wildcardSubscriptionReceivesAllTopics() {
        EventBus bus = new EventBus();
        List<String> topics = new ArrayList<>();
        bus.subscribe(Subscription.ALL_TOPICS, e -> topics.add(e.getTopic()));

        bus.publish("x", 1);
        bus.publish("y", 2);

        assertEquals(2, topics.size());
        assertEquals("x", topics.get(0));
        assertEquals("y", topics.get(1));
    }

    @Test
    public void unsubscribedListenerStopsReceiving() {
        EventBus bus = new EventBus();
        List<String> log = new ArrayList<>();
        Subscription sub = bus.subscribe("t", e -> log.add("keldi"));

        bus.publish("t", null);
        assertTrue(bus.unsubscribe(sub), "obuna bekor qilinishi kerak");
        assertFalse(sub.isActive(), "bekor qilingan obuna faol bo'lmasligi kerak");
        assertFalse(bus.unsubscribe(sub), "ikkinchi marta bekor qilish false qaytarishi kerak");
        bus.publish("t", null);

        assertEquals(1, log.size());
        assertEquals(0, bus.getSubscriptionCount());
    }

    @Test
    public void sequentialStrategyDeliversInSubscriptionOrder() {
        EventBus bus = new EventBus(new SequentialDelivery());
        List<String> log = new ArrayList<>();
        recordInto(bus, log, "A", 1);
        recordInto(bus, log, "B", 9);
        recordInto(bus, log, "C", 5);

        bus.publish("t", null);

        assertEquals("[A, B, C]", log.toString());
    }

    @Test
    public void priorityStrategyDeliversHighestPriorityFirstAndIsStable() {
        EventBus bus = new EventBus(new PriorityDelivery());
        List<String> log = new ArrayList<>();
        recordInto(bus, log, "A", 1);
        recordInto(bus, log, "B", 9);
        recordInto(bus, log, "C", 5);
        recordInto(bus, log, "D", 5); // C bilan teng: C birinchi yozilgan, shuning uchun C oldin

        bus.publish("t", null);

        assertEquals("[B, C, D, A]", log.toString());
    }

    @Test
    public void reverseStrategyDeliversLastSubscribedFirst() {
        EventBus bus = new EventBus(new ReverseDelivery());
        List<String> log = new ArrayList<>();
        recordInto(bus, log, "A", 0);
        recordInto(bus, log, "B", 0);
        recordInto(bus, log, "C", 0);

        bus.publish("t", null);

        assertEquals("[C, B, A]", log.toString());
    }

    @Test
    public void deliveryStrategyCanBeSwitchedAtRuntime() {
        EventBus bus = new EventBus();
        List<String> log = new ArrayList<>();
        recordInto(bus, log, "A", 1);
        recordInto(bus, log, "B", 2);

        bus.publish("t", null);                          // FIFO: A, B
        bus.setDeliveryStrategy(new PriorityDelivery());
        bus.publish("t", null);                          // priority: B, A

        assertEquals("[A, B, B, A]", log.toString());
    }

    @Test
    public void failingListenerDoesNotBreakOthersAndIsRecorded() {
        EventBus bus = new EventBus();
        List<String> log = new ArrayList<>();
        bus.subscribe("t", e -> log.add("birinchi"));
        bus.subscribe("t", e -> {
            throw new IllegalStateException("buzuq kuzatuvchi");
        });
        bus.subscribe("t", e -> log.add("uchinchi"));

        int delivered = bus.publish("t", null);

        assertEquals(2, delivered);
        assertEquals("[birinchi, uchinchi]", log.toString());
        assertEquals(1, bus.getFailures().size());
        assertTrue(bus.getFailures().get(0).getError() instanceof IllegalStateException,
                "xato turi saqlanishi kerak");
    }

    @Test
    public void invalidArgumentsAreRejected() {
        EventBus bus = new EventBus();
        assertThrows(IllegalArgumentException.class, () -> bus.subscribe("t", null));
        assertThrows(IllegalArgumentException.class, () -> bus.subscribe(" ", e -> { }));
        assertThrows(IllegalArgumentException.class, () -> bus.publish("", "x"));
        assertThrows(IllegalArgumentException.class, () -> bus.setDeliveryStrategy(null));
    }

    @Test
    public void listenerUnsubscribedDuringDeliveryIsSkipped() {
        EventBus bus = new EventBus();
        List<String> log = new ArrayList<>();
        Subscription[] second = new Subscription[1];
        bus.subscribe("t", e -> {
            log.add("birinchi");
            bus.unsubscribe(second[0]); // ikkinchisini yetkazishdan oldin bekor qiladi
        });
        second[0] = bus.subscribe("t", e -> log.add("ikkinchi"));

        bus.publish("t", null);

        assertEquals("[birinchi]", log.toString());
    }
}
