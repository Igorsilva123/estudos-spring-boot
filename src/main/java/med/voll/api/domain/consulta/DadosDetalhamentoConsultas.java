package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsultas(Long id, Long idPaciente, Long idMedico, LocalDateTime data) {
    public DadosDetalhamentoConsultas(Consulta consulta) {
        this(consulta.getId(), consulta.getPaciente().getId(), consulta.getMedico().getId(), consulta.getData());
    }
}
