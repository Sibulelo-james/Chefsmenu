package com.example.chefsmenu;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MenuRepository {
    private static final String PREF_NAME = "ChefsMenuPrefs";
    private static final String KEY_MENU_ITEMS = "menu_items_json";
    private static final String KEY_INITIALIZED = "is_initialized";

    private final SharedPreferences preferences;
    private final List<MenuItem> menuItemsList;

    public MenuRepository(Context context) {
        preferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        menuItemsList = new ArrayList<>();
        loadFromPreferences();

        // Populate sample data on first run
        if (!preferences.getBoolean(KEY_INITIALIZED, false)) {
            populateInitialSampleData();
            preferences.edit().putBoolean(KEY_INITIALIZED, true).apply();
            saveToPreferences();
        }
    }

    private void populateInitialSampleData() {
        menuItemsList.add(new MenuItem(
                "Caprese Salad",
                "Fresh mozzarella, ripe tomatoes, basil, and balsamic reduction drizzled with olive oil.",
                "Starter",
                85.00
        ));
        menuItemsList.add(new MenuItem(
                "Grilled Ribeye Steak",
                "300g grass-fed beef served with garlic butter, rosemary potato wedges and grilled vegetables.",
                "Main Course",
                245.00
        ));
        menuItemsList.add(new MenuItem(
                "Chocolate Lava Cake",
                "Warm chocolate cake with molten center, served with Madagascar vanilla ice cream.",
                "Dessert",
                75.00
        ));
    }

    private void loadFromPreferences() {
        menuItemsList.clear();
        String jsonString = preferences.getString(KEY_MENU_ITEMS, null);
        if (jsonString != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    MenuItem item = new MenuItem(
                            obj.getString("id"),
                            obj.getString("dishName"),
                            obj.getString("description"),
                            obj.getString("course"),
                            obj.getDouble("price")
                    );
                    menuItemsList.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveToPreferences() {
        try {
            JSONArray jsonArray = new JSONArray();
            for (MenuItem item : menuItemsList) {
                JSONObject obj = new JSONObject();
                obj.put("id", item.getId());
                obj.put("dishName", item.getDishName());
                obj.put("description", item.getDescription());
                obj.put("course", item.getCourse());
                obj.put("price", item.getPrice());
                jsonArray.put(obj);
            }
            preferences.edit().putString(KEY_MENU_ITEMS, jsonArray.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<MenuItem> getAllMenuItems() {
        return new ArrayList<>(menuItemsList);
    }

    public void addMenuItem(MenuItem item) {
        menuItemsList.add(0, item); // Add at top
        saveToPreferences();
    }

    public void updateMenuItem(MenuItem updatedItem) {
        for (int i = 0; i < menuItemsList.size(); i++) {
            if (menuItemsList.get(i).getId().equals(updatedItem.getId())) {
                menuItemsList.set(i, updatedItem);
                saveToPreferences();
                return;
            }
        }
    }

    public void deleteMenuItem(String id) {
        menuItemsList.removeIf(item -> item.getId().equals(id));
        saveToPreferences();
    }

    public List<MenuItem> filterAndSearch(String query, String courseFilter) {
        List<MenuItem> filteredList = new ArrayList<>();
        String normalizedQuery = (query != null) ? query.trim().toLowerCase(java.util.Locale.ROOT) : "";
        boolean filterCourse = courseFilter != null && !courseFilter.equalsIgnoreCase("All");

        for (MenuItem item : menuItemsList) {
            boolean matchesName = normalizedQuery.isEmpty()
                    || item.getDishName().toLowerCase(java.util.Locale.ROOT).contains(normalizedQuery);
            boolean matchesCourse = !filterCourse
                    || item.getCourse().equalsIgnoreCase(courseFilter);

            if (matchesName && matchesCourse) {
                filteredList.add(item);
            }
        }
        return filteredList;
    }

    // Statistics methods
    public int getTotalItemsCount() {
        return menuItemsList.size();
    }

    public double getAveragePrice() {
        if (menuItemsList.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (MenuItem item : menuItemsList) {
            sum += item.getPrice();
        }
        return sum / menuItemsList.size();
    }

    public int getCourseCount(String courseName) {
        int count = 0;
        for (MenuItem item : menuItemsList) {
            if (item.getCourse().equalsIgnoreCase(courseName)) {
                count++;
            }
        }
        return count;
    }
}