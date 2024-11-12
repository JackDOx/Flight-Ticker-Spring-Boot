package com.ltrha.ticket.models;

import com.ltrha.ticket.models.BaseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "FlightDetail")
public class FlightDetailEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @NaturalId
    private String flightCode;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = AirlineEntity.class)
    @JoinColumn(name = "airline_id", referencedColumnName = "id")
    private AirlineEntity airline;
    //Seat info

    @OneToMany(mappedBy = "flight", fetch = FetchType.LAZY
            ,targetEntity = FlightAirplaneSeat.class, cascade = CascadeType.ALL)
    private Set<FlightAirplaneSeat> flightSeat;

    //Price info
    @OneToMany(mappedBy = "flightDetail", fetch = FetchType.LAZY
            ,targetEntity = FlightAirplaneClassDetail.class, cascade = CascadeType.ALL)
    private Set<FlightAirplaneClassDetail> seatOptionFare;


    @ManyToOne(fetch = FetchType.EAGER)
    private AirplaneDetail airplane;
    //Date info
    private LocalDateTime departDate;
    private LocalDateTime estimatedArrivedDate;

    //Location info
    @ManyToOne
    @JoinColumn(name = "departure_airport_id", referencedColumnName = "id")
    private AirportEntity departureAirport;
    @ManyToOne
    @JoinColumn(name = "destination_airport_id", referencedColumnName = "id")
    private AirportEntity destinationAirport;
    @ManyToOne
    @JoinColumn(name = "departure_location_id", referencedColumnName = "id")
    private LocationEntity departureLocation;
    @ManyToOne
    @JoinColumn(name = "destination_id", referencedColumnName = "id")
    private LocationEntity destination;



}
