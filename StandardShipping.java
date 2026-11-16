package vazifa2;

/** Standart tarif: 10 000 + 1 500 * kg + 20 * km (so'm). */
public class StandardShipping implements ShippingStrategy {

    static final long BASE = 10_000;
    static final long PER_KG = 1_500;
    static final long PER_KM = 20;

    @Override
    public long calculate(ShippingOrder order) {
        return BASE
                + Math.round(PER_KG * order.getWeightKg())
                + PER_KM * order.getDistanceKm();
    }

    @Override
    public String getName() {
        return "Standart";
    }
}
