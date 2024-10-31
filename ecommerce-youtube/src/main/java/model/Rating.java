package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="rating")
public class Rating {

@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//one user can rate multi prodcts but multi ratings belong to one user
@ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;

@JsonIgnore
    @ManyToOne
    @JoinColumn(name="product_id",nullable = false)
    private Product product;

@Column(name="rating")
    private double rating;

private LocalDateTime createdAt;


}
