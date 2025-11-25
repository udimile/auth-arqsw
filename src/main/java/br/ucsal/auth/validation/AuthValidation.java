package br.ucsal.auth.validation;

import org.springframework.stereotype.Component;

import br.ucsal.auth.exception.NaoExisteException;
import br.ucsal.auth.feign.ProfessorClient;

@Component
public class AuthValidation {

    private final ProfessorClient professorClient;

    public AuthValidation(ProfessorClient professorClient) {
        this.professorClient = professorClient;
    }

    public void validaProfessorId(Long id) {
        if (!professorClient.existsId(id)) {
            throw new NaoExisteException("O professor de id: " + id + " nao existe");
        }
    }

}
