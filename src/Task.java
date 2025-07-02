
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    //A task must contain id, description, status, createdAt, updatedAt
    private int id;
    private String description, status, createdAt, updatedAt;
    //Definition of a Date Structure and an ID Counter so we can make the ID auto-incremental, both of these are global so it can be shared by all of the instances
    private static final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static int contId = 1;
    //Constructor with the initial definition of data
    public Task(String description){
        id = contId++;
        this.description = description;
        status = "todo";
        createdAt = LocalDateTime.now().format(formato);
        updatedAt = "--/--/----";
    }

    //Getters and setters, the only important change made here was setUpdatedAt() when we call this method we expect it to determine the Date when it was updated
    public int getId() {return id;}
    public String getDescription() {return description;}
    public String getStatus() {return status;}
    public String getCreatedAt() {return createdAt;}
    public String getUpdatedAt() {return updatedAt;}

    public void setId(int id) {this.id = id;}
    public void setDescription(String description) {this.description = description;}
    public void setStatus(String status) {this.status = status;}
    public void setCreatedAt(String createdAt) {this.createdAt = createdAt;}
    public void setUpdatedAt() {updatedAt = LocalDateTime.now().format(formato); }
}