package sit.int221.oasip.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sit.int221.oasip.Service.RefreshService;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/refresh")
public class RefreshController {

    @Autowired
    private RefreshService refreshService;

    @GetMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity refreshLogin(@Validated HttpServletRequest request){
        return refreshService.refreshToken(request);
    }
}
