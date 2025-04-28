package io.demo.cash_desk_module.models;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.Map;

@Getter
@Setter
public class Transaction {
    private Long id;

    private ZonedDateTime date;

    @NotNull
    private Long cashierId;

    @NotNull
    private Currency currency;

    @NotNull
    private Cash cash;
}
