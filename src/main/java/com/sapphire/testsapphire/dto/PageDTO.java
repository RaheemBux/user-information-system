package com.sapphire.testsapphire.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PageDTO {
    private Long totalElements;
    private Integer totalPages;
    private List data;

    public PageDTO(List data, Long totalElements, Integer totalPages) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.data = data;
    }
}
