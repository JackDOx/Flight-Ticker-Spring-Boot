//package com.ltrha.ticket.models;
//
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.UUID;
//
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "user_device")
//public class UserDeviceEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(name = "id", updatable = false, nullable = false)
//    private UUID id;
//
////    @ManyToOne(fetch = FetchType.LAZY)
////    @JoinColumn(name = "user_id", nullable = false)
////    private UserEntity user;
//
//    private String userId;
//
//    @Column(name = "device_type")
//    private String deviceType;
//
//    private String deviceId;
//
//    private String ip;
//
//    @OneToOne(optional = false, mappedBy = "userDevice")
//    private RefreshTokenEntity refreshToken;
//
//    @Column(name = "is_refresh_active")
//    private Boolean isRefreshActive;
//}
