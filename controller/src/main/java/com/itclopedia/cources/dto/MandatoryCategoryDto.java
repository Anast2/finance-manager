package com.itclopedia.cources.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MandatoryCategoryDto {

    private String name;

    private float amount;

    private LocalDate deadline;

    private int userId;

}
