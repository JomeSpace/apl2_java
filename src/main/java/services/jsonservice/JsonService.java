package services.jsonservice;

import dtos.ParamDTO;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static services.jsonservice.JsonStructure.KEYS;

/**
 * "Persistenzschicht" here all the data is saved and extracted from/to a JSON file.
 * Service class to handle reading from and writing to a JSON configuration file.
 */
public class JsonService {
    private final Path filePath;

    private static final int DEFAULT_SELLERS = 10;
    private static final int DEFAULT_BUYERS = 10;

    /**
     * Constructs a new JsonService with the given file name.
     *
     * @param fileName the name or path of the JSON file to work with
     */
    public JsonService(String fileName) {
        this.filePath = Path.of(fileName);
    }

    /**
     * Imports parameters from the JSON file.
     * Returns default values if the file is missing or invalid.
     *
     * @return a {@link ParamDTO} object with simulation parameters
     */
    public ParamDTO importJson() {
        if (!Files.exists(filePath)) {
            System.err.println("File '" + filePath + "' not found. Using defaults.");
            return new ParamDTO(DEFAULT_SELLERS, DEFAULT_BUYERS);
        }

        try {
            String content = Files.readString(filePath, StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(content);

            int sellers = json.getInt(KEYS[0]);
            int buyers = json.getInt(KEYS[1]);

            return new ParamDTO(sellers, buyers);

        } catch (IOException | JSONException e) {
            System.err.println("Failed to read '" + filePath + "': " + e.getMessage());
            return new ParamDTO(DEFAULT_SELLERS, DEFAULT_BUYERS);
        }
    }

    /**
     * Exports the given parameters to the JSON file.
     *
     * @param param the {@link ParamDTO} a data-transfer-object containing values to save
     */
    public void exportJson(ParamDTO param) {
        JSONObject json = new JSONObject();
        json.put(KEYS[0], param.numSellers());
        json.put(KEYS[1], param.numBuyers());

        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            writer.write(json.toString(4)); // pretty-print JSON
            System.out.println("Saved config to: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to write file '" + filePath + "': " + e.getMessage());
        }
    }
}
