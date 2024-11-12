package com.ltrha.ticket.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ltrha.ticket.models.BaseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Entity
//@Table(name = "`User`")
public class UserEntity extends BaseEntity {

    //    @Id
    private UUID id;

    private String fullName;

    //    @NaturalId
    private String email;

    //    @Column(unique = true)
    private String phoneNumber;

    private LocalDate dob;

    //    @ManyToOne
//    @JoinColumn(name = "role_id")
//    private RoleEntity role;

    private String role;
    private boolean isActive;

    public UserEntity(String name, String email) {
        this.fullName = name;
        this.email = email;
    }

}
