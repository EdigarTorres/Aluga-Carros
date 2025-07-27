package ada.projeto.edigar;

public class Carros {

    private Long id;
    private String marca;
    private String modelo;
    private StatusCarro status;
    private int ano;
    private String motor;

    // Construtor padrão necessário para Quarkus/Jackson
    public Carros() {}

    // Construtor com todos os parâmetros
    public Carros(Long id, String marca, String modelo, StatusCarro status, int ano, String motor) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.status = status;
        this.ano = ano;
        this.motor = motor;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public StatusCarro getStatus() { return status; }
    public void setStatus(StatusCarro status) { this.status = status; }

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    public String getMotor() { return motor; }
    public void setMotor(String motor) { this.motor = motor; }

    public String getCarTitle() {
        return marca + " " + modelo + " " + motor;
    }
}