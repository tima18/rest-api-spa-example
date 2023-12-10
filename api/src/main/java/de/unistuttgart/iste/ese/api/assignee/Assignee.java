package de.unistuttgart.iste.ese.api.assignee;

import java.util.List;

import de.unistuttgart.iste.ese.api.todos.ToDo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "assignees")


public class Assignee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    /**
     * @param prename 
     * @param name
     * @param email
     */
    public Assignee(final String prename, final String name, final String email) {
        this.name = name;
        this.prename = prename;
        this.email = email; 
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

    public void addToDo(ToDo t2) {
    }

} 

    


    