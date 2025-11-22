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

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userRepository.findByLogin(username);
    }

    public void registerUser(RegisterDTO dto) {
        if (this.userRepository.findByLogin(dto.username()) != null) {
            throw new RuntimeException("Usuário já existe!");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());

        User newUser = new User(dto.username(), encryptedPassword, dto.role(), dto.professorId());

        this.userRepository.save(newUser);
    }
}
