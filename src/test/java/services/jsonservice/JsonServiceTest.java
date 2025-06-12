package services.jsonservice;

import dtos.ParamDTO;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class JsonServiceTest {
    //paramTest.json is used for only testing
    private final Path jsonPath = Path.of("src/main/resources/paramTest.json");
    private final JsonService jsonService = new JsonService("src/main/resources/paramTest.json");
    private String backupContent;

    @BeforeEach
    void backupAndDeleteJson() throws IOException {
        // Backup if file exists
        if (Files.exists(jsonPath)) {
            backupContent = Files.readString(jsonPath);
            Files.delete(jsonPath); // Simulate missing file
        }
    }

    @AfterEach
    void restoreJson() throws IOException {
        // Restore file if it was backed up
        if (backupContent != null) {
           Files.writeString(jsonPath, backupContent);
        }
    }


    @Test
    void importJson_ShouldReturnDefaults_IfFileMissing() {
        ParamDTO dto = jsonService.importJson();

        assertEquals(10, dto.numSellers());
        assertEquals(10, dto.numBuyers());
    }

    @Test
    void exportJson_ShouldCreateFile() {
        ParamDTO param = new ParamDTO(7, 8);
        jsonService.exportJson(param);

        assertTrue(Files.exists(jsonPath), "Expected file to be created");
    }

    @Test
    void exportThenImport_ShouldMatchOriginal() {
        ParamDTO expected = new ParamDTO(5, 15);

        jsonService.exportJson(expected);

        ParamDTO actual = jsonService.importJson();

        assertEquals(expected.numSellers(), actual.numSellers());
        assertEquals(expected.numBuyers(), actual.numBuyers());
    }
}
