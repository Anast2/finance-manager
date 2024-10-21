package com.itclopedia.cources.services.payment.reminders;

import com.itclopedia.cources.model.PaymentReminder;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRemindService {

    List<PaymentReminder> getCurrentRemindersForUser(int userId);

    List<PaymentReminder> getAllCurrent();

    void createPaymentReminder(int userId, int mandatoryCategoryId, LocalDate deadline);

    void joinPaymentReminder(int userId, int paymentReminderId);

    void updateMandatoryCatId(int mandatoryCategoryId, int paymentReminderId);

}
