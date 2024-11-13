package br.com.mateusulrich.recipeservice.common.pagination;

import java.util.List;
import java.util.function.Function;

public record PageResponse<T>(
        int pageNumber,
        int pageSize,
        long total,
        List<T> items
) {
    public <R> PageResponse<R> map(final Function<T, R> mapper) {
        final List<R> list = this.items.stream()
                .map(mapper)
                .toList();
        return new PageResponse<>(pageNumber(), pageSize(), total(), list);
    }
}
