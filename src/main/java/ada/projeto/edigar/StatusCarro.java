package ada.projeto.edigar;

//Esse enum pode ser usado para controlar e verificar o estado de um carro em um sistema de locação,
// garantindo que só esses três estados sejam válidos.

public enum StatusCarro {
    AVAILABLE, // Disponível para locação
    RENTED, // Locado por um cliente
    UNDER_MAINTENANCE // Em manutenção, não disponível para locação
}
