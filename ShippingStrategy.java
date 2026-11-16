package vazifa2;

/** Strategy interfeysi: jo'natish narxini hisoblash algoritmi. */
public interface ShippingStrategy {

    /** @return jo'natish narxi (so'mda). */
    long calculate(ShippingOrder order);

    /** Tarif nomi. */
    String getName();
}
