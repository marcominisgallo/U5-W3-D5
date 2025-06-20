package it.epicode.U5_W3_D5.dto;

import lombok.Data;

@Data
public class UtenteRegisterDTO {
    private String username;
    private String email;
    private String password;
    private String ruolo; // "UTENTE" o "ORGANIZZATORE"
}