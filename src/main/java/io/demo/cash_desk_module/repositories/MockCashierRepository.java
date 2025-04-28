package io.demo.cash_desk_module.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.demo.cash_desk_module.exceptions.BadRequestException;
import io.demo.cash_desk_module.models.Cashier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
@Component
public class MockCashierRepository {
    private final Map<Long, Cashier> cashierMap;

    public MockCashierRepository() throws IOException {
        this.cashierMap = new HashMap<>();
        File cashierFile = new File("config/cashiers.txt");
        if (!cashierFile.exists()) {
            log.error("Cashier config file not found!");
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        List<Cashier> cashiers = objectMapper.readValue(cashierFile, new TypeReference<List<Cashier>>() {});
        for (Cashier cashier : cashiers) {
            cashierMap.put(cashier.getId(), cashier);
        }
    }

    public Cashier findById(Long id) {
        return cashierMap.get(id);
    }

    public List<Cashier> findCashierByNameLike(String like) {
        List<Cashier> cashierList = new ArrayList<>();
        for (Cashier cashier : cashierMap.values()) {
            if (like == null || cashier.getName().toUpperCase().contains(like.toUpperCase())) {
                cashierList.add(cashier);
            }
        }
        if (cashierList.isEmpty())
            throw new BadRequestException("No cashiers found with name like %s".formatted(like));

        return cashierList;
    }
}
