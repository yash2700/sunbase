package com.sunbase.sunbase.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResponse {
    private int totalElements;
    private int totalPages;
    private int pageSize;
    private int pageNo;
    private List<CustomerDto> list;
    private boolean isLast;

}
