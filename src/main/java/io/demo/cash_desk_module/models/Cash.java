package io.demo.cash_desk_module.models;

import io.demo.cash_desk_module.exceptions.BadRequestException;
import io.demo.cash_desk_module.util.DenominationsUtil;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cash extends HashMap<BigDecimal, Long> {
    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal denomination : this.keySet()) {
            total = total.add(denomination.multiply(BigDecimal.valueOf(get(denomination))));
        }
        return total;
    }

    public void add(Cash cash) {
        if (cash == null)
            return;

        for (BigDecimal denomination : cash.keySet()) {
            this.merge(denomination, cash.get(denomination), Long::sum);
        }
    }

    public boolean isValid(Currency currency) {
        Set<BigDecimal> validDenominations = DenominationsUtil.denominations.get(currency.getCurrencyCode());
        for (BigDecimal denomination : keySet()) {
            if (!validDenominations.contains(denomination)) {
                return false;
            }
        }
        return true;
    }
}
