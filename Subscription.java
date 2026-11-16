package vazifa3.eventbus;

/** Obuna: kim, qaysi mavzuga, qanday ustuvorlik bilan yozilgan. Bekor qilish uchun "tutqich". */
public final class Subscription {

    /** Barcha mavzularni qamrab oluvchi belgi. */
    public static final String ALL_TOPICS = "*";

    private final long id;
    private final String topic;
    private final int priority;
    private final EventListener listener;
    private volatile boolean active = true;

    Subscription(long id, String topic, int priority, EventListener listener) {
        this.id = id;
        this.topic = topic;
        this.priority = priority;
        this.listener = listener;
    }

    public long getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public int getPriority() {
        return priority;
    }

    public EventListener getListener() {
        return listener;
    }

    public boolean isActive() {
        return active;
    }

    void deactivate() {
        this.active = false;
    }

    boolean matches(String eventTopic) {
        return ALL_TOPICS.equals(topic) || topic.equals(eventTopic);
    }
}
