package de.unistuttgart.iste.ese.api.todos;

import org.springframework.data.repository.CrudRepository;

public interface ToDoRepository extends CrudRepository<ToDo, Long>{

	ToDo findByTitle(String title);
	
	ToDo findById(long id);
	
	Iterable<ToDo> findAll();
}

