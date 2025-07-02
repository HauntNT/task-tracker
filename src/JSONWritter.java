import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class JSONWritter {
    public void writeJSON(Map<Integer, Task> taskMap, String filePath){
        try (FileWriter writer = new FileWriter(filePath)){
            writer.write("[");
            int i = 0;
            int size = taskMap.size();
            for (Map.Entry<Integer, Task> entry : taskMap.entrySet()) {
                Task t = entry.getValue();
                writer.write("{\"id\": "+t.getId()+",");
                writer.write("\"description\": \""+t.getDescription()+"\",");
                writer.write("\"status\": \""+t.getStatus()+"\",");
                writer.write("\"createdAt\": \""+t.getCreatedAt()+"\",");
                writer.write("\"updatedAt\": \""+t.getUpdatedAt()+"\"}");
                if (++i < size) {
                    writer.write(",");
                }
            }
            writer.write("]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
