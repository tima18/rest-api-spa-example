package de.unistuttgart.iste.ese.api.cats;

import org.springframework.data.repository.CrudRepository;

public interface CatRepository extends CrudRepository<Cat, Long> {
    Cat findByName(String name);

    Cat findById(long id);
}
