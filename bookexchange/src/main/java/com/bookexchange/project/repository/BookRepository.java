package com.bookexchange.project.repository;

import com.bookexchange.project.dto.AdminBookDisplayDTO;
import com.bookexchange.project.dto.SearchBookDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.Session;

import com.bookexchange.project.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class BookRepository {

    @Autowired
    private EntityManager entityManager;

    public Book save(Book book) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.persist(book);
        return book;
    }

    public List<SearchBookDTO> searchBooks(String title, String author, String genre, Long userId) {
        StringBuilder jpql = new StringBuilder("SELECT b FROM Book b WHERE 1=1"); // Start with a base condition
        List<String> conditions = new ArrayList<>();
        if (title != null && !title.isEmpty()) {
            conditions.add("b.title LIKE :title");
        }
        if (author != null && !author.isEmpty()) {
            conditions.add("b.author LIKE :author");
        }
        if (genre != null && !genre.isEmpty()) {
            conditions.add("b.genre = :genre");
        }

        // Join all conditions with OR
        if (!conditions.isEmpty()) {
            jpql.append(" AND (").append(String.join(" OR ", conditions)).append(")");
        }

        // Add the ownerId condition
        jpql.append(" AND b.ownerId <> :userId");

        // Create and configure the query
        TypedQuery<Book> query = entityManager.createQuery(jpql.toString(), Book.class);

        // Set parameters only if they are not null
        if (title != null && !title.isEmpty()) {
            query.setParameter("title", "%" + title + "%");
        }
        if (author != null && !author.isEmpty()) {
            query.setParameter("author", "%" + author + "%");
        }
        if (genre != null && !genre.isEmpty()) {
            query.setParameter("genre", genre); // Assuming genre is exact match
        }
        if (userId != null) {
            query.setParameter("userId", userId);
        }

        try {
            List<Book> books = query.getResultList();

            // Convert List<Book> to List<SearchBookDTO>
            List<SearchBookDTO> searchBookDTOs = new ArrayList<>();
            for (Book book : books) {
                boolean isUserRequested = checkIfUserRequested(book.getId(), userId);
                SearchBookDTO dto = new SearchBookDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getGenre(),
                        book.getBookCondition(),
                        book.getOwnerId(),
                        isUserRequested
                );
                searchBookDTOs.add(dto);
            }
            return searchBookDTOs;
        } catch (Exception e) {
            // Log the exception and handle it according to your application's needs
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list in case of an error
        }
    }

    private boolean checkIfUserRequested(Long bookId, Long userId) {
        String jpql = "SELECT COUNT(e) FROM ExchangeRequest e WHERE e.bookId = :bookId AND e.requesterId = :userId";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("bookId", bookId);
        query.setParameter("userId", userId);
        Long count = query.getSingleResult();
        return count > 0;
    }
    public Book findById(Long id) {
        return entityManager.find(Book.class, id);
    }

    public List<Book> findBookWithOwnerId(Long ownerId){
        String jpql = "SELECT b FROM Book b WHERE b.ownerId = :ownerId";

        // Create and configure the query
        TypedQuery<Book> query = entityManager.createQuery(jpql, Book.class);
        query.setParameter("ownerId", ownerId);

        try {
            // Execute the query and return the result list
            return query.getResultList();
        } catch (Exception e) {
            // Log the exception and handle it according to your application's needs
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list in case of an error
        }
    }

    public Book updateBook(Book book) {
        return entityManager.merge(book);
    }

    public List<AdminBookDisplayDTO> findAllBooksWithOwnerDetails() {
        String jpql = "SELECT new com.bookexchange.project.dto.AdminBookDisplayDTO(b.id, b.title, b.author, b.bookCondition, u.id, u.username) " +
                "FROM Book b JOIN User u ON b.ownerId = u.id";
        TypedQuery<AdminBookDisplayDTO> query = entityManager.createQuery(jpql, AdminBookDisplayDTO.class);
        return query.getResultList();
    }
}


