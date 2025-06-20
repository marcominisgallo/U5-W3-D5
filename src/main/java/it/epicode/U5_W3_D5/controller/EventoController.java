package it.epicode.U5_W3_D5.controller;

import it.epicode.U5_W3_D5.dto.EventoDTO;
import it.epicode.U5_W3_D5.exception.NotFoundException;
import it.epicode.U5_W3_D5.model.Evento;
import it.epicode.U5_W3_D5.model.Ruolo;
import it.epicode.U5_W3_D5.model.Utente;
import it.epicode.U5_W3_D5.service.EventoService;
import it.epicode.U5_W3_D5.service.UtenteService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventi")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private UtenteService utenteService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Evento saveEvento(@RequestBody @Validated EventoDTO eventoDTO, BindingResult bindingResult, Authentication authentication) throws NotFoundException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (e, s) -> e + s));
        }
        Utente organizzatore = utenteService.getUtenteByUsername(authentication.getName());
        if (organizzatore.getRuolo() != Ruolo.ORGANIZZATORE) {
            throw new ValidationException("Solo gli organizzatori possono creare eventi.");
        }
        Evento evento = Evento.builder()
                .titolo(eventoDTO.getTitolo())
                .descrizione(eventoDTO.getDescrizione())
                .data(eventoDTO.getData())
                .luogo(eventoDTO.getLuogo())
                .postiDisponibili(eventoDTO.getPostiDisponibili())
                .organizzatore(organizzatore)
                .build();
        return eventoService.saveEvento(evento);
    }

    @GetMapping("")
    public List<Evento> getAllEventi() {
        return eventoService.getAllEventi();
    }

    @GetMapping("/{id}")
    public Evento getEvento(@PathVariable Long id) throws NotFoundException {
        return eventoService.getEvento(id);
    }

    @PutMapping("/{id}")
    public Evento updateEvento(@PathVariable Long id, @RequestBody @Validated EventoDTO eventoDTO, BindingResult bindingResult, Authentication authentication) throws NotFoundException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (e, s) -> e + s));
        }
        Utente organizzatore = utenteService.getUtenteByUsername(authentication.getName());
        if (organizzatore.getRuolo() != Ruolo.ORGANIZZATORE) {
            throw new ValidationException("Solo gli organizzatori possono modificare eventi.");
        }
        Evento eventoDetails = Evento.builder()
                .titolo(eventoDTO.getTitolo())
                .descrizione(eventoDTO.getDescrizione())
                .data(eventoDTO.getData())
                .luogo(eventoDTO.getLuogo())
                .postiDisponibili(eventoDTO.getPostiDisponibili())
                .build();
        return eventoService.updateEvento(id, eventoDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteEvento(@PathVariable Long id) throws NotFoundException {
        eventoService.deleteEvento(id);
    }
}