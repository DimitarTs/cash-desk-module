package io.demo.cash_desk_module.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Cashier {
    private Long id;
    private String name;
    private Map<String, Cash> cash = new HashMap<>();
}
