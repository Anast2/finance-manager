package com.itclopedia.cources.services.expense;

import java.util.List;

public interface ExpenseService {

    List<String> checkFamilyStatusForOptionalCatLastMonth();

    Float getTotalAmountForOptionalCateInLastMonth(int categoryId);

}
