package it.epicode.U5_W3_D5.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventoDTO {
    private String titolo;
    private String descrizione;
    private LocalDateTime data;
    private String luogo;
    private int postiDisponibili;
}