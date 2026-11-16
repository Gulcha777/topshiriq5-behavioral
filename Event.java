package vazifa3.eventbus;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

/** Hodisa: mavzu (topic), yuklama (payload), vaqt va noyob raqam. O'zgarmas ob'ekt. */
public final class Event {

    private static final AtomicLong SEQUENCE = new AtomicLong();

    private final long id;
    private final String topic;
    private final Object payload;
    private final Instant timestamp;

    public Event(String topic, Object payload) {
        if (topic == null || topic.trim().isEmpty()) {
            throw new IllegalArgumentException("Hodisa mavzusi bo'sh bo'lishi mumkin emas");
        }
        this.id = SEQUENCE.incrementAndGet();
        this.topic = topic;
        this.payload = payload;
        this.timestamp = Instant.now();
    }

    public long getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public Object getPayload() {
        return payload;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Event#" + id + "{" + topic + ", " + payload + "}";
    }
}
