package pe.edu.cibertec.patitas_fronteras.dto;

public record LoginRequestDTO(String tipoDocumento, String numeroDocumento, String password) {
}
