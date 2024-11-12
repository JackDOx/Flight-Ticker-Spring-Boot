//package com.ltrha.ticket.service.impl;
//
//import com.ltrha.ticket.config.security.UserPrincipal;
//import com.ltrha.ticket.config.security.jwt.JwtProvider;
//import com.ltrha.ticket.constant.AppConstant;
//import com.ltrha.ticket.constant.business.AuthConstant;
//import com.ltrha.ticket.constant.business.UserRole;
//import com.ltrha.ticket.constant.web.route.CommonRoute;
//import com.ltrha.ticket.domain.request.auth.*;
//import com.ltrha.ticket.domain.response.JwtResponse;
//import com.ltrha.ticket.domain.response.LoginResponse;
//import com.ltrha.ticket.exception.FlightTicketRuntimeException;
//import com.ltrha.ticket.models.RoleEntity;
//import com.ltrha.ticket.models.UserDeviceEntity;
//import com.ltrha.ticket.models.UserEntity;
//import com.ltrha.ticket.modules.password.PasswordHasher;
//import com.ltrha.ticket.repositories.RefreshTokenRepository;
//import com.ltrha.ticket.repositories.RoleRepository;
//import com.ltrha.ticket.repositories.UserDeviceRepository;
//import com.ltrha.ticket.repositories.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.jetbrains.annotations.NotNull;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cache.Cache;
//import org.springframework.cache.CacheManager;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//import static com.ltrha.ticket.config.cache.RedisConfig.USER_DEVICE_PREFIX;
//
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//    Logger logger = LoggerFactory.getLogger(AuthService.class);
//
//    private final AuthenticationManager authenticationManager;
//    private final UserRepository userRepository;
//    private final PasswordHasher passwordHasher;
//    private final MailService emailService;
//    private final JwtProvider jwtProvider;
//    private final RefreshTokenRepository refreshTokenRepository;
//    private final UserDeviceRepository userDeviceRepository;
//    private final RoleRepository roleRepository;
//    private final CacheManager cacheManager;
//    private final RedisTemplate<String, Object> redisTemplate;
//
//
//    private void removeUserDeviceAndRefreshTokenFromCache(String userDeviceId) {
//        Cache cache = cacheManager.getCache("user_devices");
//        if (cache == null) {
//            throw new RuntimeException("Cache not found");
//        }
//        cache.evict(userDeviceId);
//    }
//
//    public LoginResponse login(@NotNull final LoginRequest loginRequest) {
//        var email = loginRequest.getEmail();
//        var password = loginRequest.getPassword();
//
//
//        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "User not found"));
//        Authentication authentication = authenticateUser(email, password);
//
//
//        var providedDeviceInfo = loginRequest.getRequestDeviceInfo();
//        //Device id is provided in request
//        if (providedDeviceInfo == null || providedDeviceInfo.getDeviceId() == null) {
//            throw new FlightTicketRuntimeException(HttpStatus.BAD_REQUEST, "Device not found");
//        }
//
//
//        //Save device in db
//        var userDeviceEntity = userDeviceRepository.findByDeviceId(providedDeviceInfo.getDeviceId());
//        if (userDeviceEntity.isEmpty()) {
//            var userDevice = UserDeviceEntity.builder()
//                    .deviceType("Test device")
//                    .deviceId(providedDeviceInfo.getDeviceId())
//                    .user(user)
//                    .ip("127.0.0.1")
//                    .build();
//            userDeviceRepository.save(userDevice);
//
//        }
//
//        //Create access token
//        var token = jwtProvider.generateJwtToken(authentication);
//
//        //Create refresh token
//        String refreshToken = createRefreshToken();
//
//        //Create jwt response
//        var jwt = new JwtResponse(token, refreshToken, String.valueOf(jwtProvider.getExpiryDuration()));
//
//        //Cache
//        cacheRefreshToken(providedDeviceInfo.getDeviceId(), refreshToken);
//
//        return new LoginResponse(jwt);
//    }
//
//    private void cacheRefreshToken(String userId, String refreshToken) {
//        //Cache
//        redisTemplate.opsForValue().set(USER_DEVICE_PREFIX + userId, refreshToken);
//
//    }
//
//    private String findRefreshTokenByUserDeviceIdFromCache(String deviceId) {
//        return (String) redisTemplate.opsForValue().get(USER_DEVICE_PREFIX + deviceId);
//
//    }
//
//
//    private String createRefreshToken() {
//
//        return UUID.randomUUID().toString();
//
//    }
//
//    private UserDeviceEntity createUserDevice(RequestDeviceInfo requestDeviceInfo, UserEntity user) {
//        var userDevice = new UserDeviceEntity();
//        userDevice.setDeviceType(requestDeviceInfo.getDeviceType());
//        userDevice.setIsRefreshActive(true);
//        userDevice.setUser(user);
//        return userDevice;
//    }
//
//    public JwtResponse refreshToken(RefreshTokenRequest request) {
//        String providedRefreshToken = request.getRefreshToken();
//        var deviceId = request.getDeviceId();
//
//        if (deviceId == null) {
//            throw new FlightTicketRuntimeException(HttpStatus.BAD_REQUEST, "Please provide deviceId");
//        }
//        var deviceInfo = userDeviceRepository.findByDeviceId(deviceId).orElseThrow(
//                () -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Cannot find deviceInfo")
//        );
//
//        var user = deviceInfo.getUser();
//
//        //Validate token
//        String cacheRefreshToken = findRefreshTokenByUserDeviceIdFromCache(deviceId);
//
//        if (!providedRefreshToken.equals(cacheRefreshToken)) {
//            throw new FlightTicketRuntimeException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
//        }
//
//        //Generate new access token
//        var newToken = jwtProvider.generateTokenFromUser(user);
//
//        return new JwtResponse(newToken, cacheRefreshToken, String.valueOf(jwtProvider.getExpiryDuration()));
//    }
//
//    private Authentication authenticateUser(String email, String password) {
//        try {
//            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
//        } catch (Exception e) {
//            throw new FlightTicketRuntimeException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
//        }
//    }
//
//    public void signup(final SignupRequest signupRequest) {
//
//        var email = signupRequest.getEmail();
//        var fullName = signupRequest.getFullName();
//        var phoneNumber = signupRequest.getPhoneNumber();
//        var password = signupRequest.getPassword();
//        var confirmPassword = signupRequest.getConfirmPassword();
//
//        if (userRepository.findByEmail(email).isPresent()) {
//            throw new FlightTicketRuntimeException(HttpStatus.BAD_REQUEST, "Email already exists");
//        }
//
//
//        if (!password.equals(confirmPassword)) {
//            throw new FlightTicketRuntimeException(HttpStatus.BAD_REQUEST, "Password and confirm password do not match");
//        }
//        //Hash password
//        String hashedPassword = passwordHasher.hashPassword(password);
//
//        try {
//            RoleEntity role = roleRepository.findByName(UserRole.USER);
//            var user = new UserEntity();
//            user.setEmail(email);
//            user.setFullName(fullName);
//            user.setPhoneNumber(phoneNumber);
//            user.setPassword(hashedPassword);
//            user.setRole(role);
//
//            userRepository.saveAndFlush(user);
//        } catch (Exception e) {
//            throw new FlightTicketRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "System error!");
//        }
//    }
//
//    public void sendActivationMail(SendActivationMailRequest request) {
//        UserEntity user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "User not found"));
//        String token = jwtProvider.generateTokenFromUser(user);
//        String activationLink = AppConstant.domain + CommonRoute.API_BASE_URL + "/activate" + "?token=" + token;
//        try {
//            emailService.sendActivationEmail(user, activationLink);
//        } catch (Exception e) {
//            logger.warn("Error sending activation email", e);
//        }
//    }
//
//    public void activateAccount(String token) {
//        String email = jwtProvider.getUserNameFromJwtToken(token);
//        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "User not found"));
//        user.setActive(true);
//        try {
//
//            userRepository.saveAndFlush(user);
//        } catch (Exception e) {
//            logger.warn("Error activating account", e);
//            throw new FlightTicketRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "System error!");
//        }
//    }
//
//    public void changePassword() {
//
//    }
//
//    private String generateRandomResetPasswordToken() {
//        return UUID.randomUUID().toString();
//    }
//
//    public void forgotPassword(String email) {
//        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "User not found"));
//
//        String token = generateRandomResetPasswordToken();
//
//        user.setResetPasswordToken(token);
//        user.setResetPasswordTokenExpiry(LocalDateTime.now().plusMinutes(AuthConstant.RESET_PASSWORD_LINK_EXPIRE_AFTER));
//        userRepository.saveAndFlush(user);
//
//        //Send password link
//        try {
//            emailService.sendResetPasswordEmail(user, token, AuthConstant.RESET_PASSWORD_LINK_EXPIRE_AFTER);
//        } catch (Exception e) {
//            logger.warn("Error sending reset password email", e);
//        }
//
//    }
//
//    public void resetPassword(ResetPasswordRequest request) {
//        String token = request.getToken();
//        String password = request.getPassword();
//        String confirmPassword = request.getConfirmPassword();
//
//        if (!password.equals(confirmPassword)) {
//            throw new FlightTicketRuntimeException(HttpStatus.BAD_REQUEST, "Password and confirm password do not match");
//        }
//        UserEntity user = userRepository.findByResetPasswordToken(token).orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "User not found"));
//
//        if (user.getResetPasswordTokenExpiry().isBefore(LocalDateTime.now())) {
//            throw new FlightTicketRuntimeException(HttpStatus.BAD_REQUEST, "Reset password link expired");
//        }
//        String hashedPassword = passwordHasher.hashPassword(password);
//        user.setPassword(hashedPassword);
//
//        try {
//            userRepository.saveAndFlush(user);
//        } catch (Exception e) {
//            logger.warn("Error resetting password", e);
//            throw new FlightTicketRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "System error!");
//        }
//
//    }
//
//    public void logout(LogoutRequest request, UserPrincipal currentUser) {
//        try {
////            refreshTokenRepository.deleteByUser_Id(currentUser.getId());
//        } catch (Exception e) {
//            throw new FlightTicketRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "System error!");
//        }
//
//
//    }
//}
