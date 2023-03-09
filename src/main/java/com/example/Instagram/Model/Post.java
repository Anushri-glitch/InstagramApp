package com.example.Instagram.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.security.Timestamp;

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

    @Column(name="PostDate")
    private Timestamp postDate;

    @JoinColumn(name="UserId")
    @ManyToOne
    private User userId;

}
