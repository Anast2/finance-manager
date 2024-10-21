package com.itclopedia.cources.dao.mandatory.category;

import com.itclopedia.cources.model.MandatoryCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MandatoryCategoryRepository extends JpaRepository<MandatoryCategory, Integer> {

    @Modifying
    @Query("UPDATE MandatoryCategory mc SET mc.amount = :amount WHERE mc.mandatoryCategoryId = :id")
    void updateMandatoryCatAmount(@Param("id") int id, @Param("amount") float amount);

    @Modifying
    @Query("UPDATE MandatoryCategory mc SET mc.name = :name WHERE mc.mandatoryCategoryId = :id")
    void updateMandatoryCatNameById(@Param("id") int id, @Param("name") String name);

}
