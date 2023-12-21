package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "follow")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class Follow {

    @ManyToOne
    private Member member;

    @Id
    @Column(name = "follow_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    private Long fromUserId;
    private Long toUserId;

    public Follow(Member member, Long fromUserId, Long toUserId) {
        this.member = member;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }

}
