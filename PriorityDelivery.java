package vazifa3.eventbus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Ustuvorlik bo'yicha: priority katta bo'lgan obuna birinchi oladi.
 * Teng ustuvorlikda ro'yxatdan o'tish tartibi saqlanadi (barqaror saralash).
 */
public class PriorityDelivery implements DeliveryStrategy {

    @Override
    public List<Subscription> arrange(List<Subscription> matching) {
        List<Subscription> sorted = new ArrayList<>(matching);
        sorted.sort(Comparator.comparingInt(Subscription::getPriority).reversed());
        return sorted;
    }

    @Override
    public String getName() {
        return "Ustuvorlik bo'yicha";
    }
}
