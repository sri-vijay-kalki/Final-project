package com.bookexchange.project.repository;

import com.bookexchange.project.dto.AdminUserDisplayDTO;
import com.bookexchange.project.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Transactional
public class UserRepository {

 @Autowired
 private EntityManager entityManager;

 public User findByUsername(String username) {
  // Create a query to find a user by username
  TypedQuery<User> query = entityManager.createQuery(
          "SELECT u FROM User u WHERE u.username = :username", User.class);
  query.setParameter("username", username);

  // Get the single result or return null if no user found
  return query.getResultStream().findFirst().orElse(null);
 }

 public User save(User user) {
  Session currentSession = entityManager.unwrap(Session.class);
  currentSession.persist(user);
  return user;
 }

 public User findById(Long id) {
  return entityManager.find(User.class, id);
 }

 public void delete(User user) {
  if (entityManager.contains(user)) {
   entityManager.remove(user);
  } else {
   entityManager.remove(entityManager.merge(user));
  }
 }
 public List<AdminUserDisplayDTO> findAllWithBookCount() {
  // JPQL query to get the user and count of associated books
  String jpql = "SELECT new com.bookexchange.project.dto.AdminUserDisplayDTO(u.username, COUNT(b.id)) " +
          "FROM User u LEFT JOIN Book b ON u.id = b.ownerId " +
          "GROUP BY u.username";

  TypedQuery<AdminUserDisplayDTO> query = entityManager.createQuery(jpql, AdminUserDisplayDTO.class);
  return query.getResultList();
 }
}
