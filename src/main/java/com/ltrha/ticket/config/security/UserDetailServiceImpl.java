package com.ltrha.ticket.config.security;

//import com.ltrha.ticket.repositories.UserRepository;
import com.ltrha.ticket.service.impl.client.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserServiceClient userServiceClient;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        return userServiceClient.getUser(id)
                .map(UserPrincipal::build)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }


}
