package com.itclopedia.services.expense;

import com.itclopedia.cources.dao.expense.ExpenseRepoCustomImpl;
import com.itclopedia.cources.model.*;
import com.itclopedia.cources.dao.optional.category.OptionalCategoryRepository;
import com.itclopedia.cources.services.expense.ExpenseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {

    @Mock
    private OptionalCategoryRepository optionalCategoryRepository;

    @Mock
    private ExpenseRepoCustomImpl expenseRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @Test
    void checkFamilyStatusForOptionalCatLastMonth() {
        OptionalCategory category1 = new OptionalCategory();
        category1.setOptionalCategoryId(1);
        category1.setName("Category1");

        OptionalCategory category2 = new OptionalCategory();
        category2.setOptionalCategoryId(2);
        category2.setName("Category2");

        Mockito.when(optionalCategoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));
        Mockito.when(expenseRepository.getTotalAmountForOptionalCateInLastMonth(1))
                .thenReturn(100.0f);
        Mockito.when(expenseRepository.getTotalAmountForOptionalCateInLastMonth(2))
                .thenReturn(110.0f);

        List<String> result = expenseService.checkFamilyStatusForOptionalCatLastMonth();

        assertEquals(5, result.size());
        assertEquals("Family spending statistics for the last month for the optional category",
                result.get(0));
        assertEquals("Category1", result.get(1));
        assertEquals(" 100.0", result.get(2));
        assertEquals("Category2", result.get(3));
        assertEquals(" 110.0", result.get(4));

        verify(optionalCategoryRepository, times(1)).findAll();
        verify(expenseRepository, times(1))
                .getTotalAmountForOptionalCateInLastMonth(1);
        verify(expenseRepository, times(1))
                .getTotalAmountForOptionalCateInLastMonth(2);
    }

    @Test
    void getTotalAmountForOptionalCateInLastMonth() {
        Mockito.when(expenseRepository.getTotalAmountForOptionalCateInLastMonth(1))
                .thenReturn(110.0f);
        Float result = expenseService.getTotalAmountForOptionalCateInLastMonth(1);
        assertNotNull(result);
        assertEquals(110.0f, result);
        verify(expenseRepository, times(1))
                .getTotalAmountForOptionalCateInLastMonth(1);
    }

}