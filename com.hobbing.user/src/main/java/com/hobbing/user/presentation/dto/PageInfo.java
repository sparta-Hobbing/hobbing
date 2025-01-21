package com.hobbing.user.presentation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Value
public class PageInfo {

    @NotNull
    @Min(1)
    Integer pageSize;

    @NotNull
    @Min(1)
    Integer pageNumber;

    public PageRequest toPageRequest() {
        return PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}