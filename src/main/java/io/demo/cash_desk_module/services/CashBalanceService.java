package io.demo.cash_desk_module.services;

import io.demo.cash_desk_module.models.*;
import io.demo.cash_desk_module.repositories.MockCashierRepository;
import io.demo.cash_desk_module.repositories.MockTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CashBalanceService {
    MockCashierRepository cashierRepository;
    MockTransactionRepository transactionRepository;

    public CashBalanceService(MockCashierRepository cashierRepository, MockTransactionRepository transactionRepository) {
        this.cashierRepository = cashierRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<CashBalance> getCashBalance(ZonedDateTime dateFrom, ZonedDateTime dateTo, String cashierName) {
        List<Cashier> cashiers = cashierRepository.findCashierByNameLike(cashierName);

        log.info("Returning cash balance for cashier(s) {}: {} - {}",
                Strings.join(cashiers.stream().map(Cashier::getName).toList(), ','),
                dateFrom == null ? "N/A" : dateFrom,
                dateTo == null ? "N/A" : dateTo);

        List<CashBalance> cashBalances = new ArrayList<>();
        for (Cashier cashier : cashiers) {
            CashBalance cashBalance = getCashBalance(cashier, dateFrom, dateTo);
            cashBalances.add(cashBalance);
        }

        return cashBalances;
    }

    private CashBalance getCashBalance(Cashier cashier, ZonedDateTime dateFrom, ZonedDateTime dateTo) {
        List<Transaction> transactions = transactionRepository.getTransactions(null, dateTo, cashier.getId());
        CashBalance cashBalance = new CashBalance();
        cashBalance.setCashier(cashier.getName());

        Map<String, Cash> cashes = new HashMap<>();
        cashes.put("EUR", (Cash) cashier.getCash().get("EUR").clone());
        cashBalance.getCash().put("EUR", new CurrencyBalance());
        cashes.put("BGN", (Cash) cashier.getCash().get("BGN").clone());
        cashBalance.getCash().put("BGN", new CurrencyBalance());

        transactions.stream().filter(t -> dateFrom != null && t.getDate().isBefore(dateFrom)).forEach(t -> {
            cashes.get(t.getCurrency().getCurrencyCode()).add(t.getCash());
        });
        for (String currency : cashes.keySet()) {
            cashBalance.getCash().get(currency).setStartBalance(cashes.get(currency).getTotal());
            cashBalance.getCash().get(currency).setStartDenominations((Cash) cashes.get(currency).clone());
        }

        transactions.stream().filter(t -> dateFrom == null || !t.getDate().isBefore(dateFrom)).forEach(t -> {
            cashes.get(t.getCurrency().getCurrencyCode()).add(t.getCash());
        });
        for (String currency : cashes.keySet()) {
            cashBalance.getCash().get(currency).setEndBalance(cashes.get(currency).getTotal());
            cashBalance.getCash().get(currency).setEndDenominations( cashes.get(currency));
        }

        return cashBalance;
    }
}
