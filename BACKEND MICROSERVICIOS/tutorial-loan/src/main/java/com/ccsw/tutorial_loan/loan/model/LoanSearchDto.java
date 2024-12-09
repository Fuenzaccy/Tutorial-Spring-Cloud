package com.ccsw.tutorial_loan.loan.model;

import com.ccsw.tutorial_loan.common.pagination.PageableRequest;


public class LoanSearchDto {
    private PageableRequest pageable;

    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }

}
