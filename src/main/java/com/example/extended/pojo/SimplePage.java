package com.example.extended.pojo;

import org.springframework.data.domain.Page;

import java.util.List;

public class SimplePage<T> {

    private final List<T> items;
    private final long currentPage;
    private final long totalPages;
    private final long totalItems;

    public SimplePage(Page<T> page) {
        this.items = page.getContent();
        this.currentPage = page.getNumber();
        this.totalPages = page.getTotalPages();
        this.totalItems = page.getTotalElements();
    }

    public List<T> getItems() {
        return items;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public long getTotalItems() {
        return totalItems;
    }
}
