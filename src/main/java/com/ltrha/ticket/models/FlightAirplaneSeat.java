package com.ltrha.ticket.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flight_airplane_seat")
public class FlightAirplaneSeat {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = AirplaneSeat.class)
    private AirplaneSeat airplaneSeat;

    @ManyToOne(targetEntity = FlightDetailEntity.class)
    private FlightDetailEntity flight;

    private boolean isAvailable;
}
