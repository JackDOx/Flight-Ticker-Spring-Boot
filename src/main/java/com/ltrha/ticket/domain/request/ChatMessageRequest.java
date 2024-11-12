package com.ltrha.ticket.domain.request;

import lombok.*;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class ChatMessageRequest {
    private String message;
    private String userId;
    private String agentId;
}
