package com.itclopedia.cources.dao.finance.goals;

import com.itclopedia.cources.model.FinanceGoals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FinanceGoalsRepository extends JpaRepository<FinanceGoals, Integer>, FinanceGoalRepoCustom {

    @Modifying
    @Query("UPDATE FinanceGoals fg SET fg.amount = :amount WHERE fg.financeGoalId = :id")
    void updateAmount(@Param("id")int id,@Param("amount") float amount);

    @Modifying
    @Query("UPDATE FinanceGoals fg SET fg.currAmount = fg.currAmount + :currAmount WHERE fg.financeGoalId = :id")
    void updateCurrAmount(@Param("id") int id, @Param("currAmount") float currAmount);

}

