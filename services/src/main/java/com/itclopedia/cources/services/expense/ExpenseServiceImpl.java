package com.itclopedia.cources.services.expense;

import com.itclopedia.cources.dao.expense.ExpenseRepoCustomImpl;
import com.itclopedia.cources.dao.optional.category.OptionalCategoryRepository;
import com.itclopedia.cources.model.OptionalCategory;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.ArrayList;

@Service
@Transactional
@NoArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseRepoCustomImpl expenseRepository;
    private static final Logger log = LoggerFactory.getLogger(ExpenseServiceImpl.class);
    private OptionalCategoryRepository optionalCategoryRepository;

    @Autowired
    public ExpenseServiceImpl(OptionalCategoryRepository optionalCategoryRepository,
                              ExpenseRepoCustomImpl expenseRepository) {
        this.optionalCategoryRepository = optionalCategoryRepository;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<String> checkFamilyStatusForOptionalCatLastMonth() {
        List<String> result = new ArrayList<>();
        result.add("Family spending statistics for the last month for the optional category");
        for (OptionalCategory category : optionalCategoryRepository.findAll()) {
            result.add(category.getName());
            result.add(" " + getTotalAmountForOptionalCateInLastMonth(category.getOptionalCategoryId()));
        }
        return result;
    }

    public Float getTotalAmountForOptionalCateInLastMonth(int categoryId) {
        log.info("starting getTotalAmountForCategoryInLastMonthOptional");
        return expenseRepository.getTotalAmountForOptionalCateInLastMonth(categoryId);
    }

}
