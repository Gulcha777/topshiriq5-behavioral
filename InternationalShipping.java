package vazifa2;

/**
 * Xalqaro tarif: 150 000 + 12 000 * kg + e'lon qilingan qiymatning 5% bojxona yig'imi.
 * Masofa hisobga olinmaydi (xalqaro yagona tarif).
 */
public class InternationalShipping implements ShippingStrategy {

    static final long BASE = 150_000;
    static final long PER_KG = 12_000;
    static final long CUSTOMS_PERCENT = 5;

    @Override
    public long calculate(ShippingOrder order) {
        return BASE
                + Math.round(PER_KG * order.getWeightKg())
                + order.getDeclaredValue() * CUSTOMS_PERCENT / 100;
    }

    @Override
    public String getName() {
        return "Xalqaro";
    }
}
