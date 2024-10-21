package com.itclopedia.cources.services.optional.category;

import com.itclopedia.cources.model.OptionalCategory;
import com.itclopedia.cources.model.Transaction;
import com.itclopedia.cources.model.User;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OptionalCatService {

    List<OptionalCategory> getAll();

    OptionalCategory getByName(String name);

    List<Transaction> getTransactionsByDate(int optionalCategoryId, LocalDate from);

    void insertOptionalCat(OptionalCategory optionalCategory);

    void updateOptionalCatBudget(int id, float budget);

    void deleteOptionalCat(int id);

    float checkForUser(User user, OptionalCategory optionalCategory);

    void updateOptionalCatNameById(int id, String name);
}
