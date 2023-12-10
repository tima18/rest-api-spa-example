package de.unistuttgart.iste.ese.api.todos;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import de.unistuttgart.iste.ese.api.assignee.Assignee;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "toDo")
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    
    
    private String title;

    private String description;

    
    private boolean finished;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Assignee.class)
    private List<Assignee> assigneeList;

    @JsonFormat(shape = Shape.NUMBER_INT)
    private Date createdDate;

    @JsonFormat(shape = Shape.NUMBER_INT)
    private Date dueDate;

    @JsonFormat(shape = Shape.NUMBER_INT)
    private Date finishedDate;

    private int[] assigneeIdList;

    public ToDo() {
        this.assigneeList = new LinkedList<>();
    }

    public ToDo(String title, String description, Date dueDate) {
        this.title = title;
        this.description = description;
        this.finished = false;
        this.createdDate = Calendar.getInstance().getTime();
        this.dueDate = dueDate;
        this.assigneeList = new LinkedList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public List<Assignee> getAssigneeList() {
        return assigneeList;
    }

    public void setAssigneeList(List<Assignee> assigneeList) {
        this.assigneeList = assigneeList;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    public int[] getAssigneeIdList() {
        return assigneeIdList;
    }

    public void setAssigneeIdList(int[] assigneeIdList) {
        this.assigneeIdList = assigneeIdList;
    }
    
}
