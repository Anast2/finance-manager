package com.itclopedia.cources.dao.custom.category;

import com.itclopedia.cources.model.CustomCategory;
import com.itclopedia.cources.model.Expense;
import com.itclopedia.cources.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomCategoryRepoCustomImpl implements CustomCategoryRepoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> checkForUser(int userId) {
        User user = entityManager.find(User.class, userId);
        if (user == null)
            throw new IllegalArgumentException("User or custom category does not exist");
        List<String> result2 = new ArrayList<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Float> query = criteriaBuilder.createQuery(Float.class);
        Root<Expense> expenseRoot = query.from(Expense.class);
        Join<Expense, Transaction> transactionJoin = expenseRoot.join("transaction", JoinType.INNER);
        Float result;
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfLastMonth = now.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfLastMonth = now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        Predicate datePredicate = criteriaBuilder.between(transactionJoin.get("dataTime"),
                java.sql.Date.valueOf(firstDayOfLastMonth),
                java.sql.Date.valueOf(lastDayOfLastMonth));
        Predicate userPredicate = criteriaBuilder.equal(transactionJoin.get("userId"), user);
        for (CustomCategory customCategory : user.getCustomCategorySet()) {
            Predicate categoryPredicate = criteriaBuilder.equal(expenseRoot.get("customCategory"), customCategory);
            query.select(criteriaBuilder.sum(transactionJoin.get("amount")))
                    .where(criteriaBuilder.and(userPredicate, categoryPredicate, datePredicate));
            result = entityManager.createQuery(query).getSingleResult();
            result2.add(customCategory.getName() + ", " + result);
        }
        return result2;
    }
}
