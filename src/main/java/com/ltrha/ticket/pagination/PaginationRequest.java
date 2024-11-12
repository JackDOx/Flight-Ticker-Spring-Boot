package com.ltrha.ticket.pagination;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.OffsetScrollPosition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Optional;

@Getter
@Setter
public class PaginationRequest implements Pageable, Serializable {
    private long page;
    private long limit;
    private Sort sort;
    private long offset;

    public PaginationRequest(final long page, final long limit, final Sort sort) {
        if (page < 1) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }

        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }

        this.limit = limit;
        this.page = page;
        this.sort = sort;
        this.offset = (page - 1) * limit;
    }

    public PaginationRequest(final long page, final long limit) {
        this(page, limit, Sort.unsorted());
    }


    @Override
    public long getOffset() {
        return this.offset;
    }

    @Override
    public boolean isPaged() {
        return Pageable.super.isPaged();
    }

    @Override
    public boolean isUnpaged() {
        return Pageable.super.isUnpaged();
    }

    @Override
    public int getPageNumber() {
        return (int) (page / limit);
    }

    @Override
    public int getPageSize() {
        return (int) this.limit;
    }


    @Override
    public Sort getSortOr(Sort sort) {
        return Pageable.super.getSortOr(sort);
    }

    @Override
    public Pageable next() {
        return new PaginationRequest(getPage() + getPageSize(), getPageSize(), getSort());
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? new PaginationRequest(getPage() - getPageSize(), getPageSize(), getSort()) : this.first();
    }

    @Override
    public Pageable first() {
        return new PaginationRequest(0, this.getPageSize(), this.getSort());
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return new PaginationRequest(pageNumber, this.getPageSize(), this.getSort());
    }

    @Override
    public boolean hasPrevious() {
        return page > limit;
    }

    @Override
    public Optional<Pageable> toOptional() {
        return Pageable.super.toOptional();
    }

    @Override
    public Limit toLimit() {
        return Pageable.super.toLimit();
    }

    @Override
    public OffsetScrollPosition toScrollPosition() {
        return Pageable.super.toScrollPosition();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof PaginationRequest that)) return false;

        return new EqualsBuilder()
                .append(limit, that.limit)
                .append(page, that.page)
                .append(sort, that.sort)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(limit)
                .append(page)
                .append(sort)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("limit", limit)
                .append("offset", page)
                .append("sort", sort)
                .toString();
    }
}
