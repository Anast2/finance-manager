package com.itclopedia.cources.services.mandatory.category;

import com.itclopedia.cources.dao.user.UserRepository;
import com.itclopedia.cources.exceptions.EntityNotFoundException;
import com.itclopedia.cources.model.MandatoryCategory;
import com.itclopedia.cources.model.PaymentReminder;
import com.itclopedia.cources.model.User;
import com.itclopedia.cources.dao.mandatory.category.MandatoryCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.List;

@Service
@Transactional
public class MandatoryCatServiceImpl implements MandatoryCatService {

    private final MandatoryCategoryRepository mandatoryCategoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public MandatoryCatServiceImpl(UserRepository userRepository,
                                   MandatoryCategoryRepository mandatoryCategoryRepository) {
        this.userRepository = userRepository;
        this.mandatoryCategoryRepository = mandatoryCategoryRepository;
    }

    @Override
    public void insertMandatoryCat(User user, String name, float amount, LocalDate deadline) {
        MandatoryCategory mandatoryCategory = new MandatoryCategory(name, amount);
        PaymentReminder paymentReminder = new PaymentReminder(deadline, mandatoryCategory);
        mandatoryCategory.setPaymentReminder(paymentReminder);
        paymentReminder.addUser(user);
        mandatoryCategoryRepository.save(mandatoryCategory);
        userRepository.save(user);
    }

    @Override
    public List<MandatoryCategory> getAllMandatoryCats() {
        return mandatoryCategoryRepository.findAll();
    }

    @Override
    public List<MandatoryCategory> printUserMandatoryCats(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found", userId));
        return user.getPaymentReminders().stream()
                .filter(paymentReminder -> paymentReminder.getExpense() == null)
                .map(PaymentReminder::getMandatoryCategory)
                .collect(Collectors.toList());
    }

    @Override
    public void updateMandatoryCatNameById(int id, String name) {
        mandatoryCategoryRepository.updateMandatoryCatNameById(id, name);
    }

    @Override
    public void updateMandatoryCatAmount(int id, float amount) {
        mandatoryCategoryRepository.updateMandatoryCatAmount(id, amount);
    }

}

