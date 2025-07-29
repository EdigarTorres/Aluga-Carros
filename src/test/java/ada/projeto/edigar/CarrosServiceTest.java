package ada.projeto.edigar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarrosServiceTest {

    private CarrosRepository repository;
    private CarrosService service;

    @BeforeEach
    void setup() {
        repository = mock(CarrosRepository.class);
        service = new CarrosService(repository);
    }

    // Testa se o método cadastrar define o status do carro como disponível
    @Test
    void cadastrarCarroSetsStatusToAvailable() {
        Carros carro = new Carros();
        carro.setMarca("Fiat");
        carro.setModelo("Uno");
        carro.setAno(2020);
        carro.setMotor("1.0");

        when(repository.save(carro)).thenReturn(carro);

        Carros result = service.cadastrar(carro);

        assertEquals(StatusCarro.AVAILABLE, result.getStatus());
        verify(repository).save(carro);
    }

    // Testa se o método listar retorna todos os carros cadastrados
    @Test
    void listarCarrosReturnsAllCars() {
        List<Carros> carros = List.of(new Carros(), new Carros());
        when(repository.findAll()).thenReturn(carros);

        List<Carros> result = service.listar();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    // Testa se o método consultar retorna um carro pelo ID
    @Test
    void consultarCarroByIdReturnsCarIfExists() {
        Carros carro = new Carros();
        carro.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(carro));

        Optional<Carros> result = service.consultar(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(repository).findById(1L);
    }

    // Testa se o método consultar lança exceção se o carro não for encontrado
    @Test
    void consultarCarroByIdReturnsEmptyIfNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Optional<Carros> result = service.consultar(1L);

        assertTrue(result.isEmpty());
        verify(repository).findById(1L);
    }

    // Testa se o método alterarStatus altera o status do carro corretamente
    @Test
    void atualizarCarroUpdatesAllFields() {
        Carros carro = new Carros();
        carro.setId(1L);
        carro.setMarca("Fiat");
        carro.setModelo("Uno");
        carro.setAno(2020);
        carro.setMotor("1.0");

        Carros updatedCarro = new Carros();
        updatedCarro.setMarca("Fiat");
        updatedCarro.setModelo("Uno");
        updatedCarro.setAno(2021);
        updatedCarro.setMotor("1.4");

        when(repository.findById(1L)).thenReturn(Optional.of(carro));
        when(repository.save(carro)).thenReturn(carro);

        Carros result = service.atualizar(1L, updatedCarro);

        assertEquals("Fiat", result.getMarca());
        assertEquals("Uno", result.getModelo());
        assertEquals(2021, result.getAno());
        assertEquals("1.4", result.getMotor());
        verify(repository).findById(1L);
        verify(repository).save(carro);
    }

    // Testa se o método alterarStatus lança exceção se o status não puder ser alterado
    @Test
    void removerCarroThrowsIfRented() {
        Carros carro = new Carros();
        carro.setId(1L);
        carro.setStatus(StatusCarro.RENTED);

        when(repository.findById(1L)).thenReturn(Optional.of(carro));

        assertThrows(IllegalStateException.class, () -> service.remover(1L));
        verify(repository).findById(1L);
        verify(repository, never()).deleteById(1L);
    }

    // Testa se o método removerCarro deleta o carro se ele não estiver alugado
    @Test
    void removerCarroDeletesIfNotRented() {
        Carros carro = new Carros();
        carro.setId(1L);
        carro.setStatus(StatusCarro.AVAILABLE);

        when(repository.findById(1L)).thenReturn(Optional.of(carro));

        service.remover(1L);

        verify(repository).findById(1L);
        verify(repository).deleteById(1L);
    }
}