package sit.int221.oasip.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sit.int221.oasip.DTO.*;
import sit.int221.oasip.Service.UserService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
class UserController {
    @Autowired
    private UserService service;

    @GetMapping("")
    public List<UserDTO> getAllUser(@RequestParam(defaultValue = "name") String sortBy) {
        return service.getAllUser(sortBy);
    }

    @GetMapping("/{id}")
    public UserDTO getUserWithId (@PathVariable Integer id) {
        return service.getUserWithId(id);
    }

    @PostMapping("")
    public sit.int221.oasip.Entity.User create(@Validated @RequestBody AddUserDTO newUser){
        return service.save(newUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}")
    public EditUserDTO edit(@Validated @RequestBody EditUserDTO updateUser, @PathVariable Integer id) {
        return service.updateUser(updateUser, id);
    }

}
