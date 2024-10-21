package com.itclopedia.cources.dao.payment.reminder;

import com.itclopedia.cources.model.PaymentReminder;
import com.itclopedia.cources.model.User;

import java.util.List;

public interface PaymReminderRepoCustom {

    List<PaymentReminder> getCurrentRemindersForUser(User user);

    List<PaymentReminder> getAllCurrent();

}
