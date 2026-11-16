package vazifa3.eventbus;

/** Yetkazish paytida kuzatuvchi xato bersa, shu yozuv saqlanadi (bus to'xtamaydi). */
public final class DeliveryFailure {

    private final Subscription subscription;
    private final Event event;
    private final RuntimeException error;

    DeliveryFailure(Subscription subscription, Event event, RuntimeException error) {
        this.subscription = subscription;
        this.event = event;
        this.error = error;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public Event getEvent() {
        return event;
    }

    public RuntimeException getError() {
        return error;
    }

    @Override
    public String toString() {
        return "Xato{obuna#" + subscription.getId() + ", " + event + ", " + error + "}";
    }
}
