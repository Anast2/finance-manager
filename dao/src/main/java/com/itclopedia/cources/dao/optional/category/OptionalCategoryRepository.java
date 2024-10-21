package com.itclopedia.cources.dao.optional.category;

import com.itclopedia.cources.model.OptionalCategory;
import com.itclopedia.cources.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@Repository
public interface OptionalCategoryRepository extends JpaRepository<OptionalCategory, Integer>,
        OptionalCategoryRepoCustom {

    Optional<OptionalCategory> findByName(String name);

    @Modifying
    @Query("UPDATE OptionalCategory op SET op.amount= :amount WHERE op.optionalCategoryId = :id")
    void updateOptionalCatAmount(@Param("id") int id, @Param("amount") float amount);

    @Modifying
    @Query("UPDATE OptionalCategory op SET op.currAmount = op.currAmount + :currAmount WHERE" +
            " op.optionalCategoryId = :id")
    void updateOptionalCatCurrAmount(@Param("id") int id, @Param("currAmount") float currAmount);

    @Modifying
    @Query("UPDATE OptionalCategory op SET op.name = :name WHERE op.optionalCategoryId = :id")
    void updateOptionalCatNameById(@Param("id") int id, @Param("name") String name);

    @Query("SELECT t FROM Transaction t " +
            "JOIN Expense e ON t.transactionId = e.transaction.transactionId " +
            "JOIN e.optionalCategory c " +
            "WHERE c.optionalCategoryId = :optionalCategoryId AND FUNCTION('DATE', t.dataTime) = :from")
    List<Transaction> getTransactionsByDate(@Param("optionalCategoryId") int optionalCategoryId,
                                            @Param("from") LocalDate from);

}
