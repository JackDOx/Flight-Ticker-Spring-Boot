package com.ltrha.ticket.models;

import com.ltrha.ticket.models.BaseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="AirplaneDetail")
public class AirplaneDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    private String brandName;
    private String model;
    private String origin;
    private String numOfSeat;
    private String description;
    private String seatLayout;

    @OneToMany(mappedBy = "airplaneDetail", fetch = FetchType.LAZY)
    private List<AirplaneClassOption> seatOptions;

}
