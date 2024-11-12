package com.ltrha.ticket.models;

import com.ltrha.ticket.models.BaseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "location")
public class LocationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    private String locationCode;
    private String locationName;


    public LocationEntity(String locationName, String locationCode) {
        this.locationName = locationName;
        this.locationCode = locationCode;
    }
}
