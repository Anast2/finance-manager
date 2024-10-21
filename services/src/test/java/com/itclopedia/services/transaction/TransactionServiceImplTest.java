package com.itclopedia.services.transaction;

import com.itclopedia.cources.dao.custom.category.CustomCategoryRepository;
import com.itclopedia.cources.dao.expense.ExpenseRepository;
import com.itclopedia.cources.dao.income.IncomeRepository;
import com.itclopedia.cources.dao.optional.category.OptionalCategoryRepository;
import com.itclopedia.cources.dao.finance.goals.FinanceGoalsRepository;

import com.itclopedia.cources.dao.user.UserRepository;
import com.itclopedia.cources.model.*;
import com.itclopedia.cources.services.transaction.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private CustomCategoryRepository customCategoryRepository;

    @Mock
    private IncomeRepository incomeRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private OptionalCategoryRepository optionalCategoryRepository;

    @Mock
    private FinanceGoalsRepository financeGoalsRepository;

    @Mock
    private UserRepository userRepository;

    private Transaction transaction;

    @InjectMocks
    TransactionServiceImpl transactionRegistration;

    @Test
    void incomeRegistration() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(1);
        transactionRegistration.incomeRegistration(transaction);
        verify(incomeRepository, times(1)).save(any(Income.class));
    }

    @BeforeEach
    void setUp() {
        transaction = new Transaction();
        transaction.setAmount(100);
    }

    @Test
    void expenseRegistrationOptionalCategory() {
        OptionalCategory optionalCategory = new OptionalCategory();
        optionalCategory.setName("Optional");
        optionalCategory.setOptionalCategoryId(1);
        User user = new User();
        user.setId(1);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(optionalCategoryRepository.findById(1)).thenReturn(Optional.of(optionalCategory));
        transactionRegistration.expenseRegistration(transaction, "Optional", 1, user.getId());
        verify(optionalCategoryRepository, times(1)).findById(1);
        verify(expenseRepository, times(2)).save(any(Expense.class));
        verify(optionalCategoryRepository, times(1))
                .updateOptionalCatCurrAmount(1, transaction.getAmount());
    }

    @Test
    void expenseRegistrationCustomCategory() {
        CustomCategory customCategory = new CustomCategory();
        customCategory.setName("Custom");
        customCategory.setCustomCategoryId(1);
        User user = new User();
        user.setId(1);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(customCategoryRepository.findById(1)).thenReturn(Optional.of(customCategory));
        transactionRegistration.expenseRegistration(transaction, "Custom", 1, user.getId());
        verify(customCategoryRepository, times(1)).findById(1);
        verify(expenseRepository, times(2)).save(any(Expense.class));
        verify(customCategoryRepository, times(1))
                .updateCustomCatCurrAmount(1, transaction.getAmount());
    }

    @Test
    void expenseRegistrationFinanceGoals() {
        FinanceGoals financeGoals = new FinanceGoals();
        financeGoals.setFinanceGoalId(1);
        financeGoals.setDescription("Finance goal");
        User user = new User();
        user.setId(1);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(financeGoalsRepository.findById(1)).thenReturn(Optional.of(financeGoals));
        transactionRegistration.expenseRegistration(transaction,
                "Finance goals", 1, user.getId());
        verify(expenseRepository, times(1)).save(any(Expense.class));
        verify(financeGoalsRepository, times(1))
                .updateCurrAmount(1, transaction.getAmount());
    }

}