package com.itclopedia.cources.services.payment.reminders;

import com.itclopedia.cources.dao.mandatory.category.MandatoryCategoryRepository;
import com.itclopedia.cources.dao.payment.reminder.PaymReminderRepository;
import com.itclopedia.cources.dao.user.UserRepository;
import com.itclopedia.cources.exceptions.EntityNotFoundException;
import com.itclopedia.cources.model.MandatoryCategory;
import com.itclopedia.cources.model.PaymentReminder;
import com.itclopedia.cources.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class PaymentRemindServiceImpl implements PaymentRemindService {

    private final PaymReminderRepository paymReminderRepository;
    private final UserRepository userRepository;
    private final MandatoryCategoryRepository mandatoryCategoryRepository;

    @Autowired
    public PaymentRemindServiceImpl(PaymReminderRepository paymReminderRepository,
                                    UserRepository userRepository,
                                    MandatoryCategoryRepository mandatoryCategoryRepository) {
        this.paymReminderRepository = paymReminderRepository;
        this.userRepository = userRepository;
        this.mandatoryCategoryRepository = mandatoryCategoryRepository;
    }

    @Override
    public List<PaymentReminder> getCurrentRemindersForUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));
        return paymReminderRepository.getCurrentRemindersForUser(user);
    }

    @Override
    public List<PaymentReminder> getAllCurrent() {
        return paymReminderRepository.getAllCurrent();
    }

    @Override
    public void createPaymentReminder(int userId, int mandatoryCategoryId, LocalDate deadline) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));
        MandatoryCategory mandatoryCategory = mandatoryCategoryRepository.findById(mandatoryCategoryId)
                .orElseThrow(() -> new EntityNotFoundException("Mandatory category", mandatoryCategoryId));
        PaymentReminder paymentReminder = new PaymentReminder(deadline, mandatoryCategory);
        paymentReminder.addUser(user);
        paymReminderRepository.save(paymentReminder);
    }

    @Override
    public void joinPaymentReminder(int userId, int paymentReminderId) {
        PaymentReminder paymentReminder = paymReminderRepository.findById(paymentReminderId)
                .orElseThrow(() -> new EntityNotFoundException("Payment Reminder not fount",
                        paymentReminderId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));
        paymentReminder.addUser(user);
        paymReminderRepository.save(paymentReminder);
    }

    @Override
    public void updateMandatoryCatId(int mandatoryCategoryId, int paymentReminderId) {
        MandatoryCategory mandatoryCategory = mandatoryCategoryRepository.findById(mandatoryCategoryId)
                .orElseThrow(() -> new EntityNotFoundException("Mandatory category", mandatoryCategoryId));
        paymReminderRepository.updateMandatoryCatId(mandatoryCategory, paymentReminderId);
    }

}
