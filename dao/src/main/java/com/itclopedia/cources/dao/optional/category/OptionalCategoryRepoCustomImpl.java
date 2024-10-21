package com.itclopedia.cources.dao.optional.category;

import com.itclopedia.cources.model.Expense;
import com.itclopedia.cources.model.OptionalCategory;
import com.itclopedia.cources.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class OptionalCategoryRepoCustomImpl implements OptionalCategoryRepoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public float check(String name) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Float> criteriaQuery = builder.createQuery(Float.class);
        Root<OptionalCategory> root = criteriaQuery.from(OptionalCategory.class);
        criteriaQuery.select(builder.sum(root.get(name)));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public float checkForUser(User user, OptionalCategory optionalCategory) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Float> query = criteriaBuilder.createQuery(Float.class);
        Root<Expense> expense = query.from(Expense.class);
        Join<Expense, Transaction> transaction = expense.join("transaction", JoinType.INNER);
        Subquery<Float> subquery = query.subquery(Float.class);

        Root<Expense> subExpense = subquery.from(Expense.class);
        Join<Expense, OptionalCategory> subCategory = subExpense.join("optionalCategories");
        subquery.select(subExpense.get("expenseId"))
                .where(criteriaBuilder.equal(subCategory.get("optionalCategoryId"),
                        optionalCategory.getOptionalCategoryId()));

        Predicate userPredicate = criteriaBuilder.equal(transaction.get("userId"), user);
        Predicate categoryPredicate = criteriaBuilder.in(expense.get("expenseId")).value(subquery);
        query.select(criteriaBuilder.sum(transaction.get("amount")))
                .where(criteriaBuilder.and(userPredicate, categoryPredicate));
        if (entityManager.createQuery(query).getSingleResult() == null)
            return 0f;
        return entityManager.createQuery(query).getSingleResult();
    }

}
