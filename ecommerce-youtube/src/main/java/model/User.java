package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
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

User(){

}


}
