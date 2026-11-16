package vazifa3.eventbus;

import java.util.List;

/**
 * Strategy interfeysi: hodisa yetkazish TARTIBINI belgilaydi.
 * EventBus tartibni o'zi bilmaydi - strategiyaga topshiradi.
 */
public interface DeliveryStrategy {

    /**
     * @param matching mavzuga mos keluvchi obunalar (ro'yxatdan o'tish tartibida)
     * @return yetkazish tartibida joylashtirilgan YANGI ro'yxat (kirish ro'yxati o'zgarmaydi)
     */
    List<Subscription> arrange(List<Subscription> matching);

    String getName();
}
