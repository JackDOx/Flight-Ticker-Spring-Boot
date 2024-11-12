package com.ltrha.ticket.models;

import com.ltrha.ticket.models.BaseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Ticket")
@Builder
public class TicketEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

//    @ManyToOne(fetch = FetchType.EAGER)
//    private UserEntity bookedBy;

    private String bookedById;
    //Owner information
    @Embedded
    private TicketOwner owner;

    private long fare;


    @ManyToOne(fetch = FetchType.LAZY)
    private FlightDetailEntity flightDetail;

    private int status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", referencedColumnName = "id")
    private AirplaneSeat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airplane_class_option_id", referencedColumnName = "id")
    private AirplaneClassOption airplaneClassOption;


    }
