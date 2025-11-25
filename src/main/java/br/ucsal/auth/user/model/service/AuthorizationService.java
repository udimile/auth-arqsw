package br.ucsal.auth.user.model.service;

import br.ucsal.auth.user.dto.RegisterDTO;
import br.ucsal.auth.user.model.entity.User;
import br.ucsal.auth.user.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.ucsal.auth.validation.AuthValidation;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    private AuthValidation authValidation;

    public AuthorizationService(AuthValidation authValidation) {
        this.authValidation = authValidation;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userRepository.findByUsername(username);
    }

    public void registerUser(RegisterDTO dto) {
        if (this.userRepository.findByUsername(dto.username()) != null) {
            throw new RuntimeException("Usuário já existe!");
        }
        authValidation.validaProfessorId(dto.professorId());

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());

        User newUser = new User(dto.username(), encryptedPassword, dto.role(), dto.professorId());

        this.userRepository.save(newUser);
    }
}
