package it.epicode.U5_W3_D5.repository;

import it.epicode.U5_W3_D5.model.Evento;
import it.epicode.U5_W3_D5.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByOrganizzatore(Utente organizzatore);
}