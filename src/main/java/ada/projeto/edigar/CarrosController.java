package ada.projeto.edigar;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

// A classe CarrosController é um recurso RESTful que expõe endpoints para manipulação de carros.
@Path("/carros")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarrosController {

    @Inject
    CarrosService service;

    // Define o endpoint para cadastrar um novo carro.
    @POST
    public Carros cadastrar(Carros carro) {
        return service.cadastrar(carro);
    }

    // Define o endpoint para listar todos os carros.
    @GET
    public List<Carros> listar() {
        return service.listar();
    }

    // Define o endpoint para consultar um carro específico pelo ID.
    @GET
    @Path("/{id}")
    public Carros consultar(@PathParam("id") Long id) {
        return service.consultar(id).orElseThrow();
    }

    // Define o endpoint para atualizar os dados de um carro existente.
    @PUT
    @Path("/{id}")
    public Carros atualizar(@PathParam("id") Long id, Carros dados) {
        return service.atualizar(id, dados);
    }

    // Define o endpoint para alterar o status de um carro.
    @PATCH
    @Path("/{id}/status")
    public Carros alterarStatus(@PathParam("id") Long id, @QueryParam("status") StatusCarro status) {
        return service.alterarStatus(id, status);
    }

    // Define o endpoint para remover um carro pelo ID.
    @DELETE
    @Path("/{id}")
    public void remover(@PathParam("id") Long id) {
        service.remover(id);
    }
}