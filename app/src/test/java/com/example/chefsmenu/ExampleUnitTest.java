package com.example.chefsmenu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class ExampleUnitTest {
    @Test
    public void menuItemUsesProvidedId() {
        MenuItem item = new MenuItem("dish-1", "Steak", "Description", "Main Course", 245.0);
        assertEquals("dish-1", item.getId());
    }

    @Test
    public void menuItemGeneratesUniqueId() {
        MenuItem first = new MenuItem("Soup", "Description", "Starter", 60.0);
        MenuItem second = new MenuItem("Salad", "Description", "Starter", 70.0);
        assertNotEquals(first.getId(), second.getId());
    }

    @Test
    public void menuItemEqualityUsesId() {
        MenuItem first = new MenuItem("dish-1", "Steak", "A", "Main Course", 245.0);
        MenuItem second = new MenuItem("dish-1", "Updated Steak", "B", "Main Course", 260.0);
        assertEquals(first, second);
    }
}