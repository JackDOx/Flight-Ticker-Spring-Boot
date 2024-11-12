//package com.ltrha.ticket.repositories;
//
//import com.ltrha.ticket.models.UserDeviceEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Collection;
//import java.util.Optional;
//import java.util.UUID;
//
//public interface UserDeviceRepository extends JpaRepository<UserDeviceEntity, UUID> {
//    public Collection<UserDeviceEntity> findByUser_Id(UUID userId);
//    public Optional<UserDeviceEntity> findByDeviceId(String deviceId);
//
//}
