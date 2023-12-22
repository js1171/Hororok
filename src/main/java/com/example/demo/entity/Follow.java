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

    @Id
    @Column(name = "follow_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id", nullable = false)
    private Member toUser;

    public Follow(Member fromUser, Member toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}