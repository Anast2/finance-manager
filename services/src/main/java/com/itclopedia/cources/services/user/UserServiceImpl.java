package com.itclopedia.cources.services.user;

import com.itclopedia.cources.dao.custom.category.CustomCategoryRepository;
import com.itclopedia.cources.dao.expense.ExpenseRepository;
import com.itclopedia.cources.dao.finance.goals.FinanceGoalsRepository;
import com.itclopedia.cources.dao.payment.reminder.PaymReminderRepository;
import com.itclopedia.cources.dao.role.RoleRepository;
import com.itclopedia.cources.dao.user.UserRepository;
import com.itclopedia.cources.exceptions.EntityNotFoundException;
import com.itclopedia.cources.model.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CustomCategoryRepository customCategoryRepository;
    private final ExpenseRepository expenseRepository;
    private final FinanceGoalsRepository financeGoalsRepository;
    private final PaymReminderRepository paymReminderRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           CustomCategoryRepository customCategoryRepository,
                           ExpenseRepository expenseRepository,
                           FinanceGoalsRepository financeGoalsRepository,
                           PaymReminderRepository paymReminderRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.customCategoryRepository = customCategoryRepository;
        this.expenseRepository = expenseRepository;
        this.financeGoalsRepository = financeGoalsRepository;
        this.paymReminderRepository = paymReminderRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found", id));
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("User not found", name));
    }

    @Transactional
    public void updateUserEmail(int id, String email) {
        if (!userRepository.existsById(id))
            throw new EntityNotFoundException("User not found", id);
        userRepository.updateUserEmail(id, email);
    }

    @Transactional
    public void updateUserPassword(int id, String password) {
        if (!userRepository.existsById(id))
            throw new EntityNotFoundException("User not found", id);
        userRepository.updateUserPassword(id, password);
    }

    @Transactional
    public void updateUserName(int id, String name) {
        if (!userRepository.existsById(id))
            throw new EntityNotFoundException("User not found", id);
        userRepository.updateUserName(id, name);
    }

    @Override
    public void insertNewUser(String name, String email, String password) {
        User user = new User();
        String encodedPassword = passwordEncoder.encode(password);
        user.setName(name);
        user.setPassword(encodedPassword);
        user.setEmail(email);
        Role role = roleRepository.findByName("ROLE_USER");
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));
        Set<CustomCategory> customCategorySet = user.getCustomCategorySet();
        if (!customCategorySet.isEmpty()) {
            for (CustomCategory customCategory : customCategorySet)
                customCategoryRepository.deleteById(customCategory.getCustomCategoryId());
        }
        List<Expense> expenses = expenseRepository.findAll();
        if (!expenses.isEmpty()) {
            for (Expense expense : expenses) {
                if (expense.getTransaction().getUserId().getId() == user.getId()) {
                    expenseRepository.delete(expense);
                }
            }
        }
        userRepository.delete(user);
    }

    @Override
    public void deleteFinanceGoal(int userId, int financeGoalId) {
        FinanceGoals financeGoal = financeGoalsRepository.findById(financeGoalId)
                .orElseThrow(() -> new EntityNotFoundException("Finance goal", financeGoalId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));
        if (financeGoal.getUserSet().size() > 1 &&
                user.getFinanceGoalsSet().contains(financeGoal) &&
                financeGoal.getUserSet().contains(user)) {
            user.getFinanceGoalsSet().remove(financeGoal);
            financeGoal.getUserSet().remove(user);
            financeGoalsRepository.save(financeGoal);
        } else
            financeGoalsRepository.delete(financeGoal);
    }

    @Override
    public void deleteCustomCategory(int userId, int customCategoryId) {
        CustomCategory customCategory = customCategoryRepository.findById(customCategoryId)
                .orElseThrow(() -> new EntityNotFoundException("Custom category", customCategoryId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));
        if (customCategory.getUserSet().size() > 1 &&
                user.getCustomCategorySet().contains(customCategory) &&
                customCategory.getUserSet().contains(user)) {
            user.getCustomCategorySet().remove(customCategory);
            customCategory.getUserSet().remove(user);
            customCategoryRepository.save(customCategory);
        } else
            customCategoryRepository.delete(customCategory);
    }

    @Override
    public void addPaymentReminder(int userId, int paymentReminderId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));
        PaymentReminder paymentReminder = paymReminderRepository.findById(paymentReminderId)
                .orElseThrow(() -> new EntityNotFoundException("Payment reminder", paymentReminderId));
        user.getPaymentReminders().add(paymentReminder);
        paymentReminder.getUserSet().add(user);
        paymReminderRepository.save(paymentReminder);
    }

    @Override
    public void addRole(int userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        role.getUsers().add(user);
        userRepository.save(user);
    }

}
