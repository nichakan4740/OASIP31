package sit.int221.oasip.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sit.int221.oasip.DTO.EventDTO;
import sit.int221.oasip.Entity.Event;
import sit.int221.oasip.Service.EventService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/events")
class EventController {
    @Autowired
    private EventService service;

    //Get all Event
    @GetMapping("")
    public List<EventDTO> getAllEvent(@RequestParam(defaultValue = "eventStartTime") String sortBy){
        return service.getAllEvent(sortBy);
    }

    //Get Event with id
    @GetMapping("/{id}")
    public EventDTO getCustomerById(@PathVariable Integer id) {
        return service.getSimpleCustomerById(id);
    }

    //Add new Event
    @PostMapping("")
    public Event create(@Validated @RequestBody Event newEvent){
        return service.save(newEvent);
    }

    //Delete an event with id = ?
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }

    //Update an event with id = ?
    @PutMapping("/{id}")
    public Event update(@RequestBody Event updateEvent, @PathVariable Integer id) {
        return service.updateId(updateEvent, id);
    }

}
