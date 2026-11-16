package vazifa2;

/**
 * Context: strategiyani saqlaydi va hisoblashni unga topshiradi.
 * Strategiya runtime'da setStrategy() orqali almashtiriladi (if/else kerak emas, OCP).
 */
public class ShippingCalculator {

    private ShippingStrategy strategy;

    public ShippingCalculator(ShippingStrategy strategy) {
        setStrategy(strategy);
    }

    public final void setStrategy(ShippingStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Strategiya null bo'lishi mumkin emas");
        }
        this.strategy = strategy;
    }

    public ShippingStrategy getStrategy() {
        return strategy;
    }

    public long calculate(ShippingOrder order) {
        if (order == null) {
            throw new IllegalArgumentException("Buyurtma null bo'lishi mumkin emas");
        }
        return strategy.calculate(order);
    }
}
