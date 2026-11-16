package vazifa2;

/** Ekspress tarif (tezkor yetkazish): 25 000 + 2 500 * kg + 40 * km (so'm). */
public class ExpressShipping implements ShippingStrategy {

    static final long BASE = 25_000;
    static final long PER_KG = 2_500;
    static final long PER_KM = 40;

    @Override
    public long calculate(ShippingOrder order) {
        return BASE
                + Math.round(PER_KG * order.getWeightKg())
                + PER_KM * order.getDistanceKm();
    }

    @Override
    public String getName() {
        return "Ekspress";
    }
}
