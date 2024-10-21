package com.itclopedia.cources.services.finance.goals;

import com.itclopedia.cources.model.FinanceGoals;

import java.util.List;
import java.util.Set;

public interface FinanceGoalsService {

    void deleteFinanceGoal(int id);

    void updateFinanceGoalBudget(int id, float amount);

    void insertFinanceGoal(int userId, FinanceGoals financeGoal);

    void joinFinanceGoal(int userId, int financeGoalId);

    Set<FinanceGoals> getAllForUser(int userId);

    List<String> getStatisticsForUser(int userId);

}
