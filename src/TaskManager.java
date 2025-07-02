
import java.util.HashMap;
import java.util.Map;

public class TaskManager {
    private Map<Integer, Task> taskMap = new HashMap<>();
    private final JSONWritter json = new JSONWritter();
    private final String filePath = "tasks.json";
    //Add a task
    public void add(Task task){
        taskMap.put(task.getId(), task);
        json.writeJSON(taskMap, filePath);
    }
    //Delete a task by ID
    public void delete(int id){
        Task removed = taskMap.remove(id);
        if (removed != null) {
            System.out.println("Task succesfully deleted");
            json.writeJSON(taskMap, filePath);
        } else{
            System.out.println("Theres no task related to that ID");
        }
    }
    //Update a task by ID
    public void updateDescription(int id, String newDescription){
        Task updTask = taskMap.get(id);
        if (updTask != null) {
            updTask.setDescription(newDescription);
            updTask.setUpdatedAt();
            json.writeJSON(taskMap, filePath);
        } else {
            System.out.println("Theres no task related to that ID");
        }
    }
    //Change the status of a task
    public void updateStatus(int id, String changedStatus){
        Task updTask = taskMap.get(id);
        if (updTask != null) {
            if (changedStatus == "todo" || changedStatus == "in-progress" || changedStatus == "done") {
                updTask.setStatus(changedStatus);
                updTask.setUpdatedAt();
                json.writeJSON(taskMap, filePath);  
            } else {
                System.out.println("The Status value must be: \ntodo, in-progress or done");
            }
        }
    }
    //Show all the tasks
    public void showTasks(){
        System.out.println(String.format("|%-10s|%-60s|%-20s|%-30s|%-30s|", "ID","Description","Status","Created At","Updated At"));
        for (Task t : taskMap.values()) {
            System.out.println(String.format("|%-10d|%-60s|%-20s|%-30s|%-30s|", t.getId(),t.getDescription(),t.getStatus(),t.getCreatedAt(),t.getUpdatedAt()));
        }
    }
    //Show only the tasks that are done by status
    public void doneTasks(){
        System.out.println(String.format("|%-10s|%-60s|%-20s|%-30s|%-30s|", "ID","Description","Status","Created At","Updated At"));
        for (Task t : taskMap.values()) {
            if ("done".equalsIgnoreCase(t.getStatus())) {
              System.out.println(String.format("|%-10d|%-60s|%-20s|%-30s|%-30s|", t.getId(),t.getDescription(),t.getStatus(),t.getCreatedAt(),t.getUpdatedAt()));   
            }
        }
    }
    //Show only the tasks that are not done by status
    public void todoTasks(){
        System.out.println(String.format("|%-10s|%-60s|%-20s|%-30s|%-30s|", "ID","Description","Status","Created At","Updated At"));
        for (Task t : taskMap.values()) {
            if ("todo".equalsIgnoreCase(t.getStatus())) {
              System.out.println(String.format("|%-10d|%-60s|%-20s|%-30s|%-30s|", t.getId(),t.getDescription(),t.getStatus(),t.getCreatedAt(),t.getUpdatedAt()));   
            }
        }
    }
    //Show only the tasks that are in progress by status
    public void inProgressTasks(){
        System.out.println(String.format("|%-10s|%-60s|%-20s|%-30s|%-30s|", "ID","Description","Status","Created At","Updated At"));
        for (Task t : taskMap.values()) {
            if ("in-progress".equalsIgnoreCase(t.getStatus())) {
              System.out.println(String.format("|%-10d|%-60s|%-20s|%-30s|%-30s|", t.getId(),t.getDescription(),t.getStatus(),t.getCreatedAt(),t.getUpdatedAt()));   
            }
        }
    }
}
