//package com.ltrha.ticket.models;
//
//
//import java.time.Instant;
//import java.util.UUID;
//
//import com.ltrha.ticket.models.BaseEntity.BaseEntity;
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.GenericGenerator;
//
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@Builder
//@Table(name = "refresh_token")
//public class RefreshTokenEntity extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(updatable = false, nullable = false)
//    private UUID id;
//
//    @Column(name = "token", nullable = false, unique = true)
//    private String token;
//
//    @Column(name = "refresh_count")
//    private Long refreshCount;
//
//    @Column(name = "expiry_date", nullable = false)
//    private Instant expiryDate;
//
//
//    @JoinColumn(name = "user_device_id", nullable = false)
//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
//    private UserDeviceEntity userDevice;
//
//    public void incrementRefreshCount() {
//        refreshCount = refreshCount + 1;
//    }
//}