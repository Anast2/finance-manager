package com.itclopedia.cources.services.user;

import com.itclopedia.cources.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(int id);

    User getUserByName(String name);

    void updateUserEmail(int id, String email);

    void updateUserPassword(int id, String password);

    void updateUserName(int id, String name);

    void insertNewUser(String name, String email, String password);

    void deleteUser(int id);

    void deleteFinanceGoal(int userId, int financeGoalId);

    void deleteCustomCategory(int userId, int customCategoryId);

    void addPaymentReminder(int userId, int paymentReminderId);

    void addRole(int userId, String roleName);

}