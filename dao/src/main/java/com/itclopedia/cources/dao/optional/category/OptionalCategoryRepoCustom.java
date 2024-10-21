package com.itclopedia.cources.dao.optional.category;

import com.itclopedia.cources.model.OptionalCategory;
import com.itclopedia.cources.model.User;

public interface OptionalCategoryRepoCustom {

    float check(String name);

    float checkForUser(User userId, OptionalCategory optionalCategoryId);

}
