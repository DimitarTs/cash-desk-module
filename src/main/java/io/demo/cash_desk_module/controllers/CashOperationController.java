package io.demo.cash_desk_module.controllers;

import io.demo.cash_desk_module.models.Transaction;
import io.demo.cash_desk_module.services.CashOperationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cash-operation")
public class CashOperationController {

    private final CashOperationService cashOperationService;

    public CashOperationController(CashOperationService cashOperationService) {
        this.cashOperationService = cashOperationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cashOperation(@Valid @RequestBody Transaction transaction) {
        cashOperationService.processCashOperation(transaction);
    }
}
