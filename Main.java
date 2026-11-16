package vazifa2;

/** Vazifa 2 demosi: Strategy andozasi (jo'natish narxi). */
public class Main {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        System.out.println("=== VAZIFA 2: Strategy (jo'natish narxi) ===");

        ShippingOrder order = new ShippingOrder(2.5, 300, 500_000);
        System.out.println("Buyurtma: " + order);

        ShippingCalculator calculator = new ShippingCalculator(new StandardShipping());
        print(calculator, order);

        calculator.setStrategy(new ExpressShipping());     // runtime'da almashtirish
        print(calculator, order);

        calculator.setStrategy(new InternationalShipping()); // yana almashtirish
        print(calculator, order);
    }

    private static void print(ShippingCalculator calculator, ShippingOrder order) {
        System.out.printf(java.util.Locale.ROOT, "  %-9s tarif: %,d so'm%n",
                calculator.getStrategy().getName(), calculator.calculate(order));
    }
}
