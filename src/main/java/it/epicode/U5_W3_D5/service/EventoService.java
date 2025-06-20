package it.epicode.U5_W3_D5.service;

import it.epicode.U5_W3_D5.exception.NotFoundException;
import it.epicode.U5_W3_D5.model.Evento;
import it.epicode.U5_W3_D5.model.Utente;
import it.epicode.U5_W3_D5.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public Evento saveEvento(Evento evento) {
        return eventoRepository.save(evento);
    }

    public List<Evento> getAllEventi() {
        return eventoRepository.findAll();
    }

    public Evento getEvento(Long id) throws NotFoundException {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Evento con id " + id + " non trovato"));
    }

    public Evento updateEvento(Long id, Evento eventoDetails) throws NotFoundException {
        Evento evento = getEvento(id);
        evento.setTitolo(eventoDetails.getTitolo());
        evento.setDescrizione(eventoDetails.getDescrizione());
        evento.setData(eventoDetails.getData());
        evento.setLuogo(eventoDetails.getLuogo());
        evento.setPostiDisponibili(eventoDetails.getPostiDisponibili());
        return eventoRepository.save(evento);
    }

    public void deleteEvento(Long id) throws NotFoundException {
        Evento evento = getEvento(id);
        eventoRepository.delete(evento);
    }

    public List<Evento> getEventiByOrganizzatore(Utente organizzatore) {
        return eventoRepository.findByOrganizzatore(organizzatore);
    }
}