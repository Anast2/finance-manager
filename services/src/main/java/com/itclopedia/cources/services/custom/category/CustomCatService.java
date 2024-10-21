package com.itclopedia.cources.services.custom.category;

import com.itclopedia.cources.model.CustomCategory;
import com.itclopedia.cources.model.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface CustomCatService {

    List<CustomCategory> getAll();

    List<Transaction> getTransactionsByDate(int customCategoryId, LocalDate from);

    Set<CustomCategory> getAllForUser(int userId);

    void insertCustomCat(int userId, CustomCategory customCategory);

    void joinCustomCategory(int userId, int customCategoryId);

    void updateAmount(float amount, int id);

    void updateCustomCatNameById(int id, String name);

    void deleteCustomCategory(int id);

    List<String> checkForUser(int userId);

}
