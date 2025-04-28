package io.demo.cash_desk_module.models;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class CashBalance {
    private String cashier;
    Map<String, CurrencyBalance> cash = new HashMap<>();
}
