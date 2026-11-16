package vazifa3.eventbus;

/** Observer interfeysi (funksional): lambda yoki sinf sifatida berilishi mumkin. */
@FunctionalInterface
public interface EventListener {

    void onEvent(Event event);
}
