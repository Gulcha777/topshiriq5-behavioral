package tests;

import testkit.Test;
import vazifa2.ExpressShipping;
import vazifa2.InternationalShipping;
import vazifa2.ShippingCalculator;
import vazifa2.ShippingOrder;
import vazifa2.ShippingStrategy;
import vazifa2.StandardShipping;

import static testkit.Assert.assertEquals;
import static testkit.Assert.assertSame;
import static testkit.Assert.assertThrows;

/** Vazifa 2: Strategy andozasi testlari. */
public class ShippingCalculatorTest {

    // 2.5 kg, 300 km, 500 000 so'm qiymat
    private final ShippingOrder order = new ShippingOrder(2.5, 300, 500_000);

    @Test
    public void standardPriceIsCorrect() {
        // 10 000 + 1 500*2.5 + 20*300 = 10 000 + 3 750 + 6 000
        assertEquals(19_750, new ShippingCalculator(new StandardShipping()).calculate(order));
    }

    @Test
    public void expressPriceIsCorrect() {
        // 25 000 + 2 500*2.5 + 40*300 = 25 000 + 6 250 + 12 000
        assertEquals(43_250, new ShippingCalculator(new ExpressShipping()).calculate(order));
    }

    @Test
    public void internationalPriceIgnoresDistanceAndAddsCustoms() {
        // 150 000 + 12 000*2.5 + 5% * 500 000 = 150 000 + 30 000 + 25 000
        ShippingCalculator calculator = new ShippingCalculator(new InternationalShipping());
        assertEquals(205_000, calculator.calculate(order));
        assertEquals(205_000, calculator.calculate(new ShippingOrder(2.5, 9_999, 500_000)));
    }

    @Test
    public void strategyCanBeSwitchedAtRuntime() {
        ShippingCalculator calculator = new ShippingCalculator(new StandardShipping());
        assertEquals(19_750, calculator.calculate(order));

        calculator.setStrategy(new ExpressShipping());
        assertEquals(43_250, calculator.calculate(order));

        calculator.setStrategy(new InternationalShipping());
        assertEquals(205_000, calculator.calculate(order));
    }

    @Test
    public void customStrategyWorksWithoutChangingCalculator() {
        ShippingStrategy free = new ShippingStrategy() {
            @Override
            public long calculate(ShippingOrder o) {
                return 0;
            }

            @Override
            public String getName() {
                return "Bepul";
            }
        };
        ShippingCalculator calculator = new ShippingCalculator(new StandardShipping());
        calculator.setStrategy(free);

        assertEquals(0, calculator.calculate(order));
        assertSame(free, calculator.getStrategy());
    }

    @Test
    public void invalidOrdersAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new ShippingOrder(0, 10, 0));
        assertThrows(IllegalArgumentException.class, () -> new ShippingOrder(-1, 10, 0));
        assertThrows(IllegalArgumentException.class, () -> new ShippingOrder(1, -5, 0));
        assertThrows(IllegalArgumentException.class, () -> new ShippingOrder(1, 5, -1));
    }

    @Test
    public void nullStrategyAndNullOrderAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new ShippingCalculator(null));
        ShippingCalculator calculator = new ShippingCalculator(new StandardShipping());
        assertThrows(IllegalArgumentException.class, () -> calculator.setStrategy(null));
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate(null));
    }
}
