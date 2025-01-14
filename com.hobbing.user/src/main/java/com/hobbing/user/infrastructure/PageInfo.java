package com.hobbing.user.infrastructure;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Value
public class PageInfo {

    @NotNull
    @Min(10)
    Integer pageSize;

    @NotNull
    @Min(1)
    Integer currentPage;

    public PageRequest toPageRequest() {
        return PageRequest.of(currentPage - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}
