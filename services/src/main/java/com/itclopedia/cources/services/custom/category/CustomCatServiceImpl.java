package com.itclopedia.cources.services.custom.category;

import com.itclopedia.cources.dao.custom.category.CustomCategoryRepository;
import com.itclopedia.cources.dao.user.UserRepository;
import com.itclopedia.cources.exceptions.EntityAlreadyExistException;
import com.itclopedia.cources.exceptions.EntityNotFoundException;
import com.itclopedia.cources.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CustomCatServiceImpl implements CustomCatService {

    private final CustomCategoryRepository customCategoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public CustomCatServiceImpl(CustomCategoryRepository customCategoryRepository,
                                UserRepository userRepository) {
        this.customCategoryRepository = customCategoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<CustomCategory> getAll() {
        return customCategoryRepository.findAll();
    }

    @Override
    public void insertCustomCat(int userId, CustomCategory customCategory) {
        if (customCategory == null)
            throw new IllegalArgumentException("CustomCategory is null");
        if (customCategory.getCustomCategoryId() != 0 &&
                customCategoryRepository.existsById(customCategory.getCustomCategoryId()))
            throw new EntityAlreadyExistException("Custom category", customCategory.getCustomCategoryId());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));
        customCategory.addUser(user);
        customCategoryRepository.save(customCategory);
    }

    @Override
    public void joinCustomCategory(int userId, int customCategoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));
        CustomCategory customCategory = customCategoryRepository.findById(customCategoryId)
                .orElseThrow(() -> new EntityNotFoundException("Custom category", userId));
        customCategory.addUser(user);
        customCategoryRepository.save(customCategory);
    }

    @Override
    public void updateAmount(float amount, int id) {
        if (amount < 0)
            throw new IllegalArgumentException("Amount can not be negative");
        if (!customCategoryRepository.existsById(id))
            throw new EntityNotFoundException("Custom category", id);
        customCategoryRepository.updateCustomCatAmount(id, amount);
    }

    @Override
    public void deleteCustomCategory(int id) {
        CustomCategory customCategory = customCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Custom category", id));
        customCategoryRepository.delete(customCategory);
    }

    @Override
    public List<Transaction> getTransactionsByDate(int customCategoryId, LocalDate from) {
        return customCategoryRepository.getTransactionsByDate(customCategoryId, from);
    }

    @Override
    public List<String> checkForUser(int userId) {
        if (!userRepository.existsById(userId))
            throw new EntityNotFoundException("User", userId);
        return customCategoryRepository.checkForUser(userId);
    }

    @Override
    public Set<CustomCategory> getAllForUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));
        return user.getCustomCategorySet();
    }

    @Override
    public void updateCustomCatNameById(int id, String name) {
        customCategoryRepository.updateCustomCatNameById(id, name);
    }

}
