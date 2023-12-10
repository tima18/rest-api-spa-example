package de.unistuttgart.iste.ese.api.assignee;

import org.springframework.data.repository.CrudRepository;

public interface AssigneeRepository extends CrudRepository<Assignee, Long> {

	Assignee findByName(String name);
	
	Assignee findById(long id);
	
	Iterable<Assignee> findAll();
}
