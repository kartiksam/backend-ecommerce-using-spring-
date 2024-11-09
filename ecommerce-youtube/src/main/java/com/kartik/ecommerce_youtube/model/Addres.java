package com.kartik.ecommerce_youtube.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Addres {
@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//@column we have used so that in db this name will be there  not firstName
@Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="street_address")
    private String streetAddress;

    @Column(name="city")
    private String city;

    @Column(name="state")
    private String state;

    @Column(name="zip_code")
    private String zipCode;

//    user and address relation one to many one user have multi address   but addressa and user realtion many to one
//    @ManyToOne: Defines a many-to-one relationship with the User entity. This means that multiple Address entries can be associated with a single User.
//   col with user_id exist ina drress tab;le @JoinColumn(name="user_id"): Specifies that the foreign key in the address table for this relationship is user_id.
//    @JsonIgnore: Ensures that when this entity is serialized to JSON, the user field is ignored, which can prevent circular references or limit sensitive data exposure.
//abhi tk signup or login others apis need tot est
    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

    private String mobile;

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
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Addres(Long id, String firstName, String lastName, String streetAddress, String city, String state, String zipCode, User user, String mobile) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.user = user;
        this.mobile = mobile;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    Addres(){

}









}
