package de.unistuttgart.iste.ese.api.cats;

import de.unistuttgart.iste.ese.api.ApiVersion1;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@ApiVersion1
public class CatController {

    @Autowired
    private CatRepository catRepository;

    // executed after start-up and dependency injection
    @PostConstruct
    public void init() {

        // check if DB is empty
        long numberOfCats = catRepository.count();
        if (numberOfCats == 0) {
            // adding sample data for demonstration purposes
            Cat octocat = new Cat("Octocat", 42, "https://avatars1.githubusercontent.com/u/583231");
            catRepository.save(octocat);

            Cat grumpyCat = new Cat("Grumpy Cat", 10,
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/d/dc/Grumpy_Cat_%2814556024763%29_%28cropped%29.jpg/220px-Grumpy_Cat_%2814556024763%29_%28cropped%29.jpg");
            catRepository.save(grumpyCat);
        }
    }

    // get all cats
    @GetMapping("/cats")
    public List<Cat> getCats() {
        List<Cat> allCats = (List<Cat>) catRepository.findAll();
        return allCats;
    }

    // get a single cat
    @GetMapping("/cats/{id}")
    public Cat getCat(@PathVariable("id") long id) {

        Cat searchedCat = catRepository.findById(id);
        if (searchedCat != null) {
            return searchedCat;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Cat with ID %s not found!", id));
    }

    // create a cat
    @PostMapping("/cats")
    @ResponseStatus(HttpStatus.CREATED)
    public Cat createCat(@Valid @RequestBody Cat requestBody) {
        Cat cat = new Cat(requestBody.getName(), requestBody.getAgeInYears(),
                requestBody.getPicUrl());
        Cat savedCat = catRepository.save(cat);
        return savedCat;
    }

    // update a cat
    @PutMapping("/cats/{id}")
    public Cat updateCat(@PathVariable("id") long id, @Valid @RequestBody Cat requestBody) {
        requestBody.setId(id);
        Cat catToUpdate = catRepository.findById(id);
        if (catToUpdate != null) {
            Cat savedCat = catRepository.save(requestBody);
            return savedCat;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Cat with ID %s not found!", id));
    }

    // delete a cat
    @DeleteMapping("/cats/{id}")
    public Cat deleteCat(@PathVariable("id") long id) {

        Cat catToDelete = catRepository.findById(id);
        if (catToDelete != null) {
            catRepository.deleteById(id);
            return catToDelete;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Cat with ID %s not found!", id));
    }
}
