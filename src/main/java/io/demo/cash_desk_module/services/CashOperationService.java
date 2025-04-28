package io.demo.cash_desk_module.services;

import io.demo.cash_desk_module.exceptions.BadRequestException;
import io.demo.cash_desk_module.models.Cash;
import io.demo.cash_desk_module.models.CashBalance;
import io.demo.cash_desk_module.models.Cashier;
import io.demo.cash_desk_module.models.Transaction;
import io.demo.cash_desk_module.repositories.MockCashierRepository;
import io.demo.cash_desk_module.repositories.MockTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Slf4j
@Service
public class CashOperationService {

    private final MockCashierRepository cashierRepository;
    private final MockTransactionRepository transactionRepository;
    private final CashBalanceService cashBalanceService;

    public CashOperationService(
            MockCashierRepository cashierRepository,
            MockTransactionRepository transactionRepository, CashBalanceService cashBalanceService) {
        this.cashierRepository = cashierRepository;
        this.transactionRepository = transactionRepository;
        this.cashBalanceService = cashBalanceService;
    }

    public void processCashOperation(Transaction transaction) {
        if (!transaction.getCash().isValid(transaction.getCurrency())) {
            throw new BadRequestException("Invalid denominations!");
        }

        Cashier cashier = cashierRepository.findById(transaction.getCashierId());
        CashBalance cashBalance = cashBalanceService.getCashBalance(null, null, cashier.getName()).get(0);
        Cash current = cashBalance.getCash().get(transaction.getCurrency().getCurrencyCode()).getEndDenominations();
        for (BigDecimal denomination : transaction.getCash().keySet()) {
            if (transaction.getCash().get(denomination) < 0 &&
                    (current.get(denomination) == null || transaction.getCash().get(denomination) + current.get(denomination) < 0)) {
                throw new BadRequestException("Insufficient funds!");
            }
        }

        log.info("Registering cash operation by cashier {} in the amount of {} {}",
                cashier.getName(),
                transaction.getCash().getTotal(),
                transaction.getCurrency()
        );

        transaction.setDate(ZonedDateTime.now());
        transactionRepository.saveTransaction(transaction);
    }
}
