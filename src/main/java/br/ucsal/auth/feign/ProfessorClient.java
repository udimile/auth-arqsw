package br.ucsal.auth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "professorClient", url = "http://localhost:8083/professores")
public interface ProfessorClient {

    @GetMapping("/exists/{id}")
    public Boolean existsId(@PathVariable Long id);
}
