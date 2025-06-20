package it.epicode.U5_W3_D5.controller;

import it.epicode.U5_W3_D5.dto.PrenotazioneDTO;
import it.epicode.U5_W3_D5.exception.NotFoundException;
import it.epicode.U5_W3_D5.model.Evento;
import it.epicode.U5_W3_D5.model.Prenotazione;
import it.epicode.U5_W3_D5.model.Utente;
import it.epicode.U5_W3_D5.service.EventoService;
import it.epicode.U5_W3_D5.service.PrenotazioneService;
import it.epicode.U5_W3_D5.service.UtenteService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private EventoService eventoService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione savePrenotazione(@RequestBody @Validated PrenotazioneDTO prenotazioneDTO, BindingResult bindingResult, Authentication authentication) throws NotFoundException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (e, s) -> e + s));
        }
        Utente utente = utenteService.getUtenteByUsername(authentication.getName());
        Evento evento = eventoService.getEvento(prenotazioneDTO.getEventoId());

        if (prenotazioneService.existsByUtenteAndEventoId(utente, evento.getId())) {
            throw new ValidationException("Hai già prenotato questo evento.");
        }

        Prenotazione prenotazione = Prenotazione.builder()
                .utente(utente)
                .evento(evento)
                .dataPrenotazione(LocalDateTime.now())
                .build();

        return prenotazioneService.savePrenotazione(prenotazione);
    }

    @GetMapping("")
    public List<Prenotazione> getPrenotazioni(Authentication authentication) {
        Utente utente = utenteService.getUtenteByUsername(authentication.getName());
        return prenotazioneService.getPrenotazioniByUtente(utente);
    }

    @DeleteMapping("/{id}")
    public void deletePrenotazione(@PathVariable Long id) throws NotFoundException {
        prenotazioneService.deletePrenotazione(id);
    }
}