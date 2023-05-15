package com.example.Instagram.Model;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name= "tabl_User")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class User {
    @Id
    @Column(name="userId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @Column(name="firstName")
    private String userFirstName;

    @Column(name="lastName")
    private String userLastName;

    @Column(name="userAge")
    private Integer userAge;

    @Column(name="userEmail")
    private String userEmail;

    @Column(name = "userPassword")
    private String userPassword;

    @Column(name="userPhone")
    private String userPhoneNumber;

    public User(String userFirstName, String userLastName,
                Integer userAge, String userEmail, String userPassword, String userPhoneNumber) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userAge = userAge;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userPhoneNumber = userPhoneNumber;
    }
}
