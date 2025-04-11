package med.voll.api.domain.consulta;

import jakarta.validation.ValidationException;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultasRepository consultasRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    public DadosDetalhamentoConsultas agendar(DadosAgendamentoConsultas dados){


        if(!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("id do paciente não existe!!");
        }

        if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("Id do medico não exite!");
        }
        validadores.forEach(v -> v.validar(dados));
        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);
        if (medico == null) {

            throw new ValidacaoException("não exite esse medico disponivel nesta data");
        }
        var consulta = new Consulta(null, medico, paciente, dados.data());
        consultasRepository.save(consulta);

        return new DadosDetalhamentoConsultas(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsultas dados) {
        if(dados.idMedico() != null ){
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if(dados.especialidade() == null){
            throw new ValidacaoException("Especialidade do medico é obrigatoria quando o medico não for escolhido!");
        }
        return medicoRepository.escolherMedicoAleatorioLivreData(dados.especialidade(), dados.data());
    }



}
