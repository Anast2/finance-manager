package com.itclopedia.cources.services.finance.goals;

import com.itclopedia.cources.dao.user.UserRepository;
import com.itclopedia.cources.exceptions.EntityAlreadyExistException;
import com.itclopedia.cources.exceptions.EntityNotFoundException;
import com.itclopedia.cources.model.FinanceGoals;
import com.itclopedia.cources.model.User;
import com.itclopedia.cources.dao.finance.goals.FinanceGoalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class FinanceGoalsServiceImpl implements FinanceGoalsService {

    private final FinanceGoalsRepository financeGoalsRepository;
    private final UserRepository userRepository;

    @Autowired
    public FinanceGoalsServiceImpl(FinanceGoalsRepository financeGoalsRepository,
                                   UserRepository userRepository) {
        this.financeGoalsRepository = financeGoalsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void updateFinanceGoalBudget(int id, float amount) {
        if (!financeGoalsRepository.existsById(id))
            throw new EntityNotFoundException("Finance goal", id);
        if (amount < 0)
            throw new IllegalArgumentException("Finance goal amount cannot be negative");
        financeGoalsRepository.updateAmount(id, amount);
    }

    @Override
    public void deleteFinanceGoal(int id) {
        if (!financeGoalsRepository.existsById(id))
            throw new EntityNotFoundException("Finance goal", id);
        financeGoalsRepository.deleteById(id);
    }

    @Override
    public void insertFinanceGoal(int userId, FinanceGoals financeGoal) {
        if (financeGoal == null)
            throw new IllegalArgumentException("FinanceGoal cannot be null");
        if (financeGoalsRepository.existsById(financeGoal.getFinanceGoalId()))
            throw new EntityAlreadyExistException("Finance goal", financeGoal.getFinanceGoalId());
        if (financeGoal.getDescription() == null)
            throw new IllegalArgumentException("Finance goal description cannot be null");
        if (financeGoal.getStart().isBefore(LocalDate.now()) ||
                financeGoal.getEnd().isBefore(LocalDate.now()) ||
                financeGoal.getStart().isAfter(financeGoal.getEnd()))
            throw new IllegalArgumentException("FinanceGoal date is incorrect ");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));
        financeGoal.addUser(user);
        financeGoalsRepository.save(financeGoal);
    }

    @Override
    public void joinFinanceGoal(int userId, int financeGoalId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));
        FinanceGoals financeGoals = financeGoalsRepository.findById(financeGoalId)
                .orElseThrow(() -> new EntityNotFoundException("Finance goal", financeGoalId));
        financeGoals.addUser(user);
        financeGoalsRepository.save(financeGoals);
    }

    @Override
    public Set<FinanceGoals> getAllForUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));
        return user.getFinanceGoalsSet();
    }

    @Override
    public List<String> getStatisticsForUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));
        return user.getFinanceGoalsSet().stream()
                .map(financeGoals -> financeGoals.getDescription() + ", "
                        + percents(financeGoals.getCurrAmount(), financeGoals.getAmount()) + "%")
                .collect(Collectors.toList());
    }

    private float percents(float curr, float all) {
        if (all == 0)
            throw new IllegalArgumentException("Finance goal amount cannot be zero");
        return (curr / all) * 100;
    }
}

