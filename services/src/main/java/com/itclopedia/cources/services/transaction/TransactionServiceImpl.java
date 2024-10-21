package com.itclopedia.cources.services.transaction;

import com.itclopedia.cources.dao.custom.category.CustomCategoryRepository;
import com.itclopedia.cources.dao.payment.reminder.PaymReminderRepository;
import com.itclopedia.cources.dao.expense.ExpenseRepository;
import com.itclopedia.cources.dao.finance.goals.FinanceGoalsRepository;
import com.itclopedia.cources.dao.income.IncomeRepository;
import com.itclopedia.cources.dao.optional.category.OptionalCategoryRepository;
import com.itclopedia.cources.dao.user.UserRepository;
import com.itclopedia.cources.exceptions.EntityNotFoundException;
import com.itclopedia.cources.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final CustomCategoryRepository customCategoryRepository;
    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final OptionalCategoryRepository optionalCategoryRepository;
    private final PaymReminderRepository paymReminderRepository;
    private final FinanceGoalsRepository financeGoalsRepository;
    private final UserRepository userRepository;

    @Autowired
    public TransactionServiceImpl(CustomCategoryRepository customCategoryRepository,
                                  IncomeRepository incomeRepository,
                                  ExpenseRepository expenseRepository,
                                  OptionalCategoryRepository optionalCategoryRepository,
                                  PaymReminderRepository paymReminderRepository,
                                  FinanceGoalsRepository financeGoalsRepository,
                                  UserRepository userRepository) {
        this.customCategoryRepository = customCategoryRepository;
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
        this.optionalCategoryRepository = optionalCategoryRepository;
        this.paymReminderRepository = paymReminderRepository;
        this.financeGoalsRepository = financeGoalsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void incomeRegistration(Transaction transaction) {
        Income income = new Income(transaction);
        incomeRepository.save(income);
    }

    @Override
    public void expenseRegistration(Transaction transaction, String category, int id, int userId) {
        Expense expense = new Expense(transaction);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));
        transaction.setUserId(user);
        CategoryType categoryType = CategoryType.fromString(category);
        switch (categoryType) {
            case OPTIONAL -> {
                OptionalCategory optionalCategory = optionalCategoryRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Optional category not found", id));
                expenseRepository.save(expense);
                optionalCategory.addExpense(expense);
                expenseRepository.save(expense);
                optionalCategoryRepository.updateOptionalCatCurrAmount(id, transaction.getAmount());
            }
            case MANDATORY -> {
                PaymentReminder paymentReminder = paymReminderRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Mandatory category not found", id));
                if (checkMandatoryAmount(expense.getTransaction().getAmount(),
                        paymentReminder.getMandatoryCategory())) {
                    expenseRepository.save(expense);
                    paymentReminder.setExpense(expense);
                    paymReminderRepository.save(paymentReminder);
                    deleteRemindersAfterExpense4Users(paymentReminder);
                }
            }
            case CUSTOM -> {
                CustomCategory customCategory = customCategoryRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Custom category not found", id));
                expenseRepository.save(expense);
                customCategory.addExpense(expense);
                expenseRepository.save(expense);
                customCategoryRepository.updateCustomCatCurrAmount(customCategory.getCustomCategoryId(),
                        transaction.getAmount());
            }
            case FINANCE_GOALS -> {
                FinanceGoals financeGoals = financeGoalsRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Finance goal", id));
                expenseRepository.save(expense);
                financeGoals.addExpense(expense);
                financeGoalsRepository.updateCurrAmount(id, transaction.getAmount());
            }
        }
    }

    private boolean checkMandatoryAmount(float amount, MandatoryCategory mandatoryCategory) {
        if (amount != mandatoryCategory.getAmount())
            throw new IllegalArgumentException("Payment amount is not equal to expected");
        return true;
    }

    private void deleteRemindersAfterExpense4Users(PaymentReminder paymentReminder) {
        for (User user : paymentReminder.getUserSet()) {
            user.getPaymentReminders().remove(paymentReminder);
            userRepository.save(user);
        }
        paymentReminder.getUserSet().clear();
    }

}
