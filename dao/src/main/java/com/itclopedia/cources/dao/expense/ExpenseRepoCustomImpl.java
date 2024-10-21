package com.itclopedia.cources.dao.expense;

import com.itclopedia.cources.model.Expense;
import com.itclopedia.cources.model.OptionalCategory;
import com.itclopedia.cources.model.Transaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class ExpenseRepoCustomImpl implements ExpenseRepoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Float getTotalAmountForOptionalCateInLastMonth(int categoryId) {
        LocalDate now = LocalDate.now();
        LocalDate oneMonthAgo = now.minusMonths(1);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Float> cq = cb.createQuery(Float.class);
        Root<Expense> expense = cq.from(Expense.class);
        Join<Expense, Transaction> transactionJoin = expense.join("transaction");
        Join<Expense, OptionalCategory> categoryJoin = expense.join("optionalCategory");
        Predicate categoryPredicate = cb.equal(categoryJoin.get("optionalCategoryId"), categoryId);
        Predicate datePredicate = cb.between(transactionJoin.get("dataTime"), oneMonthAgo, now);
        cq.select(cb.sum(transactionJoin.get("amount")))
                .where(cb.and(categoryPredicate, datePredicate));
        TypedQuery<Float> query = entityManager.createQuery(cq);
        Float totalAmount = query.getSingleResult();
        return totalAmount != null ? totalAmount : 0;
    }

}
