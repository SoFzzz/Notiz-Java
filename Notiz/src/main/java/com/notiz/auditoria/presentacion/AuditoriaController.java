package com.notiz.auditoria.presentacion;

import com.notiz.auditoria.negocio.AuditoriaService;
import com.notiz.auditoria.negocio.RegistroAuditoriaResultado;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auditoria")
public class AuditoriaController {

    private final AuditoriaService auditoria;

    public AuditoriaController(AuditoriaService auditoria) {
        this.auditoria = auditoria;
    }

    @GetMapping
    public List<RegistroAuditoriaResultado> consultar(@RequestParam(required = false) String notaId) {
        return auditoria.consultar(notaId);
    }
}
