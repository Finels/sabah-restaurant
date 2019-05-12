package com.fasteam.common.orm.dto;

import java.util.List;

/**
 * Description:  com.fasteam.common.orm.dto
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/3/24
 */
public class Pagination<T> {
    public static final String DEFAULT_PAGE_NO = "1";
    public static final String DEFAULT_PAGE_SIZE = "10";
    private int pageNo;
    private int pageSize;
    private long totalCount;
    private long totalPages;
    private List<T> contents;

    public Pagination() {
    }

    public Pagination(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getContents() {
        return this.contents;
    }

    public void setContents(List<T> contents) {
        this.contents = contents;
    }

    public long getTotalPages() {
        if (this.totalCount < 0L) {
            return -1L;
        } else if (this.totalCount == 0L) {
            return 1L;
        } else {
            long count = this.totalCount / (long)this.pageSize;
            if (this.totalCount % (long)this.pageSize > 0L) {
                ++count;
            }

            return count;
        }
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = (long)totalPages;
    }

    public int getStartIndex() {
        int pageStart = this.pageNo <= 0 ? 1 : this.pageNo;
        int offset = this.pageSize <= 0 ? 10 : this.pageSize;
        return (pageStart - 1) * offset;
    }

    public int getEndIndex() {
        return this.getStartIndex() + this.pageSize;
    }
}
