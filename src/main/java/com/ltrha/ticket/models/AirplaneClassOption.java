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
@Table(name = "airplane_class_option")
public class AirplaneClassOption {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = AirplaneDetail.class)
    private AirplaneDetail airplaneDetail;

    //Economy, Business, First Class
    private String className;


    private int seatCount;

}
