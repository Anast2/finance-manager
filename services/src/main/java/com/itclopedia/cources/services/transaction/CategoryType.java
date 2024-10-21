package com.itclopedia.cources.services.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryType {

    OPTIONAL("Optional"),
    MANDATORY("Mandatory"),
    CUSTOM("Custom"),
    FINANCE_GOALS("Finance goals");

    private final String value;

    public static CategoryType fromString(String value) {
        for (CategoryType categoryType : CategoryType.values()) {
            if (categoryType.value.equalsIgnoreCase(value)) {
                return categoryType;
            }
        }
        throw new IllegalArgumentException("Unknown category type: " + value);
    }

}
