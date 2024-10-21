package com.itclopedia.services.finance.goals;

import com.itclopedia.cources.dao.finance.goals.FinanceGoalsRepository;
import com.itclopedia.cources.dao.user.UserRepository;
import com.itclopedia.cources.exceptions.EntityAlreadyExistException;
import com.itclopedia.cources.model.*;
import com.itclopedia.cources.exceptions.EntityNotFoundException;
import com.itclopedia.cources.services.finance.goals.FinanceGoalsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Collections;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FinanceGoalsServiceImplTest {

    @Mock
    private FinanceGoalsRepository financeGoalsRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FinanceGoalsServiceImpl financeGoalsService;

    @Test
    void updateFinanceGoalBudget() {
        int goalId = 1;
        float amount = 100.0f;
        Mockito.when(financeGoalsRepository.existsById(goalId)).thenReturn(true);
        financeGoalsService.updateFinanceGoalBudget(goalId, amount);
        verify(financeGoalsRepository, times(1)).updateAmount(goalId, amount);
    }

    @Test
    void updateFinanceGoalBudget_EntityNotFound() {
        int goalId = 1;
        float amount = 100.0f;
        Mockito.when(financeGoalsRepository.existsById(goalId)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> financeGoalsService
                .updateFinanceGoalBudget(goalId, amount));
    }

    @Test
    void deleteFinanceGoal() {
        int goalId = 1;
        Mockito.when(financeGoalsRepository.existsById(goalId)).thenReturn(true);
        financeGoalsService.deleteFinanceGoal(goalId);
        verify(financeGoalsRepository, times(1)).deleteById(goalId);
    }

    @Test
    void deleteFinanceGoal_EntityNotFound() {
        int goalId = 1;
        Mockito.when(financeGoalsRepository.existsById(goalId)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> financeGoalsService.deleteFinanceGoal(goalId));
    }

    @Test
    void insertFinanceGoal() {
        int userId = 1;
        FinanceGoals financeGoal = new FinanceGoals();
        financeGoal.setFinanceGoalId(1);
        financeGoal.setDescription("New Goal");
        financeGoal.setStart(LocalDate.now().plusDays(1));
        financeGoal.setEnd(LocalDate.now().plusMonths(1));
        User user = new User();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(financeGoalsRepository.existsById(financeGoal.getFinanceGoalId())).thenReturn(false);
        financeGoalsService.insertFinanceGoal(userId, financeGoal);
        verify(financeGoalsRepository, times(1)).save(financeGoal);
    }

    @Test
    void insertFinanceGoal_AlreadyExists() {
        int userId = 1;
        FinanceGoals financeGoal = new FinanceGoals();
        financeGoal.setFinanceGoalId(1);
        Mockito.when(financeGoalsRepository.existsById(financeGoal.getFinanceGoalId())).thenReturn(true);
        assertThrows(EntityAlreadyExistException.class, () -> financeGoalsService
                .insertFinanceGoal(userId, financeGoal));
    }

    @Test
    void joinFinanceGoal() {
        int userId = 1;
        int goalId = 2;
        User user = new User();
        FinanceGoals financeGoals = new FinanceGoals();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(financeGoalsRepository.findById(goalId)).thenReturn(Optional.of(financeGoals));
        financeGoalsService.joinFinanceGoal(userId, goalId);
        verify(financeGoalsRepository, times(1)).save(financeGoals);
    }

    @Test
    void getAllForUser() {
        int userId = 1;
        User user = new User();
        Set<FinanceGoals> financeGoalsSet = Collections.singleton(new FinanceGoals());
        user.setFinanceGoalsSet(financeGoalsSet);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Set<FinanceGoals> result = financeGoalsService.getAllForUser(userId);
        assertEquals(financeGoalsSet, result);
    }

    @Test
    void getStatisticsForUser() {
        int userId = 1;
        User user = new User();
        FinanceGoals financeGoal = new FinanceGoals();
        financeGoal.setDescription("Goal 1");
        financeGoal.setAmount(100.0f);
        financeGoal.setCurrAmount(50.0f);
        user.setFinanceGoalsSet(Collections.singleton(financeGoal));
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        List<String> result = financeGoalsService.getStatisticsForUser(userId);
        assertEquals(1, result.size());
        assertTrue(result.get(0).contains("Goal 1, 50.0%"));
    }

}