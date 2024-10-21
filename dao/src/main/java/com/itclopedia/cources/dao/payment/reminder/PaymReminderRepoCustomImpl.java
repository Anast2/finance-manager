package com.itclopedia.cources.dao.payment.reminder;

import com.itclopedia.cources.model.PaymentReminder;
import com.itclopedia.cources.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class PaymReminderRepoCustomImpl implements PaymReminderRepoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PaymentReminder> getCurrentRemindersForUser(User user) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PaymentReminder> criteriaQuery = criteriaBuilder.createQuery(PaymentReminder.class);
        Root<PaymentReminder> root = criteriaQuery.from(PaymentReminder.class);
        Join<PaymentReminder, User> userJoin = root.join("userSet", JoinType.INNER);
        LocalDate now = LocalDate.now();
        Predicate datePredicate = criteriaBuilder.greaterThan(root.get("date"), now);
        Predicate expenseNullPredicate = criteriaBuilder.isNull(root.get("expense"));
        Predicate userPredicate = criteriaBuilder.equal(userJoin.get("id"), user.getId());
        criteriaQuery.where(criteriaBuilder.and(datePredicate, expenseNullPredicate, userPredicate));
        TypedQuery<PaymentReminder> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<PaymentReminder> getAllCurrent() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PaymentReminder> criteriaQuery = criteriaBuilder.createQuery(PaymentReminder.class);
        Root<PaymentReminder> root = criteriaQuery.from(PaymentReminder.class);
        LocalDate now = LocalDate.now();
        Predicate datePredicate = criteriaBuilder.greaterThan(root.get("date"), now);
        Predicate expenseNullPredicate = criteriaBuilder.isNull(root.get("expense"));
        criteriaQuery.where(criteriaBuilder.and(datePredicate, expenseNullPredicate));
        TypedQuery<PaymentReminder> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
