package com.itclopedia.cources.controller;

import com.itclopedia.cources.dto.TransactionRequestDto;
import com.itclopedia.cources.model.Transaction;
import com.itclopedia.cources.services.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/transaction/registration")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/expense")
    public ResponseEntity<?> expenseRegistration(@RequestBody TransactionRequestDto transactionRequestDto) {
        transactionService.expenseRegistration(transactionRequestDto.getTransaction(), transactionRequestDto.getCategory(), transactionRequestDto.getIdCategory(), transactionRequestDto.getUserId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/income")
    public ResponseEntity<?> incomeRegistration(@RequestBody Transaction transaction) {
        transactionService.incomeRegistration(transaction);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
