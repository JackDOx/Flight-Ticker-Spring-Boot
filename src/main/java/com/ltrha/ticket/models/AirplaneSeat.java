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
@Table(name = "airplane_seat")
public class AirplaneSeat {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String seatCode;

    @ManyToOne(targetEntity = AirplaneDetail.class, fetch = FetchType.LAZY)
    private AirplaneDetail airplaneDetail;

    @ManyToOne(targetEntity = AirplaneClassOption.class, fetch = FetchType.LAZY)
    private AirplaneClassOption airplaneClassOption;

}
