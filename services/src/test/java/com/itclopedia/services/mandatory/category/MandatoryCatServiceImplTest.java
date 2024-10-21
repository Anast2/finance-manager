package com.itclopedia.services.mandatory.category;

import com.itclopedia.cources.dao.mandatory.category.MandatoryCategoryRepository;
import com.itclopedia.cources.dao.user.UserRepository;
import com.itclopedia.cources.model.MandatoryCategory;
import com.itclopedia.cources.model.PaymentReminder;
import com.itclopedia.cources.model.User;
import com.itclopedia.cources.services.mandatory.category.MandatoryCatServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MandatoryCatServiceImplTest {

    @Mock
    private MandatoryCategoryRepository mandatoryCategoryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MandatoryCatServiceImpl mandatoryCatService;

    private User testUser;
    private MandatoryCategory testCategory;
    private PaymentReminder testReminder;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testCategory = new MandatoryCategory("Test Category", 100);
        testReminder = new PaymentReminder(LocalDate.now().plusDays(30), testCategory);
        testCategory.setPaymentReminder(testReminder);
    }

    @Test
    void insertMandatoryCat() {
        Mockito.when(userRepository.save(testUser)).thenReturn(testUser);
        Mockito.when(mandatoryCategoryRepository.save(testCategory)).thenReturn(testCategory);
        mandatoryCatService.insertMandatoryCat(testUser, "Test Category", 100,
                LocalDate.now().plusDays(30));
        verify(mandatoryCategoryRepository, times(1))
                .save(any(MandatoryCategory.class));
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void getAllMandatoryCats() {
        Mockito.when(mandatoryCategoryRepository.findAll())
                .thenReturn(Collections.singletonList(testCategory));
        List<MandatoryCategory> categories = mandatoryCatService.getAllMandatoryCats();
        assertFalse(categories.isEmpty());
        assertEquals(1, categories.size());
        assertEquals(testCategory, categories.get(0));
        verify(mandatoryCategoryRepository, times(1)).findAll();
    }

    @Test
    void printUserMandatoryCats() {
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        testUser.setPaymentReminders(Collections.singleton(testReminder));
        List<MandatoryCategory> result = mandatoryCatService.printUserMandatoryCats(1);
        assertEquals(1, result.size());
        assertEquals(testCategory, result.get(0));
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void updateMandatoryCatNameById() {
        int id = testCategory.getMandatoryCategoryId();
        String newName = "Updated Name";
        mandatoryCatService.updateMandatoryCatNameById(id, newName);
        verify(mandatoryCategoryRepository, times(1))
                .updateMandatoryCatNameById(id, newName);
    }

    @Test
    void updateMandatoryCatAmount() {
        int id = testCategory.getMandatoryCategoryId();
        float newAmount = 150.0f;
        mandatoryCatService.updateMandatoryCatAmount(id, newAmount);
        verify(mandatoryCategoryRepository, times(1))
                .updateMandatoryCatAmount(id, newAmount);
    }

}