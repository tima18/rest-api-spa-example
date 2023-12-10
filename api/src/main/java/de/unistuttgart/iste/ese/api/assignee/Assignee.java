package de.unistuttgart.iste.ese.api.assignee;

import java.util.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import de.unistuttgart.iste.ese.api.todos.ToDo;

@Entity
@Table(name = "assignee")
public class Assignee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @NotNull
    @Size(min = 1)
    private String prename;

    @NotNull
    @Size(min = 1)
    private String name;

    @NotNull
    @Size(min = 1)
    @Email(regexp = "[a-zA-Z0-9._%+-]+@iste.uni-stuttgart.de")
    private String email;

    @ManyToMany(mappedBy = "assigneeList", fetch = FetchType.EAGER)
    private List<ToDo> todos;

    public Assignee() {}

    public Assignee(final String prename, final String name, final String email) {
        this.prename = prename;
        this.name = name;
        this.email = email;
        this.todos = new LinkedList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addToDo(ToDo todo) {
        this.todos.add(todo);
        todo.getAssigneeList().add(this);
    }

    public void removeToDo(ToDo todo) {
        this.todos.remove(todo);
        todo.getAssigneeList().remove(this);
    }

    public void setTodos(List<ToDo> todos) {
        this.todos = todos;
    }
}
