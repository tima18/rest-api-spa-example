package de.unistuttgart.iste.ese.api.cats;

import org.springframework.data.repository.CrudRepository;

public interface CatRepository extends CrudRepository<Cat, Integer> {
    Cat findByName(String name);

    Cat findById(long id);
}
