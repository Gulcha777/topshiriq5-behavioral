package vazifa3.eventbus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * EventBus = Observer (obuna/bekor qilish/xabar berish) + Strategy (yetkazish tartibi).
 *
 * - Observer: subscribe / unsubscribe / publish
 * - Strategy: setDeliveryStrategy() orqali runtime'da tartib almashtiriladi
 * - Bir kuzatuvchi xato bersa, qolganlarga yetkazish davom etadi (xatolar getFailures()da)
 */
public class EventBus {

    private final List<Subscription> subscriptions = new CopyOnWriteArrayList<>();
    private final List<DeliveryFailure> failures = new CopyOnWriteArrayList<>();
    private final AtomicLong subscriptionSequence = new AtomicLong();
    private final AtomicLong publishedCount = new AtomicLong();
    private volatile DeliveryStrategy deliveryStrategy;

    public EventBus() {
        this(new SequentialDelivery());
    }

    public EventBus(DeliveryStrategy deliveryStrategy) {
        setDeliveryStrategy(deliveryStrategy);
    }

    // ---------- Strategy ----------

    public final void setDeliveryStrategy(DeliveryStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Yetkazish strategiyasi null bo'lishi mumkin emas");
        }
        this.deliveryStrategy = strategy;
    }

    public DeliveryStrategy getDeliveryStrategy() {
        return deliveryStrategy;
    }

    // ---------- Observer ----------

    public Subscription subscribe(String topic, EventListener listener) {
        return subscribe(topic, 0, listener);
    }

    public Subscription subscribe(String topic, int priority, EventListener listener) {
        if (topic == null || topic.trim().isEmpty()) {
            throw new IllegalArgumentException("Mavzu bo'sh bo'lishi mumkin emas");
        }
        if (listener == null) {
            throw new IllegalArgumentException("Kuzatuvchi null bo'lishi mumkin emas");
        }
        Subscription subscription =
                new Subscription(subscriptionSequence.incrementAndGet(), topic, priority, listener);
        subscriptions.add(subscription);
        return subscription;
    }

    /** @return true - obuna bekor qilindi; false - obuna allaqachon yo'q edi. */
    public boolean unsubscribe(Subscription subscription) {
        if (subscription == null) {
            return false;
        }
        boolean removed = subscriptions.remove(subscription);
        if (removed) {
            subscription.deactivate();
        }
        return removed;
    }

    /**
     * Hodisani e'lon qiladi.
     *
     * @return xatosiz yetkazilgan kuzatuvchilar soni
     */
    public int publish(String topic, Object payload) {
        Event event = new Event(topic, payload);
        publishedCount.incrementAndGet();

        List<Subscription> matching = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            if (subscription.isActive() && subscription.matches(topic)) {
                matching.add(subscription);
            }
        }

        int delivered = 0;
        for (Subscription subscription : deliveryStrategy.arrange(matching)) {
            if (!subscription.isActive()) {
                continue; // yetkazish paytida bekor qilingan bo'lsa - o'tkazib yuboramiz
            }
            try {
                subscription.getListener().onEvent(event);
                delivered++;
            } catch (RuntimeException ex) {
                failures.add(new DeliveryFailure(subscription, event, ex));
            }
        }
        return delivered;
    }

    // ---------- Holat ----------

    public int getSubscriptionCount() {
        return subscriptions.size();
    }

    public long getPublishedCount() {
        return publishedCount.get();
    }

    public List<DeliveryFailure> getFailures() {
        return Collections.unmodifiableList(new ArrayList<>(failures));
    }

    public void clearFailures() {
        failures.clear();
    }
}
