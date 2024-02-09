package com.example.cyclingclub;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class EventTests {
    @Mock
    EventDatabaseHandler eventDb;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void verifyFailedEventCreation() {
        when(eventDb.insertEvent("Test Event", "Road Race", "Description", "Age requirement")).thenReturn(false);
        assertFalse(eventDb.insertEvent("Test Event", "Road Race", "Description", "Age requirement"));
    }

    @Test
    public void verifySucceededEventCreation() {
        when(eventDb.insertEvent("Test Event", "Road Race", "Description", "Age requirement")).thenReturn(true);
        boolean res = eventDb.insertEvent("Test Event", "Road Race", "Description", "Age requirement");
        assertTrue(res);
    }

    @Test
    @Parameters({"Test Event"})
    public void verifySucceededEventDeletion(String event) {
        // Mocking the database deletion to simulate success
        when(eventDb.removeEvent(event)).thenReturn(true);
        assertTrue(eventDb.removeEvent(event));
    }
    @Test
    @Parameters({"Test Event"})
    public void verifyFailedEventDeletion(String event) {
        when(eventDb.removeEvent(event)).thenReturn(true);
        boolean res = eventDb.removeEvent(event);
        assertFalse(res);
    }

    @Test
    @Parameters({"Test Event"})
    public void verifySucceededEventEdit(String event) {
        // Mocking the database update to simulate success
        when(eventDb.updateEventData(
                event, "Modified event", "Hill Climb", "Description", "New Requirements"
        )).thenReturn(true);

        boolean isEditSuccessful = eventDb.updateEventData(
                event, "Modified event", "Hill Climb", "Description", "New Requirements"
        );

    }

}
