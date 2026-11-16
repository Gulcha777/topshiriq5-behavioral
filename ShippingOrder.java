package vazifa2;

/** Jo'natma ma'lumotlari (o'zgarmas). Barcha strategiyalar shu ob'ektdan foydalanadi. */
public final class ShippingOrder {

    private final double weightKg;
    private final int distanceKm;
    private final long declaredValue; // e'lon qilingan qiymat, so'mda

    public ShippingOrder(double weightKg, int distanceKm, long declaredValue) {
        if (weightKg <= 0) {
            throw new IllegalArgumentException("Og'irlik musbat bo'lishi kerak: " + weightKg);
        }
        if (distanceKm < 0) {
            throw new IllegalArgumentException("Masofa manfiy bo'lishi mumkin emas: " + distanceKm);
        }
        if (declaredValue < 0) {
            throw new IllegalArgumentException("Qiymat manfiy bo'lishi mumkin emas: " + declaredValue);
        }
        this.weightKg = weightKg;
        this.distanceKm = distanceKm;
        this.declaredValue = declaredValue;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public int getDistanceKm() {
        return distanceKm;
    }

    public long getDeclaredValue() {
        return declaredValue;
    }

    @Override
    public String toString() {
        return String.format(java.util.Locale.ROOT, "%.1f kg, %d km, qiymat %d so'm",
                weightKg, distanceKm, declaredValue);
    }
}
