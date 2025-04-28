package io.demo.cash_desk_module.controllers;

import io.demo.cash_desk_module.exceptions.BadRequestException;
import io.demo.cash_desk_module.models.CashBalance;
import io.demo.cash_desk_module.services.CashBalanceService;
import jakarta.validation.constraints.Max;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/cash-balance")
public class CashBalanceController {

    private final CashBalanceService cashBalanceService;

    public CashBalanceController(CashBalanceService cashBalanceService) {
        this.cashBalanceService = cashBalanceService;
    }

    @GetMapping
    public List<CashBalance> cashBalance(
            @RequestParam(required = false) ZonedDateTime dateFrom,
            @RequestParam(required = false) ZonedDateTime dateTo,
            @RequestParam(required = false) String cashier) {

        if (dateFrom != null && dateTo != null && dateFrom.isAfter(dateTo)) {
            throw new BadRequestException("dateFrom should not be after dateTo");
        }

        return cashBalanceService.getCashBalance(dateFrom, dateTo, cashier);
    }
}
