package vazifa3.eventbus;

import java.util.ArrayList;
import java.util.List;

/** FIFO: obuna bo'lish tartibida yetkazadi (birinchi yozilgan - birinchi oladi). */
public class SequentialDelivery implements DeliveryStrategy {

    @Override
    public List<Subscription> arrange(List<Subscription> matching) {
        return new ArrayList<>(matching);
    }

    @Override
    public String getName() {
        return "Ketma-ket (FIFO)";
    }
}
