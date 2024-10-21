package com.itclopedia.services.payment.reminder;

import com.itclopedia.cources.dao.mandatory.category.MandatoryCategoryRepository;
import com.itclopedia.cources.dao.payment.reminder.PaymReminderRepository;
import com.itclopedia.cources.dao.user.UserRepository;
import com.itclopedia.cources.model.MandatoryCategory;
import com.itclopedia.cources.model.PaymentReminder;
import com.itclopedia.cources.model.User;
import com.itclopedia.cources.services.payment.reminders.PaymentRemindServiceImpl;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentRemindServiceImplTest {

    @Mock
    private PaymReminderRepository paymReminderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MandatoryCategoryRepository mandatoryCategoryRepository;

    @InjectMocks
    private PaymentRemindServiceImpl paymentRemindService;

    private User testUser;
    private MandatoryCategory testCategory;
    private PaymentReminder testReminder;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testCategory = new MandatoryCategory("Test Category", 100);
        testReminder = new PaymentReminder(LocalDate.now().plusDays(30), testCategory);
        testReminder.addUser(testUser);
    }

    @Test
    void getCurrentRemindersForUser() {
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        Mockito.when(paymReminderRepository.getCurrentRemindersForUser(testUser))
                .thenReturn(Collections.singletonList(testReminder));
        List<PaymentReminder> reminders = paymentRemindService.getCurrentRemindersForUser(1);
        assertEquals(1, reminders.size());
        assertEquals(testReminder, reminders.get(0));
        verify(userRepository, times(1)).findById(1);
        verify(paymReminderRepository, times(1)).getCurrentRemindersForUser(testUser);
    }

    @Test
    void getAllCurrent() {
        Mockito.when(paymReminderRepository.getAllCurrent())
                .thenReturn(Collections.singletonList(testReminder));
        List<PaymentReminder> reminders = paymentRemindService.getAllCurrent();
        assertEquals(1, reminders.size());
        assertEquals(testReminder, reminders.get(0));
        verify(paymReminderRepository, times(1)).getAllCurrent();
    }

    @Test
    void createPaymentReminder() {
        int userId = 1;
        int mandatoryCategoryId = 1;
        LocalDate deadline = LocalDate.now().plusDays(30);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        Mockito.when(mandatoryCategoryRepository.findById(mandatoryCategoryId))
                .thenReturn(Optional.of(testCategory));
        paymentRemindService.createPaymentReminder(userId, mandatoryCategoryId, deadline);
        verify(userRepository, times(1)).findById(userId);
        verify(mandatoryCategoryRepository, times(1)).findById(mandatoryCategoryId);
        verify(paymReminderRepository, times(1)).save(any(PaymentReminder.class));
    }

    @Test
    void joinPaymentReminder() {
        int userId = 1;
        int paymentReminderId = 1;
        Mockito.when(paymReminderRepository.findById(paymentReminderId)).thenReturn(Optional.of(testReminder));
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        paymentRemindService.joinPaymentReminder(userId, paymentReminderId);
        verify(paymReminderRepository, times(1)).findById(paymentReminderId);
        verify(userRepository, times(1)).findById(userId);
        verify(paymReminderRepository, times(1)).save(testReminder);
    }

    @Test
    void updateMandatoryCatId() {
        int mandatoryCategoryId = 1;
        int paymentReminderId = 1;
        Mockito.when(mandatoryCategoryRepository.findById(mandatoryCategoryId))
                .thenReturn(Optional.of(testCategory));
        paymentRemindService.updateMandatoryCatId(mandatoryCategoryId, paymentReminderId);
        verify(mandatoryCategoryRepository, times(1)).findById(mandatoryCategoryId);
        verify(paymReminderRepository, times(1))
                .updateMandatoryCatId(testCategory, paymentReminderId);
    }

}