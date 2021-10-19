package de.unistuttgart.iste.ese.api.cats;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CatController {

    private AtomicLong counter;

    @Autowired
    private CatRepository catRepository;

    // executed after start-up and dependency injection
    @PostConstruct
    public void init() {
        this.counter = new AtomicLong();

        // Adding sample data for demonstration purposes
        Cat existingOctocat = catRepository.findByName("Octocat");
        
        // if not yet stored in the database
        if (existingOctocat == null) {
            Cat octocat = new Cat(counter.getAndIncrement(), "Octocat", 42,
                    "https://avatars1.githubusercontent.com/u/583231");
            Cat storedOctocat = catRepository.save(octocat);

            System.out.println("Added sample data: " + storedOctocat.getName());
        } else {
            counter.set(existingOctocat.getId() + 1);
        }

        Cat existingGrumpyCat = catRepository.findByName("Grumpy Cat");
        // if not yet stored in the database
        if (existingGrumpyCat == null) {
            Cat grumpyCat = new Cat(counter.getAndIncrement(), "Grumpy Cat", 10,
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/d/dc/Grumpy_Cat_%2814556024763%29_%28cropped%29.jpg/220px-Grumpy_Cat_%2814556024763%29_%28cropped%29.jpg");
            Cat storedGrumpyCat = catRepository.save(grumpyCat);

            System.out.println("Added sample data: " + storedGrumpyCat.getName());
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
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Cat with ID %s not found!", id));
    }

    // create a cat
    @PostMapping("/cats")
    @ResponseStatus(HttpStatus.CREATED)
    public Cat createCat(@Valid @RequestBody Cat requestBody) {
        Cat cat = new Cat(counter.getAndIncrement(), requestBody.getName(), requestBody.getAgeInYears(),
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

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Cat with ID %s not found!", id));
    }

    // delete a cat
    @DeleteMapping("/cats/{id}")
    public Cat deleteCat(@PathVariable("id") long id) {
 
        Cat catToDelete = catRepository.findById(id);
        if (catToDelete != null) {
            catRepository.delete(catToDelete);
            return catToDelete;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Cat with ID %s not found!", id));
    }
}
