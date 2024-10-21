package com.itclopedia.cources.dao.finance.goals;

import com.itclopedia.cources.model.FinanceGoals;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class FinanceGoalRepoCustomImpl implements FinanceGoalRepoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public float check(String name) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Float> criteriaQuery = builder.createQuery(Float.class);
        Root<FinanceGoals> root = criteriaQuery.from(FinanceGoals.class);
        try {
            criteriaQuery.select(builder.sum(root.get(name)));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

}
