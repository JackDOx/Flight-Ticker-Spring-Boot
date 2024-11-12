package com.ltrha.ticket.models;


import com.ltrha.ticket.models.BaseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class ChatSessionEntity extends BaseEntity {
    @Id
    @Column(name = "chat_session_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chatSessionId;

    @OneToMany(targetEntity = ChatMessageEntity.class, fetch = FetchType.EAGER)
    private List<ChatMessageEntity> messages;

    private String userId;
    private String agentId;

}
