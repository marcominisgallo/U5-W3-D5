package it.epicode.U5_W3_D5.service;

import it.epicode.U5_W3_D5.exception.NotFoundException;
import it.epicode.U5_W3_D5.model.Prenotazione;
import it.epicode.U5_W3_D5.model.Utente;
import it.epicode.U5_W3_D5.repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    public Prenotazione savePrenotazione(Prenotazione prenotazione) {
        return prenotazioneRepository.save(prenotazione);
    }

    public List<Prenotazione> getPrenotazioniByUtente(Utente utente) {
        return prenotazioneRepository.findByUtente(utente);
    }

    public Prenotazione getPrenotazione(Long id) throws NotFoundException {
        return prenotazioneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prenotazione con id " + id + " non trovata"));
    }

    public void deletePrenotazione(Long id) throws NotFoundException {
        Prenotazione prenotazione = getPrenotazione(id);
        prenotazioneRepository.delete(prenotazione);
    }

    public boolean existsByUtenteAndEventoId(Utente utente, Long eventoId) {
        return prenotazioneRepository.existsByUtenteAndEvento_Id(utente, eventoId);
    }
}