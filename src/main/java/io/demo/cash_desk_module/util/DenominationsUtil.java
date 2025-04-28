package io.demo.cash_desk_module.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DenominationsUtil {

    public static final Map<String, Set<BigDecimal>> denominations = Map.of(
            "EUR", Set.of(
                    BigDecimal.valueOf(0.01),
                    BigDecimal.valueOf(0.02),
                    BigDecimal.valueOf(0.05),
                    BigDecimal.valueOf(0.10),
                    BigDecimal.valueOf(0.20),
                    BigDecimal.valueOf(0.50),
                    BigDecimal.valueOf(1),
                    BigDecimal.valueOf(2),
                    BigDecimal.valueOf(5),
                    BigDecimal.valueOf(10),
                    BigDecimal.valueOf(20),
                    BigDecimal.valueOf(50),
                    BigDecimal.valueOf(100),
                    BigDecimal.valueOf(200),
                    BigDecimal.valueOf(500)
            ),
            "BGN", Set.of(
                    BigDecimal.valueOf(0.01),
                    BigDecimal.valueOf(0.02),
                    BigDecimal.valueOf(0.05),
                    BigDecimal.valueOf(0.10),
                    BigDecimal.valueOf(0.20),
                    BigDecimal.valueOf(0.50),
                    BigDecimal.valueOf(1),
                    BigDecimal.valueOf(2),
                    BigDecimal.valueOf(5),
                    BigDecimal.valueOf(10),
                    BigDecimal.valueOf(20),
                    BigDecimal.valueOf(50),
                    BigDecimal.valueOf(100)
            )
    );
}
