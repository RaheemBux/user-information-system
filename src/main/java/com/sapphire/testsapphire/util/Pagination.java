package com.sapphire.testsapphire.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;

public class Pagination {
    public static Pageable createPageRequest(Map<String, String> params) {
        Pageable pageable = null;
        String page = params.get("currentPage");
        String itemsPerPage = params.get("itemsPerPage");
        String sortBy = params.get("sortBy");
        String direction = params.get("direction");

        if (sortBy != null && !sortBy.equalsIgnoreCase("") && !sortBy.equalsIgnoreCase("undefined")) {
            if (direction != null && !direction.equalsIgnoreCase("") && !sortBy.equalsIgnoreCase("undefined")) {
                if (direction.equalsIgnoreCase("desc")) {
                    pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(itemsPerPage), Sort.by(sortBy).descending());
                } else {
                    pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(itemsPerPage), Sort.by(sortBy));
                }
            } else {
                pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(itemsPerPage), Sort.by(sortBy));
            }
        } else {
            pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(itemsPerPage));
        }
        return pageable;
    }
}
