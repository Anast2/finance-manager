package com.itclopedia.services.custom.category;

import com.itclopedia.cources.dao.custom.category.CustomCategoryRepository;
import com.itclopedia.cources.dao.user.UserRepository;
import com.itclopedia.cources.model.CustomCategory;
import com.itclopedia.cources.model.User;
import com.itclopedia.cources.model.Transaction;
import com.itclopedia.cources.services.custom.category.CustomCatServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomCatServiceImplTest {

    @InjectMocks
    private CustomCatServiceImpl customCatService;

    @Mock
    private CustomCategoryRepository customCategoryRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void getAll() {
        Mockito.when(customCategoryRepository.findAll()).thenReturn(Collections.emptyList());
        List<CustomCategory> categories = customCatService.getAll();
        assertTrue(categories.isEmpty());
        verify(customCategoryRepository, times(1)).findAll();
    }

    @Test
    void insertCustomCat() {
        int userId = 1;
        CustomCategory customCategory = new CustomCategory();
        customCategory.setCustomCategoryId(2);
        User user = new User();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(customCategoryRepository.existsById(anyInt())).thenReturn(false);
        customCatService.insertCustomCat(userId, customCategory);
        verify(customCategoryRepository, times(1)).save(customCategory);
    }

    @Test
    void joinCustomCategory() {
        int userId = 1;
        int customCategoryId = 1;
        User user = new User();
        CustomCategory customCategory = new CustomCategory();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(customCategoryRepository.findById(customCategoryId))
                .thenReturn(Optional.of(customCategory));
        customCatService.joinCustomCategory(userId, customCategoryId);
        verify(customCategoryRepository, times(1)).save(customCategory);
    }

    @Test
    void updateAmount() {
        int categoryId = 1;
        float amount = 100f;
        Mockito.when(customCategoryRepository.existsById(categoryId)).thenReturn(true);
        customCatService.updateAmount(amount, categoryId);
        verify(customCategoryRepository, times(1))
                .updateCustomCatAmount(categoryId, amount);
    }

    @Test
    void deleteCustomCategory() {
        int categoryId = 1;
        CustomCategory customCategory = new CustomCategory();
        Mockito.when(customCategoryRepository.findById(categoryId))
                .thenReturn(Optional.of(customCategory));
        customCatService.deleteCustomCategory(categoryId);
        verify(customCategoryRepository, times(1)).delete(customCategory);
    }

    @Test
    void getTransactionsByDate() {
        int customCategoryId = 1;
        LocalDate fromDate = LocalDate.now();
        Mockito.when(customCategoryRepository.getTransactionsByDate(customCategoryId, fromDate))
                .thenReturn(Collections.emptyList());
        List<Transaction> transactions = customCatService.getTransactionsByDate(customCategoryId, fromDate);
        assertTrue(transactions.isEmpty());
        verify(customCategoryRepository, times(1))
                .getTransactionsByDate(customCategoryId, fromDate);
    }

    @Test
    void checkForUser() {
        int userId = 1;
        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        Mockito.when(customCategoryRepository.checkForUser(userId)).thenReturn(Collections.emptyList());
        List<String> categories = customCatService.checkForUser(userId);
        assertTrue(categories.isEmpty());
        verify(customCategoryRepository, times(1)).checkForUser(userId);
    }

    @Test
    void getAllForUser() {
        int userId = 1;
        User user = new User();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Set<CustomCategory> categories = customCatService.getAllForUser(userId);
        assertNotNull(categories);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void updateCustomCatNameById() {
        int categoryId = 1;
        String newName = "New Category Name";
        customCatService.updateCustomCatNameById(categoryId, newName);
        verify(customCategoryRepository, times(1))
                .updateCustomCatNameById(categoryId, newName);
    }

}
