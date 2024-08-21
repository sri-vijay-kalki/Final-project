package com.bookexchange.project.model;



import jakarta.persistence.*;

@Entity
public class ExchangeRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long bookId;
	private Long requesterId;
	private Long ownerId;
	private String status; // e.g., pending, accepted, declined
	
	//Getters and Setters
	public Long getId() {
	 return id;
	}
	
	public void setId(Long id) {
	 this.id = id;
	}
	
	public Long getBookId() {
	 return bookId;
	}
	
	public void setBookId(Long bookId) {
	 this.bookId = bookId;
	}
	
	public Long getRequesterId() {
	 return requesterId;
	}
	
	public void setRequesterId(Long requesterId) {
	 this.requesterId = requesterId;
	}
	
	public Long getOwnerId() {
	 return ownerId;
	}
	
	public void setOwnerId(Long ownerId) {
	 this.ownerId = ownerId;
	}
	
	public String getStatus() {
	 return status;
	}
	
	public void setStatus(String status) {
	 this.status = status;
	}
}



