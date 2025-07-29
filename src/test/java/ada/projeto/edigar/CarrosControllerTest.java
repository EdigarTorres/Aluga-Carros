package ada.projeto.edigar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Teste unitário para a classe CarrosController, que testa os métodos do controlador de carros.
class CarrosControllerTest {

    @Mock
    private CarrosService service;

    @InjectMocks
    private CarrosController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Testa o método cadastrar do controlador, verificando se ele retorna o carro cadastrado corretamente.
    @Test
    void cadastrarCarroRetornaCarroCadastrado() {
        Carros carro = new Carros();
        carro.setMarca("Fiat");
        carro.setModelo("Uno");

        when(service.cadastrar(carro)).thenReturn(carro);

        Carros result = controller.cadastrar(carro);

        assertEquals("Fiat", result.getMarca());
        assertEquals("Uno", result.getModelo());
        verify(service).cadastrar(carro);
    }

    // Testa o método listar do controlador, verificando se ele retorna uma lista de carros.
    @Test
    void listarRetornaListaDeCarros() {
        List<Carros> carros = List.of(new Carros(), new Carros());
        when(service.listar()).thenReturn(carros);

        List<Carros> result = controller.listar();

        assertEquals(2, result.size());
        verify(service).listar();
    }

    // Testa o método consultar do controlador, verificando se ele retorna um carro pelo ID.
    @Test
    void consultarPorIdRetornaCarroSeExistir() {
        Carros carro = new Carros();
        carro.setId(1L);
        when(service.consultar(1L)).thenReturn(Optional.of(carro));

        Carros result = controller.consultar(1L);

        assertEquals(1L, result.getId());
        verify(service).consultar(1L);
    }

    // Testa o método consultar do controlador, verificando se ele lança uma exceção NotFoundException quando o carro não é encontrado.
    void consultarPorIdLancaExcecaoSeNaoEncontrado() {
        when(service.consultar(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> controller.consultar(1L));
        verify(service).consultar(1L);
    }

    // Testa o método atualizar do controlador, verificando se ele retorna o carro atualizado corretamente.
    @Test
    void atualizarCarroRetornaCarroAtualizado() {
        Carros carro = new Carros();
        carro.setId(1L);
        carro.setMarca("Fiat");
        carro.setModelo("Uno");

        Carros dados = new Carros();
        dados.setMarca("Fiat");
        dados.setModelo("Palio");

        when(service.atualizar(1L, dados)).thenReturn(carro);

        Carros result = controller.atualizar(1L, dados);

        assertEquals("Fiat", result.getMarca());
        assertEquals("Uno", result.getModelo());
        verify(service).atualizar(1L, dados);
    }

    // Testa o método alterarStatus do controlador, verificando se ele retorna o carro com o status alterado corretamente.
    @Test
    void alterarStatusRetornaCarroComStatusAlterado() {
        Carros carro = new Carros();
        carro.setId(1L);
        carro.setStatus(StatusCarro.AVAILABLE);

        when(service.alterarStatus(1L, StatusCarro.RENTED)).thenReturn(carro);

        Carros result = controller.alterarStatus(1L, StatusCarro.RENTED);

        assertEquals(StatusCarro.AVAILABLE, result.getStatus());
        verify(service).alterarStatus(1L, StatusCarro.RENTED);
    }

    // Testa o método remover do controlador, verificando se ele chama o serviço de remoção corretamente.
    @Test
    void removerCarroChamaServicoRemover() {
        doNothing().when(service).remover(1L);

        controller.remover(1L);

        verify(service).remover(1L);
    }
}