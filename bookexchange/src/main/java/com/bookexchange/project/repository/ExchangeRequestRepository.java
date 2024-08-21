package com.bookexchange.project.repository;

import com.bookexchange.project.dto.ExchangeRequestDTO;
import com.bookexchange.project.model.ExchangeRequest;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class ExchangeRequestRepository {

    @Autowired
    private EntityManager entityManager;

    public List<ExchangeRequestDTO> findByRequesterId(Long requesterId) {
        String jpql = "SELECT new com.bookexchange.project.dto.ExchangeRequestDTO(" +
                "e.id, e.bookId, b.title, b.author, e.requesterId, u.username, e.ownerId, e.status) " +
                "FROM ExchangeRequest e " +
                "JOIN Book b ON e.bookId = b.id " +
                "JOIN User u ON e.requesterId = u.id " +
                "WHERE e.requesterId = :requesterId";

        return entityManager.createQuery(jpql, ExchangeRequestDTO.class)
                .setParameter("requesterId", requesterId)
                .getResultList();
    }


    public List<ExchangeRequestDTO> findByOwnerId(Long ownerId) {
        String jpql = "SELECT new com.bookexchange.project.dto.ExchangeRequestDTO(" +
                "e.id, e.bookId, b.title, b.author, e.requesterId,u.username, e.ownerId, e.status) " +
                "FROM ExchangeRequest e " +
                "JOIN Book b ON e.bookId = b.id " +
                "JOIN User u ON e.requesterId = u.id " +
                "WHERE e.ownerId = :ownerId";

        return entityManager.createQuery(jpql, ExchangeRequestDTO.class)
                .setParameter("ownerId", ownerId)
                .getResultList();
    }

    public ExchangeRequest findById(Long exchangeRequestId) {
        return entityManager.find(ExchangeRequest.class, exchangeRequestId);
    }
    public ExchangeRequest save(ExchangeRequest exchangeRequest) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.persist(exchangeRequest);
        return exchangeRequest;
    }

    public ExchangeRequest updateAcceptRequest(Long exchangeRequestId, Long bookId){
        ExchangeRequest acceptedRequest = entityManager.find(ExchangeRequest.class, exchangeRequestId);
        acceptedRequest.setStatus("ACCEPTED");
        entityManager.merge(acceptedRequest);

        // Update the status of all other requests with the same bookId to "rejected"
        entityManager.createQuery(
                        "UPDATE ExchangeRequest e SET e.status = 'REJECTED' WHERE e.bookId = :bookId AND e.id != :exchangeRequestId")
                .setParameter("bookId", bookId)
                .setParameter("exchangeRequestId", exchangeRequestId)
                .executeUpdate();

        return acceptedRequest;
    }

    public ExchangeRequest updateDeclineRequest(Long exchangeRequestId){
        ExchangeRequest rejectedRequest = entityManager.find(ExchangeRequest.class, exchangeRequestId);
        rejectedRequest.setStatus("DECLINED");
        entityManager.merge(rejectedRequest);
        return rejectedRequest;
    }
}
