package de.unistuttgart.iste.ese.api.assignee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import de.unistuttgart.iste.ese.api.todos.ToDo;
import de.unistuttgart.iste.ese.api.todos.ToDoRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@RestController
@Validated
public class AssigneeController {
  @Autowired
    private AssigneeRepository assigneeRepository;

    @Autowired
    private ToDoRepository todoRepository;

    //add examples
    @PostConstruct
    public void init() {
        long numberOfAssignees = assigneeRepository.count();
        if (numberOfAssignees == 0) {

           Assignee ana = new Assignee("Ana Cristina", "Franco da Silva", "ana-cristina.franco-da-silva@iste.uni-stuttgart.de");
           assigneeRepository.save(ana);
        }
    }



    //get all assignees
    @GetMapping("/assignees")
    public List<Assignee> getAssignees() {
        List<Assignee> allAssignees = (List<Assignee>) assigneeRepository.findAll();
        return allAssignees;
    }

    //get a single assignee with specific id
    @GetMapping("/assignees/{id}")
    public Assignee getAssignee(@PathVariable("id") long id) {
        Assignee searchedAssignee = assigneeRepository.findById(id);
        if (searchedAssignee != null) {
            return searchedAssignee;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("Assignee with ID %s not found!", id));
    }

    //create an assignee
    @PostMapping("/assignees")
    @ResponseStatus(HttpStatus.CREATED)
    public Assignee createAssignee(@Valid @RequestBody Assignee requestBody) {
        Assignee assignee = new Assignee(requestBody.getPrename(), requestBody.getName(),
            requestBody.getEmail());
        Assignee savedAssignee = assigneeRepository.save(assignee);
        return savedAssignee;
    }

    //update an assignee
    @PutMapping("/assignees/{id}")
    public Assignee updateAssignee(@PathVariable("id") long id, @Valid @RequestBody Assignee requestBody) {
        requestBody.setId(id);
        Assignee assigneeToUpdate = assigneeRepository.findById(id);
        if (assigneeToUpdate != null) {
            Assignee savedAssignee = assigneeRepository.save(requestBody);
            return savedAssignee;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("Assignee with ID %s not found!", id));
    }

    //delete an assignee
    @DeleteMapping("/assignees/{id}")
    public Assignee deleteAssignee(@PathVariable("id") long id) {
        Assignee assigneeToDelete = assigneeRepository.findById(id);
        if (assigneeToDelete != null) {
            //removes assignee to be deleted from all todos
            Iterable<ToDo> todos = todoRepository.findAll();
            for (ToDo toDo : todos) {
                if (toDo.getAssigneeList().contains(assigneeToDelete)) {
                    toDo.getAssigneeList().remove(assigneeToDelete);
                }
            }
            assigneeRepository.deleteById(id);
            return assigneeToDelete;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("Assignee with ID %s not found!", id));
    }
}

