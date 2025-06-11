package services.jsonservice;

import dto.collection.ParamDTO;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static java.lang.System.exit;

public class JsonService implements JsonStructure {
    static String filePath;

    public JsonService(String filePath) {
        // Constructor for jsonService
        this.filePath = filePath;
    }

    public static ParamDTO importJson() {
        int value1 = 0;
        int value2 = 0;

        int[] values = null;
        try (InputStream is = JsonService.class.getClassLoader().getResourceAsStream("param.json")) {
            if (is == null) {
                System.err.println("File 'param.json' not found in resources.");
                return new ParamDTO(value1, value2);
            }

            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(content);

            values = new int[JsonStructure.KEYS.length];
            for (int i = 0; i < JsonStructure.KEYS.length; i++) {
                values[i] = json.getInt(JsonStructure.KEYS[i]);
            }

        } catch (IOException e) {
            System.err.println("File reading failed: " + e.getMessage());
            exit(1);
        } catch (JSONException e) {
            System.err.println("Invalid JSON content: " + e.getMessage());
            exit(1);
        }

        return new ParamDTO(values[0], values[1]);
    }

    public static void exportJson(ParamDTO param) {
        JSONObject json = new JSONObject();
        Object[] values = { param.numBuyers(), param.numSellers() };

        for (int i = 0; i < JsonStructure.KEYS.length; i++) {
            json.put(JsonStructure.KEYS[i], values[i]);
        }

        try (FileWriter file = new FileWriter("src/main/resources/"+filePath)) {
            file.write(json.toString(4)); // pretty print with indentation = 4
            System.out.println("Saved to " + Path.of("src/main/resources/"+filePath).toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to write file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Main method for testing
        JsonService service = new JsonService("param.json");
        ParamDTO result = service.importJson();
        if (result == null) {
            System.out.println("No data found in JSON file.");
            return;
        }
        System.out.println(result.numBuyers());

        result = new ParamDTO(108, 20);
        System.out.println(result.numSellers());
        service.exportJson(result);
    }
}
