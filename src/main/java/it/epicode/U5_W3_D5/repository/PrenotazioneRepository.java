package it.epicode.U5_W3_D5.repository;

import it.epicode.U5_W3_D5.model.Prenotazione;
import it.epicode.U5_W3_D5.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByUtente(Utente utente);
    boolean existsByUtenteAndEvento_Id(Utente utente, Long eventoId);
}