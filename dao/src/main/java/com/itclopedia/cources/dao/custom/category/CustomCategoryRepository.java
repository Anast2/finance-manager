package com.itclopedia.cources.dao.custom.category;

import com.itclopedia.cources.model.CustomCategory;
import com.itclopedia.cources.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CustomCategoryRepository extends JpaRepository<CustomCategory, Integer>,
        CustomCategoryRepoCustom {

    @Modifying
    @Query("UPDATE CustomCategory cc SET cc.amount = :amount WHERE cc.customCategoryId = :id")
    void updateCustomCatAmount(@Param("id") int id, @Param("amount") float amount);

    @Modifying
    @Query("UPDATE CustomCategory cc SET cc.currAmount = cc.currAmount + :currAmount" +
            " WHERE cc.customCategoryId = :id")
    void updateCustomCatCurrAmount(@Param("id") int id, @Param("currAmount") float currAmount);

    @Modifying
    @Query("UPDATE CustomCategory cc SET cc.name = :name WHERE cc.customCategoryId = :id")
    void updateCustomCatNameById(@Param("id") int id, @Param("name") String name);

    @Query("SELECT t FROM Transaction t " +
            "JOIN Expense e ON t.transactionId = e.transaction.transactionId " +
            "JOIN e.customCategory c " +
            "WHERE c.customCategoryId = :customCategoryId AND t.dataTime >= :from")
    List<Transaction> getTransactionsByDate(@Param("customCategoryId") int optionalCategoryId,
                                            @Param("from") LocalDate from);

}
