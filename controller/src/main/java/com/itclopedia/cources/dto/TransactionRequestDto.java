package com.itclopedia.cources.dto;

import com.itclopedia.cources.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDto {

    private Transaction transaction;

    private int userId;

    private String category;

    private int idCategory;

}
