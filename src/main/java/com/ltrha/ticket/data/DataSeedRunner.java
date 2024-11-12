package com.ltrha.ticket.data;

import com.ltrha.ticket.constant.business.UserRole;
import com.ltrha.ticket.models.*;
import com.ltrha.ticket.modules.password.PasswordHasher;
import com.ltrha.ticket.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeedRunner implements CommandLineRunner {

//    private final UserRepository userRepository;

    private final FlightDetailRepository flightDetailRepository;
    private final AirportRepository airportRepository;
    private final LocationRepository locationRepository;

    private final AirlineRepository airlineRepository;
    private final AirplaneRepository airplaneRepository;
    private final AirplaneSeatRepository airplaneSeatRepository;
    private final FlightAirplaneSeatRepository flightAirplaneSeatRepository;
    private final AirplaneSeatOptionRepository airplaneSeatOptionRepository;
    private final RoleRepository roleRepository;

    private final PasswordHasher passwordHasher;
    boolean run = false;

    @Override
    public void run(String... args) throws Exception {


        if (!run) return;

        seedRoleData();
//        seedUserData();
        seedAirlineData();

        //Location
        LocationEntity hanoi = new LocationEntity("Hanoi, Vietnam", "HNVM");
        LocationEntity hcmc = new LocationEntity("Ho Chi Minh City, Vietnam", "HCMVN");
        LocationEntity daNang = new LocationEntity("Da Nang, Vietnam", "DNVN");
        LocationEntity tokyo = new LocationEntity("Tokyo, Japan", "TKJP");

        locationRepository.saveAllAndFlush(List.of(hanoi, hcmc, daNang, tokyo));

        //Airport
        AirportEntity noiBai = new AirportEntity("Noi Bai International Airport", "VN");
        AirportEntity tanSonNhat = new AirportEntity("Tan Son Nhat International Airport", "VN");
        AirportEntity daNangAirport = new AirportEntity("Da Nang International Airport", "VN");
        AirportEntity naritaAirport = new AirportEntity("Narita International Airport", "VN");

        airportRepository.saveAllAndFlush(List.of(noiBai, tanSonNhat, daNangAirport, naritaAirport));

        //
        List<AirplaneDetail> airplanes = List.of(
                new AirplaneDetail(0, "Boeing", "737", "USA", "150", "A popular narrow-body aircraft", "3-3", null),
                new AirplaneDetail(0, "Airbus", "A320", "France", "180", "A widely used short-to-medium range aircraft", "3-3", null),
                new AirplaneDetail(0, "Embraer", "E190", "Brazil", "100", "A regional jet with a comfortable cabin", "2-2", null)
        );
        airplaneRepository.saveAllAndFlush(airplanes);

        //Flight detail
        FlightDetailEntity flight1 = new FlightDetailEntity(0, "AJH179", airlineRepository.findById(1).get(), null, null, airplaneRepository.findById(1).get(), LocalDateTime.of(2024, 8, 20, 8, 0), LocalDateTime.of(2024, 8, 20, 10, 0), noiBai, tanSonNhat, hanoi, hcmc);
        FlightDetailEntity flight2 = new FlightDetailEntity(0, "AHJ188", airlineRepository.findById(2).get(), null, null, airplaneRepository.findById(1).get(), LocalDateTime.of(2024, 8, 21, 14, 30), LocalDateTime.of(2024, 8, 21, 16, 0), tanSonNhat, daNangAirport, hcmc, daNang);
        FlightDetailEntity flight3 = new FlightDetailEntity(0, "DJG179", airlineRepository.findById(3).get(), null, null, airplaneRepository.findById(1).get(), LocalDateTime.of(2024, 8, 22, 6, 0), LocalDateTime.of(2024, 8, 22, 14, 0), daNangAirport, naritaAirport, daNang, tokyo);
        FlightDetailEntity flight4 = new FlightDetailEntity(0, "DGH195", airlineRepository.findById(1).get(), null, null, airplaneRepository.findById(1).get(), LocalDateTime.of(2024, 9, 1, 10, 0), LocalDateTime.of(2024, 9, 1, 14, 0), naritaAirport, noiBai, tokyo, hanoi);
        List<FlightDetailEntity> flights = List.of(
                flight1, flight2, flight3, flight4
        );

        flightDetailRepository.saveAllAndFlush(flights);


        //Flight seat option
        List<AirplaneClassOption> flightSeatOption = List.of(
                new AirplaneClassOption(0, airplanes.get(0), "Economy", 6),
                new AirplaneClassOption(0, airplanes.get(0), "Business", 6),
                new AirplaneClassOption(0, airplanes.get(0), "First Class", 6)
        );

        airplaneSeatOptionRepository.saveAllAndFlush(flightSeatOption);


        //Flight seat
        List<String> seatCodes = List.of("A", "B", "C");
        List<AirplaneSeat> seats = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 6; j++) {
                AirplaneSeat seat = new AirplaneSeat();
                seat.setSeatCode(((j - 1) / 3 + 1 + 2 * (i - 1)) + seatCodes.get(j % 3));
                seat.setAirplaneDetail(airplaneRepository.findById(1).get());
                seat.setAirplaneClassOption(airplaneSeatOptionRepository.findById(i).get());
                seats.add(seat);
            }
        }
        airplaneSeatRepository.saveAllAndFlush(seats);


    }

    private void seedRoleData() {
        List<RoleEntity> roles = List.of(
                new RoleEntity(0, "ROLE_USER"),
                new RoleEntity(0, "ROLE_ADMIN"),
                new RoleEntity(0, "ROLE_STAFF")
        );
        roleRepository.saveAllAndFlush(roles);
    }

    private void seedAirlineData() {
        List<AirlineEntity> airlines = List.of(
                new AirlineEntity(0, "Vietnam Airline", "VN", "Vietnam", "logoA.png", "www.airlineA.com", "123-456-7890", "contact@airlineA.com", "Address A", "Description A", true),
                new AirlineEntity(0, "Vietjet airline", "VJ", "Vietnam", "logoB.png", "www.airlineB.com", "123-456-7891", "contact@airlineB.com", "Address B", "Description B", true),
                new AirlineEntity(0, "Bamboo airway", "BA", "Vietnam", "logoC.png", "www.airlineC.com", "123-456-7892", "contact@airlineC.com", "Address C", "Description C", true)
        );
        airlineRepository.saveAllAndFlush(airlines);
    }

//    private void seedUserData() {
//        List<UserEntity> users = List.of(
//                new UserEntity("ha", "f@gmail.com"),
//                new UserEntity("ha1", "f1@gmail.com"),
//                new UserEntity("ha2", "f2@gmail.com"),
//                new UserEntity("ha3", "f3@gmail.com"),
//                new UserEntity(null, "Le Ha", "halthe180116@fpt.edu.vn", "0948595860", LocalDate.of(2000, 1, 16), passwordHasher.hashPassword("123"), roleRepository.findByName(UserRole.ADMIN), null, null, true)
//        );
//        userRepository.saveAllAndFlush(users);
//    }
}
