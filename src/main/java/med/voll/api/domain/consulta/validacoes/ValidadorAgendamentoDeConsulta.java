package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.DadosAgendamentoConsultas;

public interface ValidadorAgendamentoDeConsulta {
    void validar(DadosAgendamentoConsultas dados);
}
