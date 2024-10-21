package com.itclopedia.cources.services.transaction;

import com.itclopedia.cources.model.Transaction;

public interface TransactionService {

    void incomeRegistration(Transaction transaction);

    void expenseRegistration(Transaction transaction, String category, int id, int userId);

}

