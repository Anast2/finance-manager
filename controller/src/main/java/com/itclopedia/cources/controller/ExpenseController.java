package com.itclopedia.cources.controller;

import com.itclopedia.cources.services.expense.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping()
    public ResponseEntity<?> get() {
        return new ResponseEntity<>(expenseService.checkFamilyStatusForOptionalCatLastMonth(), HttpStatus.OK);
    }

}
