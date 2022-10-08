package sit.int221.oasip.Service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.oasip.DTO.EventDTO;
import sit.int221.oasip.Entity.Event;
import sit.int221.oasip.Repository.EventRepository;
import sit.int221.oasip.Utils.ListMapper;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;


    //Get all Event
    public List<EventDTO> getAllEvent(String sortBy) {
        List<Event> events = repository.findAll(Sort.by(sortBy).descending());
        return listMapper.mapList(events, EventDTO.class, modelMapper);
    }

    //Get Event with id
    public EventDTO getSimpleCustomerById(Integer id) {
        Event event = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Event id " + id +
                        "Does Not Exist !!!"
                ));

        return modelMapper.map(event, EventDTO.class);
    }

    //Add new Event
    public Event save(Event newEvent) {
        Event e = modelMapper.map(newEvent, Event.class);
        return repository.saveAndFlush(e);
    }


    //delete event with id = ?
    public void deleteById(Integer id) {
        repository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        id + " does not exist !!!"));
        repository.deleteById(id);
    }

    //update event with id = ?
    public Event updateId(@RequestBody Event updateEvent, @PathVariable Integer id) {
        Event event = repository.findById(id).map(o->mapEvent(o, updateEvent))
                .orElseGet(()->
                {
                    updateEvent.setId(id);
                    return updateEvent;
                });
        return repository.saveAndFlush(event);
    }
    private Event mapEvent(Event existingEvent, Event updateEvent) {
        existingEvent.setEventNotes(updateEvent.getEventNotes());
        existingEvent.setEventStartTime(updateEvent.getEventStartTime());

        return existingEvent;
    }
}
