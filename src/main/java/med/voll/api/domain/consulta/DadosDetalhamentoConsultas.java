package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsultas(Long id, Long idPaciente, Long idMedico, LocalDateTime data) {
}
