package io.demo.cash_desk_module.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.demo.cash_desk_module.models.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MockTransactionRepository {
    private List<Transaction> transactions = new ArrayList<>();

    public MockTransactionRepository() throws IOException {
        File transactionsFile = new File("config/transactions.txt");
        if (!transactionsFile.exists()) {
            log.error("Transactions config file not found!");
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.transactions = objectMapper.readValue(transactionsFile, new TypeReference<List<Transaction>>() {
        });
        this.transactions.sort(Comparator.comparing(Transaction::getDate));
    }

    public List<Transaction> getTransactions(ZonedDateTime dateFrom, ZonedDateTime dateTo, Long cashierId) {
        return transactions.stream().filter(t ->
                        (dateFrom == null || !t.getDate().isBefore(dateFrom)) &&
                        (dateTo == null || !t.getDate().isAfter(dateTo)) &&
                        Objects.equals(cashierId, t.getCashierId())
        ).collect(Collectors.toList());
    }

    public void saveTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}
