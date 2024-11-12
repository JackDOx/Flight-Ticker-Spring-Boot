package com.ltrha.ticket.models;

import com.ltrha.ticket.models.BaseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "flight_airplane_class_detail")
public class FlightAirplaneClassDetail extends BaseEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = FlightDetailEntity.class)
    private FlightDetailEntity flightDetail;

    @ManyToOne(targetEntity = AirplaneClassOption.class)
    private AirplaneClassOption airplaneClassOption;

    private long fare;

    private int seatCount;
    private int availableSeatCount;

}
