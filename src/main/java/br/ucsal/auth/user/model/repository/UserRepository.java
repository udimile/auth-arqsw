package br.ucsal.auth.user.model.repository;

import br.ucsal.auth.user.model.entity.User;
import br.ucsal.auth.user.model.entity.UserRole;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByUsername(String username);

    List<User> findByRole(UserRole role);
}
