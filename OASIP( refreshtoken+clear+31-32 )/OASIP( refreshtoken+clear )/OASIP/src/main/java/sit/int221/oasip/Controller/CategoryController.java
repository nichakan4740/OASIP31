package sit.int221.oasip.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int221.oasip.DTO.CategoryDTO;
import sit.int221.oasip.Entity.Category;
import sit.int221.oasip.Service.CategoryService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/categories")
class CategoryController {
    @Autowired
    private CategoryService service;

    //Get all EventCategory
    @GetMapping("")
    public List<CategoryDTO> getAllCategory(){
        return service.getAllCategory();
    }

    //Get EventCategory with id
    @GetMapping("/{id}")
    public CategoryDTO getCustomerById (@PathVariable Integer id) {
        return service.getCategoryById(id);
    }

    //Update an EventCategory with id = ?
    @PutMapping("/{id}")
    public Category update(@RequestBody Category updateCategory, @PathVariable Integer id) {
        return service.updateId(updateCategory , id); }

}
