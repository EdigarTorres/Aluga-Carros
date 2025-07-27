package ada.projeto.edigar;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;

// Esta classe CarrosRepository é um repositório simples para gerenciar objetos do tipo Carros em memória.

@ApplicationScoped
public class CarrosRepository {

    // Armazena os carros, usando o ID (Long) como chave
    private final Map<Long, Carros> carros = new HashMap<>();

    // Retorna uma lista com todos os carros armazenados.
    public List<Carros> findAll() {
        return new ArrayList<>(carros.values());
    }

    // Busca um carro pelo ID. Retorna um Optional para lidar com a possibilidade de não encontrar o carro.
    public Optional<Carros> findById(Long id) {
        return Optional.ofNullable(carros.get(id));
    }

    // Salva um carro no repositório. Se o carro já existir (mesmo ID), ele será atualizado.
    public Carros save(Carros carro) {
        carros.put(carro.getId(), carro);
        return carro;
    }

    // Remove um carro do repositório pelo ID. Se o carro não existir, nada acontece.
    public void deleteById(Long id) {
        carros.remove(id);
    }
}

