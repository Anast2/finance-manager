package com.itclopedia.cources.services.mandatory.category;

import com.itclopedia.cources.model.MandatoryCategory;
import com.itclopedia.cources.model.User;

import java.time.LocalDate;
import java.util.List;

public interface MandatoryCatService {

    void insertMandatoryCat(User user, String name, float amount, LocalDate deadline);

    List<MandatoryCategory> getAllMandatoryCats();

    List<MandatoryCategory> printUserMandatoryCats(int userId);

    void updateMandatoryCatAmount(int id, float amount);

    void updateMandatoryCatNameById(int id, String name);

}
