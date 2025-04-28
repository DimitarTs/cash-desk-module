package io.demo.cash_desk_module.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CurrencyBalance {
    private BigDecimal startBalance;
    private Cash startDenominations;
    private BigDecimal endBalance;
    private Cash endDenominations;
}
