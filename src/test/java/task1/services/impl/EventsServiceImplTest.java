package task1.services.impl;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import task1.models.Event;
import task1.repositories.EventsRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("EventsServiceImpl is works ...")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
class EventsServiceImplTest {

    private static final String EXIST_EVENT_TITLE = "new event";
    private static final String NOT_EXIST_EVENT_TITLE = "new year";
    private static final LocalDate DEFAULT_START_DATE = LocalDate.of(2023, 9, 3);
    private static final LocalDate DEFAULT_END_DATE = LocalDate.of(2023, 9, 4);
    private static final Event NOT_EXIST_EVENT = new Event(NOT_EXIST_EVENT_TITLE, DEFAULT_START_DATE, DEFAULT_END_DATE);
    private static final Event EXIST_EVENT = new Event(EXIST_EVENT_TITLE, DEFAULT_START_DATE, DEFAULT_END_DATE);


    private EventsServiceImpl eventsService;

    private EventsRepository eventsRepository;

    @BeforeEach
    public void setUp() {
        eventsRepository = Mockito.mock(EventsRepository.class);

        when(eventsRepository.findOneByTitle(EXIST_EVENT_TITLE)).thenReturn(EXIST_EVENT);
        when(eventsRepository.findOneByTitle(NOT_EXIST_EVENT_TITLE)).thenReturn(null);
        this.eventsService = new EventsServiceImpl(eventsRepository);
    }

    @Nested
    @DisplayName(("addEvent():"))
    class AddUser {
        @Test
        public void on_empty_title_throws_exception() {
            assertThrows(IllegalArgumentException.class, () -> eventsService.addEvent(" ", DEFAULT_START_DATE, DEFAULT_END_DATE));
        }

        @Test
        public void on_incorrect_dates_throws_exception() {
            assertThrows(RuntimeException.class, () -> eventsService.addEvent("new event", LocalDate.of(2023, 9, 4), LocalDate.of(2023, 9, 3)));
        }

        @Test
        public void returns_created_event() {
            Event actual = eventsService.addEvent(NOT_EXIST_EVENT_TITLE, DEFAULT_START_DATE, DEFAULT_END_DATE);

            verify(eventsRepository).save(NOT_EXIST_EVENT);

            assertNotNull(actual);
        }
    }

}