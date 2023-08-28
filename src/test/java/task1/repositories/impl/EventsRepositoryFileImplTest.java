package task1.repositories.impl;

import org.junit.jupiter.api.*;
import task1.models.Event;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EventsRepositoryFileImpl is works ...")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
class EventsRepositoryFileImplTest {

    private static final String TEMP_EVENTS_FILE_NAME = "events_test.txt";

    private EventsRepositoryFileImpl eventsRepository;

    @BeforeEach
    public void setUp() throws Exception {

        createNewFileForTest(TEMP_EVENTS_FILE_NAME);

        eventsRepository = new EventsRepositoryFileImpl(TEMP_EVENTS_FILE_NAME);
    }

    @AfterEach
    public void tearDown() throws Exception {
        deleteFileAfterTest(TEMP_EVENTS_FILE_NAME);
    }

    @DisplayName("save():")
    @Nested
    class Save {

        @Test
        public void writes_correct_line_to_file() throws Exception {
            Event event = new Event("new event", LocalDate.of(2023, 9, 01), LocalDate.of(2023, 9, 02));

            eventsRepository.save(event);

            String expected = "1|new event|2023-09-01|2023-09-02";

            BufferedReader reader = new BufferedReader(new FileReader(TEMP_EVENTS_FILE_NAME));

            String actual = reader.readLine();

            reader.close();

            assertEquals(expected, actual);
        }
    }

    @DisplayName("findAll():")
    @Nested
    class FindAll {

        @Test
        public void returns_correct_list_of_users() throws Exception {
            BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_EVENTS_FILE_NAME));

            writer.write("1|new event|2023-09-01|2023-09-02");
            writer.newLine();
            writer.write("2|new year|2023-12-31|2024-01-01");
            writer.newLine();
            writer.close();

            List<Event> expected = Arrays.asList(
                    new Event("new event", LocalDate.of(2023, 9, 01), LocalDate.of(2023, 9, 02)),
                    new Event("new year", LocalDate.of(2023, 12, 31), LocalDate.of(2024, 1, 01))
            );

            List<Event> actual = eventsRepository.findAll();

            assertEquals(expected, actual);
        }
    }

    private static void createNewFileForTest(String fileName) throws IOException {

        File file = new File(fileName);

        deleteIfExists(file);

        boolean result = file.createNewFile();

        if (!result) {
            throw new IllegalStateException("Problems with file create");
        }
    }

    private static void deleteFileAfterTest(String fileName) {
        File file = new File(fileName);

        deleteIfExists(file);
    }

    private static void deleteIfExists(File file) {
        if (file.exists()) {
            boolean result = file.delete();

            if (!result) {
                throw new IllegalStateException("Problems with file delete");
            }
        }
    }
}