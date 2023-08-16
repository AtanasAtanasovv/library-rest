package com.example.libraryrest.specifications;

import com.example.libraryrest.models.Author;
import com.example.libraryrest.models.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.ListJoin;

public class BookSpecifications {

    private static final String YEAR = "year";
    private static final String PUBLISHER = "publisher";
    private static final String TITLE = "title";
    private static final String STATUS = "status";
    private static final String AUTHORS = "authors";


    public static Specification<Book> yearFrom(Short yearFrom) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(YEAR), yearFrom);
    }

    public static Specification<Book> yearTo(Short yearTo) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(YEAR), yearTo);
    }

    public static Specification<Book> publisherLike(String publisher) {
        return (root, query, builder) -> builder.like(root.get(PUBLISHER), "%" + publisher + "%");
    }

    public static Specification<Book> titleLike(String title) {
        return (root, query, builder) -> builder.like(root.get(TITLE), "%" + title + "%");
    }

    public static Specification<Book> checkStatus(String status) {
        return (root, query, builder) -> builder.equal(root.get(STATUS).as(String.class), status);
    }

    public static Specification<Book> authorName(String name, String fieldName) {
                return (root, query, builder) -> {
                    ListJoin<Book, Author> authorJoin = root.joinList(AUTHORS);
                    return builder.in(authorJoin.get(fieldName)).value(name);
                };

    }
}
