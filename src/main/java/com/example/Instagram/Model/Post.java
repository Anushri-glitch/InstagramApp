package com.example.Instagram.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="table_Post")
public class Post {
    @Id
    @Column(name="PostId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer postId;

    @Column(name="CreatedDate")
    private Timestamp createDate;

    @Column(name="UpdateDate")
    private Timestamp updateDate;

    @Column(name="PostData")
    private String postData;

    @JoinColumn(name="UserId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
