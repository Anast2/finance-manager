package com.itclopedia.cources.dao.payment.reminder;

import com.itclopedia.cources.model.MandatoryCategory;
import com.itclopedia.cources.model.PaymentReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymReminderRepository extends JpaRepository<PaymentReminder, Integer>, PaymReminderRepoCustom {

    @Modifying
    @Query("UPDATE PaymentReminder pr SET pr.mandatoryCategory = :mandatoryCategory WHERE " +
            "pr.paymentReminderId = :id")
    void updateMandatoryCatId(@Param("mandatoryCategory") MandatoryCategory mandatoryCategory,
                              @Param("id") int id);
}

