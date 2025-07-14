package com.example.bookify.global.common.apiresponse;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PagedResponseDto<T> {

    private List<T> content;

    private PageInfo pageInfo;

    @Getter
    @Builder
    public static class PageInfo {
        private int currentPage;
        private int totalPages;
        private long totalElements;
        private int size;
        private boolean hasNext;
        private boolean hasPrevious;
    }



    public static <T> PagedResponseDto<T> of(List<T> content, int currentPage, int totalPages, long totalElements, int size, boolean hasNext, boolean hasPrevious) {
        return PagedResponseDto.<T>builder()
                .content(content)
                .pageInfo(PageInfo.builder()
                        .currentPage(currentPage)
                        .totalPages(totalPages)
                        .totalElements(totalElements)
                        .size(size)
                        .hasNext(hasNext)
                        .hasPrevious(hasPrevious)
                        .build())
                .build();


    }

}
