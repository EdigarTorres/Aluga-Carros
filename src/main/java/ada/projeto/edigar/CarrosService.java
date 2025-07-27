package ada.projeto.edigar;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.*;

// A classe CarrosService é um serviço responsável pela lógica de negócios relacionada à entidade Carros.
// Ela utiliza um repositório para acessar e manipular dados dos carros.

@ApplicationScoped // Define que a classe terá um escopo de aplicação, ou seja, uma única instância será usada durante toda a vida útil da aplicação.
public class CarrosService {

    private final CarrosRepository repository;

    // O repositório CarrosRepository é injetado via construtor para permitir operações de persistência.
    @Inject
    public CarrosService(CarrosRepository repository) {
        this.repository = repository;
    }

    // Recebe um objeto Carros, define seu status como disponível e salva no repositório.
    public Carros cadastrar(Carros carro) {
        carro.setStatus(StatusCarro.AVAILABLE);
        return repository.save(carro);
    }

    // Retorna todos os carros cadastrados.
    public List<Carros> listar() {
        return repository.findAll();
    }

    // Busca um carro pelo ID, retornando um Optional<Carros>.
    public Optional<Carros> consultar(Long id) {
        return repository.findById(id);
    }

    // Atualiza os dados de um carro existente.
    public Carros atualizar(Long id, Carros dados) {
        Carros carro = repository.findById(id).orElseThrow();
        carro.setMarca(dados.getMarca());
        carro.setModelo(dados.getModelo());
        carro.setAno(dados.getAno());
        carro.setMotor(dados.getMotor());
        return repository.save(carro);
    }

    // Altera o status de um carro. Verifica se a mudança de status é válida antes de aplicar.
    public Carros alterarStatus(Long id, StatusCarro novoStatus) {
        Carros carro = repository.findById(id).orElseThrow();
        StatusCarro atual = carro.getStatus();

        if (novoStatus == StatusCarro.RENTED && atual != StatusCarro.AVAILABLE)
            throw new IllegalStateException("Só pode alugar se estiver disponível.");
        if (novoStatus == StatusCarro.AVAILABLE && !(atual == StatusCarro.RENTED || atual == StatusCarro.UNDER_MAINTENANCE))
            throw new IllegalStateException("Só pode ficar disponível se estiver alugado ou em manutenção.");
        carro.setStatus(novoStatus);
        return repository.save(carro);
    }

    // Remove um carro pelo ID, verificando se ele não está alugado antes de permitir a remoção.
    public void remover(Long id) {
        Carros carro = repository.findById(id).orElseThrow();
        if (carro.getStatus() == StatusCarro.RENTED)
            throw new IllegalStateException("Não pode remover veículo alugado.");
        repository.deleteById(id);
    }
}