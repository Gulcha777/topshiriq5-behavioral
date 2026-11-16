package vazifa3.eventbus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** LIFO: oxirgi yozilgan obuna birinchi oladi (middleware/stek mantiqi). */
public class ReverseDelivery implements DeliveryStrategy {

    @Override
    public List<Subscription> arrange(List<Subscription> matching) {
        List<Subscription> reversed = new ArrayList<>(matching);
        Collections.reverse(reversed);
        return reversed;
    }

    @Override
    public String getName() {
        return "Teskari (LIFO)";
    }
}
