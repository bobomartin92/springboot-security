package dev.decadev.demo.controller;

import dev.decadev.demo.security.AppUserDetailService;
import dev.decadev.demo.security.AppUserDetails;
import dev.decadev.demo.security.JwtUtils;
import dev.decadev.demo.model.User;
import dev.decadev.demo.payload.AuthRequest;
import dev.decadev.demo.payload.PasswordUpdateRequest;
import dev.decadev.demo.payload.SignupDto;
import dev.decadev.demo.repository.UserRepository;
import dev.decadev.demo.util.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AppUserDetailService userDetailService;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserUtil userUtil;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody AuthRequest request){

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        final AppUserDetails user = (AppUserDetails) userDetailService.loadUserByUsername(request.getEmail());

        if(user != null){
            return ResponseEntity.ok(jwtUtils.generateToken(user));
        }

        return ResponseEntity.status(400).body("Some Error Occurred");
    }



    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody SignupDto signupDto){

        if(userRepository.existsByEmail(signupDto.getEmail())){
            return new ResponseEntity<>("Email is already taken", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(signupDto.getName());
        user.setEmail(signupDto.getEmail());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("update-password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordUpdateRequest passwordUpdateRequest){

        String password = passwordUpdateRequest.getPassword();
        String newPassword = passwordUpdateRequest.getNewPassword();
        String confirmPassword = passwordUpdateRequest.getConfirmPassword();

        String email = userUtil.getAuthenticatedUserEmail();

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();

            String encodedPassword = user.getPassword();

            boolean matches = passwordEncoder.matches(password, encodedPassword);

            if(!matches){
                throw new IllegalStateException("Password Incorrect");
            }

            boolean equals = newPassword.equals(confirmPassword);

            if(!equals){
                throw new IllegalStateException("New Password Does Not Match");
            }

            user.setPassword(passwordEncoder.encode(newPassword));

            User newUser = userRepository.save(user);
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        }

        return ResponseEntity.status(400).body("Something went wrong");
    }

}
