# Instagram Application
This Application Uses MySQL Database...
##### :purple_square: Its an API Based Web Application
## :one: Frameworks and Languages Used -
    1. SpringBoot
    2. JAVA
    3. Postman
    4. SQL
    
## :two: Dependency Used
    1. Spring Web
    2. Spring Boot Dev Tools
    3. Lombok
    4. Spring Data JPA
    5. MySQL Driver
    6. Jakarta
    7. Javax
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
## :three: Dataflow (Functions Used In)
### :purple_square: 1. Model - Model is used to Iniitialize the required attributes and create the accessable constructors and methods
#### :o: User.java
```java
@Entity
@Table(name= "tabl_User")
public class User {
    @Id
    @Column(name="userId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
    private String userFirstName;
    private String userLastName;
    private Integer userAge;
    private String userEmail;
    private String userPassword;
    private String userPhoneNumber;
}
```
#### :o: Post.java
```java
@Entity
@Table(name="table_Post")
public class Post {
    @Id
    @Column(name="PostId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer postId;
    private Timestamp createDate;
    private Timestamp updateDate;
    private String postData;

    @JoinColumn(name="UserId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
```
#### :o: AuthenticationToken.java
```java
public class AuthenticationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tokenId;

    private String token;
    private LocalDate tokenCreationDate;

    @OneToOne
    @JoinColumn(nullable = false, name = "fk_user_Id")
    private User user;

    public AuthenticationToken(User user) {
        this.user = user;
        this.tokenCreationDate = LocalDate.now();
        this.token = UUID.randomUUID().toString();
    }
}
```
##### To See Model
:white_check_mark: [Instagram-Model](https://github.com/Anushri-glitch/InstagramApp/tree/master/src/main/java/com/example/Instagram/Model)
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

### :purple_square: 2. Service - This Layer is used to write the logic of our CRUD operaions.
#### :o: UserService.java
```java
@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;
    
    @Autowired
    AuthenticationService authService;
    
    public SignInOutput signIn(SignInInput signInDto) {
        //check Email
        ------
        //encrypt Password
        ------

        //match it with database encrypted password
        boolean isPasswordValid = encryptedPassword.equals(user.getUserPassword());
        if(!isPasswordValid){
            throw new IllegalStateException("User Invalid!!....Signup Again!!");
        }

        //figure out the token
        -------
        //setup output response
        -------
    }
}
```

#### :o: ProductService.java
```java
@Service
public class PostService {

    @Autowired
    IPostRepository postRepository;
    public int savePost(Post post) {
        Post savedPost = postRepository.save(post);
        return savedPost.getPostId();
    }
}
```

#### :o: AuthenticationService.java
```java
@Service
public class AuthenticationService {

    @Autowired
    ITokenRepo tokenRepo;

    public void saveToken(AuthenticationToken token){
        tokenRepo.save(token);
    }

    public AuthenticationToken getToken(User user) {
        return tokenRepo.findByUser(user);
    }
}
```

#### To See Service
:white_check_mark: [Instagram-Service](https://github.com/Anushri-glitch/InstagramApp/tree/master/src/main/java/com/example/Instagram/Service)
----------------------------------------------------------------------------------------------------------------------------------------------------

### :purple_square: 3. Controller - This Controller is used to like UI between Model and Service and also for CRUD Mappings.
#### :o: UserController.java
```java
@RestController
@RequestMapping("api/v1/userApp")
public class UserController {

    @Autowired
    UserService userService;

    //SignUp
    @PostMapping("/signup")
    public SignUpOutput signUp (@RequestBody SignUpInput signUpDto){
        return userService.signUp(signUpDto);
    }
}
```

#### :o: PostController.java
```java
@RestController
public class PostController {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    PostService postService;

    @PostMapping(value="/post")
    public ResponseEntity<String> savePost(@RequestBody String postRequest){
        Post post = setPost(postRequest);
        int postId = postService.savePost(post);
        return new ResponseEntity<>("post saved with Id - " + postId, HttpStatus.CREATED);
    }
}
```

#### To See Controller
:white_check_mark: [Instagram-Controller](https://github.com/Anushri-glitch/InstagramApp/tree/master/src/main/java/com/example/Instagram/Controller)
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
### :purple_square: 3. Repository : data access object (DAO) is an object that provides an abstract interface to some type of database or other persistence mechanisms.
#### :o: IUserRepository.java
```java
@Repository
public interface IUserRepository extends JpaRepository<User,Integer> {
    User findFirstByUserEmail(String email);
}
```

#### :o: IPostRepository.java
```java
@Repository
public interface IPostRepository extends JpaRepository<Post,Integer> {
}
```

#### :o: ITokenRepo.java
```java
@Repository
public interface ITokenRepo extends JpaRepository<AuthenticationToken,Long> {
    AuthenticationToken findByUser(User user);
}
```

#### To See Repository
:white_check_mark: [Instagram-DAO](https://github.com/Anushri-glitch/InstagramApp/tree/master/src/main/java/com/example/Instagram/Dao)
-------------------------------------------------------------------------------------------------------------------------------------------------------
### :purple_square: 4. DTO :  This Layer is for SignIn and SignUp Authentication
#### :o: SignUpInput.java

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpInput {
    private String userFirstName;
    private String userLastName;
    private Integer userAge;
    private String userEmail;
    private String userPassword;
    private String userContact;
}
```
#### :o: SignUpOutput.java

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpOutput {

    String status;
    String message;
}
```

#### :o: SignInInput.java

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInInput {

    private String userEmail;
    private String userPassword;
}
```

#### :o: SignInOutput.java
```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInOutput {

    private String status;
    private String message;
}
```

#### To See DTO
:white_check_mark: [Instagram-DTO](https://github.com/Anushri-glitch/InstagramApp/tree/master/src/main/java/com/example/Instagram/dto)    
-------------------------------------------------------------------------------------------------------------------------------------------------------
## :four: DataStructures Used in Project
    1. ResponseEntity
    2. List
    3. Json
-------------------------------------------------------------------------------------------------------------------------------------------------------
## :Six: DataBase Response In project

:arrow_right: User table
 ```sql
 select * from user;
+---------+-------------------+-----------+-------+-----------+
| user_id | email             | password  | phone | user_name |
+---------+-------------------+-----------+-------+-----------+
|       1 | Anuhska@gmail.com | Anushka01 | 8091  | Anushka   |
|       2 | Richa@gmail.com   | Richa01   | 8091  | Richa     |
+---------+-------------------+-----------+-------+-----------+
```
:arrow_right: Product Table 
```sql
 select * from product;
+------------+-----------------+----------+-------------------------------+-------+---------------------+
| product_id | brand           | category | description                   | price | product_name        |
+------------+-----------------+----------+-------------------------------+-------+---------------------+
|          1 | Glam Roots      | ladies   | Sweart Shirts with cotton mix |  1000 | Sweart Shirt        |
|          2 | Kimayra         | ladies   | Kurta                         |   760 | Floral Flared Kurta |
|          3 | Saffron threads | ladies   | Floral Print Anrkali Kurta    |  1376 | Anarkali Kurta      |
|          4 | Eyebogler       | Gents    | Colour Blocked Polo T-shirt   |   195 | T-shrirt            |
|          5 | London Hills    | Gents    | Polo T-shirt with ribbed hems |   429 | Polo T-shirt        |
|          6 | Bullmer         | Gents    | Striped Henley T-shirt        |   499 | Colur T-shirt       |
+------------+-----------------+----------+-------------------------------+-------+---------------------+
```

:arrow_right: Address Table 

```sql
 select * from address;
+------------+--------------+----------+--------------+---------------+---------+---------+
| address_id | address_name | landmark | phone_number | state         | zipcode | user_id |
+------------+--------------+----------+--------------+---------------+---------+---------+
|          1 | Alopibag     | Chauraha | 8091         | Uttar Pradesh | 211018  |       1 |
|          2 | Beniganj     | Chauraha | 8091         | Uttar Pradesh | 211020  |       2 |
+------------+--------------+----------+--------------+---------------+---------+---------+
```
:arrow_right: Orders Table 

```sql
 select * from orders;
+----------+------------------+------------+------------+---------+
| order_id | product_quantity | address_id | product_id | user_id |
+----------+------------------+------------+------------+---------+
|        1 |                5 |          1 |          3 |       1 |
|        2 |                5 |          2 |          1 |       2 |
+----------+------------------+------------+------------+---------+
```
----------------------------------------------------------------------------------------------------------------------------------------------------------
## :six: Project Summary
### :o: Project result 
#### :purple_square: SAVE USER : http://localhost:8080/user
![Screenshot (783)](https://github.com/Anushri-glitch/Ecommerce-Application/assets/47708011/6d0162a6-a690-47cf-be5b-2e7b745d189d)

#### :purple_square: SAVE PRODUCT : http://localhost:8080/product
![Screenshot (784)](https://github.com/Anushri-glitch/Ecommerce-Application/assets/47708011/bb59f5a4-14ac-4b86-a98b-1f6bc70b1186)

#### :purple_square: SAVE ADDRESS : http://localhost:8080/address
![Screenshot (785)](https://github.com/Anushri-glitch/Ecommerce-Application/assets/47708011/b6435d5f-d085-48cd-9fa5-62f755bb635d)

#### :purple_square: SAVE ORDERS : http://localhost:8080/orders
![Screenshot (786)](https://github.com/Anushri-glitch/Ecommerce-Application/assets/47708011/cd59ec7f-4366-47de-8202-3e2ab5ec26d9)

#### :purple_square: FIND ORDER BY ORDERID : http://localhost:8080/employee
![Screenshot (789)](https://user-images.githubusercontent.com/47708011/236825054-6614b006-9659-41ee-bedb-0e24931e2b8d.png)

#### :purple_square: FIND USER BY USERID : http://localhost:8080/employeeId
![Screenshot (798)](https://user-images.githubusercontent.com/47708011/236825714-388a9185-487d-4c5f-922b-439dcadd9041.png)

#### :purple_square: FIND ALL PRODUCTS BASED ON CATEGORY : http://localhost:8080/employee
![Screenshot (790)](https://user-images.githubusercontent.com/47708011/236825981-0086490b-c829-48e0-a5bf-19a5a3717fa5.png)

#### :purple_square: DELETE PRODUCTS BASED ON PRODUCTID : http://localhost:8080/employee
![Screenshot (791)](https://user-images.githubusercontent.com/47708011/236826279-6db1cdea-d5ec-43a2-9769-d60c211a87f5.png)
----------------------------------------------------------------------------------------------------------------------------------------------------------


