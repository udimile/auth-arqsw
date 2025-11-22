package br.ucsal.auth.user.dto;

import br.ucsal.auth.user.model.entity.UserRole;

public record RegisterDTO(String username, String password, UserRole role, Long professorId) {
}
