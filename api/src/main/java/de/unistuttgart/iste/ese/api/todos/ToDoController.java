package de.unistuttgart.iste.ese.api.todos;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import de.unistuttgart.iste.ese.api.assignee.Assignee;
import de.unistuttgart.iste.ese.api.assignee.AssigneeRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

public class ToDoController {



    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private AssigneeRepository assigneeRepository;

    //add examples
    @PostConstruct
    public void init() {
        // check if DB is empty
        long numberOfTodos = toDoRepository.count();
        if (numberOfTodos == 0) {
            // adding sample data for demonstration purposes
            ToDo t1 = new ToDo("title1", "description1", new Date());
            Assignee assignee1 = assigneeRepository.findById(1);
            assignee1.addToDo(t1);
            int[] t1AssigneeIds = {1};
            t1.setAssigneeIdList(t1AssigneeIds);
            toDoRepository.save(t1);

            ToDo t2 = new ToDo("title2", "description2", new Date());
            Assignee assignee2 = assigneeRepository.findById(2);
            assignee2.addToDo(t2);
            int[] t2AssigneeIds = {2};
            t2.setAssigneeIdList(t2AssigneeIds);
            toDoRepository.save(t2);
        }
    }

    //get all todos
    @GetMapping("/todos")
    public List<ToDo> getTodos() {
        List<ToDo> allTodos = (List<ToDo>) toDoRepository.findAll();
        return allTodos;
    }

    //get a single todo with specific id
    @GetMapping("/todos/{id}")
    public ToDo getTodo(@PathVariable("id") long id) {

        ToDo searchedToDo = toDoRepository.findById(id);
        if (searchedToDo != null) {
            return searchedToDo;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("Todo with ID %s not found!", id));
    }

    //create a todo
    @PostMapping("/todos")
    @ResponseStatus(HttpStatus.CREATED)
    public ToDo createToDo(@Valid @RequestBody ToDo requestBody) {
        ToDo todo = new ToDo(requestBody.getTitle(), requestBody.getDescription(), requestBody.getDueDate());
        assignAssigneesToTodo(requestBody, todo);
        ToDo savedTodo = toDoRepository.save(todo);
        return savedTodo;
    }

    
     //adds all assignees from assigneeIdList to assigneeList
    private void assignAssigneesToTodo(ToDo requestBody, ToDo todo) {
        if(requestBody.getAssigneeIdList() != null) {
            for(int assigneeId : requestBody.getAssigneeIdList()) {
                Assignee currentAssignee = assigneeRepository.findById(assigneeId);
                if(currentAssignee == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Assignee with ID %s not found!", assigneeId));
                }
                todo.getAssigneeList().add(currentAssignee);
            }
        }
    }

    //update a todo
    @PutMapping("/todos/{id}")
    public ToDo updateToDo(@PathVariable("id") long id, @Valid @RequestBody ToDo requestBody) {
        requestBody.setId(id);
        ToDo todoToUpdate = toDoRepository.findById(id);
        if (todoToUpdate != null) {
            todoToUpdate.setTitle(requestBody.getTitle());
            todoToUpdate.setDescription(requestBody.getDescription());
            todoToUpdate.setFinished(true);
            if(requestBody.isFinished()) {
                todoToUpdate.setFinishedDate(new Date());
            }
            if(requestBody.getDueDate() != null) {
                todoToUpdate.setDueDate(requestBody.getDueDate());
            }
            todoToUpdate.setAssigneeList(requestBody.getAssigneeList());
            todoToUpdate.setAssigneeIdList(requestBody.getAssigneeIdList());
            ToDo updatedTodo = toDoRepository.save(todoToUpdate);
            return updatedTodo;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("Todo with ID %s not found!", id));
    }

    //delete a todo
    @DeleteMapping("/todos/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ToDo deleteToDo(@PathVariable("id") long id) {
        ToDo todoToDelete = toDoRepository.findById(id);
        if (todoToDelete != null) {
            todoToDelete.setAssigneeList(null);
            toDoRepository.deleteById(id);
            return todoToDelete;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("Todo with ID %s not found!", id));
    }

}

