package br.ucsal.auth.user.controller;

import br.ucsal.auth.user.model.service.AuthorizationService;
import br.ucsal.auth.service.TokenService;
import br.ucsal.auth.user.dto.AuthDTO;
import br.ucsal.auth.user.dto.RegisterDTO;
import br.ucsal.auth.user.dto.ResponseDTO;
import br.ucsal.auth.user.model.entity.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new ResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO dto) {
        try {
            // Delega a lógica de criar usuário criptografado para o Service
            authorizationService.registerUser(dto);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            // Se usuário já existir, retorna erro 400
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
