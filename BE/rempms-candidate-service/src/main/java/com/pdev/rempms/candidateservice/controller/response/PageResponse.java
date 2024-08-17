package com.pdev.rempms.candidateservice.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResponse {

    private Integer totalPages;
    private Integer currentPage;
    private Long totalElements;
    private Object dataList;

}
