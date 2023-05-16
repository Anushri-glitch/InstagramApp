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
## :five: DataBase Response In project

:arrow_right: User table
 ```sql
 select * from tabl_user;
+---------+----------+----------------------+------------+------------+------------+----------------------------------+
| user_id | user_age | user_email           | first_name | last_name  | user_phone | user_password                    |
+---------+----------+----------------------+------------+------------+------------+----------------------------------+
|       1 |       30 | shurbhi12@gmail.com  | Shurbhi    | Srivastava | 1234567890 | shurbhi@1234                     |
|       2 |      100 | KeshavYogi@gmail.com | Keshav     | Yoginath   | 8081009534 | NULL                             |
|       3 |       45 | Anas@gmail.com       | Anas       | Khan       | 9111009534 | NULL                             |
|       4 |       67 | Rupali@gmail.com     | Rupali     | Jagga      | 9000009534 | NULL                             |
|       5 |       68 | Arunima@gmail.com    | Arunima    | Trivedi    | 9000112893 | NULL                             |
|      52 |       24 | Anushk12@gmail.com   | Anushka    | Srivastava | 1234567890 | 914A95E82726268ABE44CEE8D6654618 |
+---------+----------+----------------------+------------+------------+------------+----------------------------------+
```
:arrow_right: Post Table 
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
 select * from table_post;
+---------+-------------------------+-----------+-------------+---------+--------------------------------------------------+
| post_id | created_date            | post_date | update_date | user_id | post_data                                        |
+---------+-------------------------+-----------+-------------+---------+--------------------------------------------------+
|       6 | 2023-03-16 13:17:16.725 | NULL      | NULL        |       1 | this is essential data which is hiding by admin. |
|       7 | 2023-03-16 13:18:29.099 | NULL      | NULL        |       1 | this is essential data which is hiding by admin. |
|       8 | 2023-03-16 13:32:40.327 | NULL      | NULL        |       2 | Life is Essential                                |
|       3 | 2023-05-16 12:45:41.418 | NULL      | NULL        |       1 | I Am The Best!!!                                 |
+---------+-------------------------+-----------+-------------+---------+--------------------------------------------------+
```
:arrow_right: AuthenticationToken Table 

```sql
 select * from authentication_token;
+----------+--------------------------------------+---------------------+------------+
| token_id | token                                | token_creation_date | fk_user_id |
+----------+--------------------------------------+---------------------+------------+
|        1 | 98e04d49-6aff-46f5-8020-6fe24a652ec9 | 2023-05-15          |         52 |
+----------+--------------------------------------+---------------------+------------+
```
----------------------------------------------------------------------------------------------------------------------------------------------------------
## :six: Project Summary
### :o: Project result 
#### :purple_square: USER SIGNUP : http://localhost:8080/api/v1/userApp/signup
![Screenshot (801)](https://github.com/Anushri-glitch/InstagramApp/assets/47708011/9659b502-3911-4b59-8f07-b283a84322e7)

#### :purple_square: USER SIGNIN : http://localhost:8080/api/v1/userApp/signIn
![Screenshot (802)](https://github.com/Anushri-glitch/InstagramApp/assets/47708011/0e78c90b-f79b-4097-a266-93b845924460)

#### :purple_square: UPDATE USER : http://localhost:8080/api/v1/userApp/updateUser/1
![Screenshot (803)](https://github.com/Anushri-glitch/InstagramApp/assets/47708011/05fd46cc-6ba6-4f31-9be0-f3f610665ac6)

#### :purple_square: SAVE POST : http://localhost:8080/post
![Screenshot (804)](https://github.com/Anushri-glitch/InstagramApp/assets/47708011/0e90699c-5f34-4671-ad28-81ea505e6c0b)

----------------------------------------------------------------------------------------------------------------------------------------------------------


