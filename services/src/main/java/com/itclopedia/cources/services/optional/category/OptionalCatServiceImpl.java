package com.itclopedia.cources.services.optional.category;

import com.itclopedia.cources.dao.user.UserRepository;
import com.itclopedia.cources.exceptions.EntityAlreadyExistException;
import com.itclopedia.cources.exceptions.EntityNotFoundException;
import com.itclopedia.cources.model.OptionalCategory;
import com.itclopedia.cources.model.Transaction;
import com.itclopedia.cources.model.User;
import com.itclopedia.cources.dao.optional.category.OptionalCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class OptionalCatServiceImpl implements OptionalCatService {

    private final OptionalCategoryRepository optionalCategoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public OptionalCatServiceImpl(OptionalCategoryRepository optionalCategoryRepository,
                                  UserRepository userRepository) {
        this.optionalCategoryRepository = optionalCategoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<OptionalCategory> getAll() {
        return optionalCategoryRepository.findAll();
    }

    @Override
    public OptionalCategory getByName(String name) {
        return optionalCategoryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Optional category", name));
    }

    @Override
    public void insertOptionalCat(OptionalCategory optionalCategory) {
        if (optionalCategory == null)
            throw new IllegalArgumentException("OptionalCategory is null");
        if (optionalCategoryRepository.findByName(optionalCategory.getName()).isPresent())
            throw new EntityAlreadyExistException("Optional category", optionalCategory.getName());
        optionalCategoryRepository.save(optionalCategory);
    }

    @Override
    public void updateOptionalCatBudget(int id, float budget) {
        if (!optionalCategoryRepository.existsById(id))
            throw new EntityNotFoundException("Optional category", id);
        if (budget < 0)
            throw new IllegalArgumentException("Optional category budget cannot be negative");
        optionalCategoryRepository.updateOptionalCatAmount(id, budget);
    }

    @Override
    public void deleteOptionalCat(int id) {
        OptionalCategory optionalCategory = optionalCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Optional category", id));
        if (optionalCategory == null)
            throw new IllegalArgumentException("Optional category not found");
        optionalCategoryRepository.delete(optionalCategory);
    }

    @Override
    public float checkForUser(User user, OptionalCategory optionalCategory) {
        if (!optionalCategoryRepository.existsById(optionalCategory.getOptionalCategoryId()))
            throw new EntityNotFoundException("Optional category not found",
                    optionalCategory.getOptionalCategoryId());
        if (!userRepository.existsById(user.getId()))
            throw new EntityNotFoundException("User not found", user.getId());
        return optionalCategoryRepository.checkForUser(user, optionalCategory);
    }

    @Override
    public List<Transaction> getTransactionsByDate(int optionalCategoryId, LocalDate from) {
        if (!optionalCategoryRepository.existsById(optionalCategoryId))
            throw new EntityNotFoundException("Optional category", optionalCategoryId);
        if (from.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Optional category start date cannot be after current date");
        return optionalCategoryRepository.getTransactionsByDate(optionalCategoryId, from);
    }

    @Override
    public void updateOptionalCatNameById(int id, String name) {
        optionalCategoryRepository.updateOptionalCatNameById(id, name);
    }
}
