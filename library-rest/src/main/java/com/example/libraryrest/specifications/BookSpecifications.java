package com.example.libraryrest.specifications;

import com.example.libraryrest.models.Author;
import com.example.libraryrest.models.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import java.util.List;

public class BookSpecifications {

    public static Specification<Book> yearFrom(Short yearFrom) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("year"), yearFrom);
    }

    public static Specification<Book> yearTo(Short yearTo) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("year"), yearTo);
    }

    public static Specification<Book> publisherLike(String publisher) {
        return (root, query, builder) -> builder.like(root.get("publisher"), "%" + publisher + "%");
    }

    public static Specification<Book> titleLike(String title) {
        return (root, query, builder) -> builder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Book> checkStatus(String status) {
        return (root, query, builder) -> builder.equal(root.get("status").as(String.class), status);
    }

    public static Specification<Book> betweenYears(Short yearFrom, Short yearTo) {
        return (root, query, builder) -> builder.between(root.get("year"), yearFrom, yearTo);
    }

    public static Specification<Book> authorName(String name, int index) {
        switch (index) {
            case 1:
                return (root, query, builder) -> {
                    ListJoin<Book, Author> authorJoin = root.joinList("authors");
                    return builder.in(authorJoin.get("firstName")).value(name);
                };
            case 2:
                return (root, query, builder) -> {
                    ListJoin<Book, Author> authorJoin = root.joinList("authors");
                    return builder.in(authorJoin.get("lastName")).value(name);
                };
            default:
                return null;
        }
    }
}
