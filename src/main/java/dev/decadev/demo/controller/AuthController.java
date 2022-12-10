package dev.decadev.demo.controller;

import dev.decadev.demo.config.CustomUserDetailService;
import dev.decadev.demo.config.JwtUtils;
import dev.decadev.demo.payload.AuthRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService userDetailService;

    private final JwtUtils jwtUtils;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateUser(@RequestBody AuthRequest request){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
        ));

        final UserDetails user = userDetailService.loadUserByUsername(request.getEmail());

        if(user != null){
            return ResponseEntity.ok(jwtUtils.generateToken(user));
        }

        return ResponseEntity.status(400).body("Some Error Occurred");
    }
//
//    @PostMapping("signup")
//    public ResponseEntity<?> registerUser(@RequestBody SignupDto signupDto){
//
//        return authService.registerUser(signupDto);
//    }
}
