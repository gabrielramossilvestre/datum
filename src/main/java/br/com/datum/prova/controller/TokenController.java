package br.com.datum.prova.controller;

import br.com.datum.prova.dto.LoginRequest;
import br.com.datum.prova.dto.LoginResponse;
import br.com.datum.prova.dto.MessageDto;
import br.com.datum.prova.message.QueueProducer;
import br.com.datum.prova.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class TokenController {

    private final JwtEncoder jwtEncoder;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final QueueProducer queueProducer;

    public TokenController(JwtEncoder jwtEncoder,
                           UserService userService,
                           BCryptPasswordEncoder passwordEncoder,
                           QueueProducer queueProducer) {
        this.jwtEncoder = jwtEncoder;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.queueProducer = queueProducer;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        var user = userService.getByName((loginRequest.getUsername()));

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest.getPassword(), passwordEncoder)) {
            throw new BadCredentialsException("user or password is invalid!");
        }

        var now = Instant.now();
        //Alterar o tempo
        var expiresIn = 300000L;

        var claims = JwtClaimsSet.builder()
                .issuer("prova")
                .subject(user.get().getUserId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }

    @PostMapping("/send_message")
    public ResponseEntity<Void> login(@RequestBody MessageDto message) {
        queueProducer.sendMessage(message.getContent());
        return ResponseEntity.ok().build();
    }
}
