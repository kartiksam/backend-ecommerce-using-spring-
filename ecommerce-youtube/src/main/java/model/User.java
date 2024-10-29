package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    User(){

    }

    public User(Long id, String firstName, String lastName, String password, String email, String role, String mobile, List<Addres> address, List<PaymentInformation> paymentInformation, List<Rating> ratings, List<Review> reviews, LocalDateTime createdAt) {
        this.id = id;
        this.firstName = firstName;
        LastName = lastName;
        this.password = password;
        this.email = email;
        this.role = role;
        this.mobile = mobile;
        this.address = address;
        this.paymentInformation = paymentInformation;
        this.ratings = ratings;
        this.reviews = reviews;
        this.createdAt = createdAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String LastName;

    private String password;

    private String email;

    private String role;

    private String mobile;

//    relation between address and user
//    1 to many one user can have many address and one address one user
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Addres> address=new ArrayList<>();

//    not an enitty givbe embeded
    @Embedded
//    so that sep table will build
    @ElementCollection
//    in table fields and rows we can change
    @CollectionTable(name="payment_information",joinColumns = @JoinColumn(name="user_id"))
    private List<PaymentInformation> paymentInformation=new ArrayList<>();

    //   1 user can give rating and review to multiple product ,mapped by so that it will not create the di9ff tables
   @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
   @JsonIgnore
    private List<Rating> ratings=new ArrayList<>();

@JsonIgnore
    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
   private List<Review> reviews=new ArrayList<>();

private LocalDateTime createdAt;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<Addres> getAddress() {
        return address;
    }

    public void setAddress(List<Addres> address) {
        this.address = address;
    }

    public List<PaymentInformation> getPaymentInformation() {
        return paymentInformation;
    }

    public void setPaymentInformation(List<PaymentInformation> paymentInformation) {
        this.paymentInformation = paymentInformation;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
