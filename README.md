# Chef's Menu Manager 🍽️

**Developer**: Sibulelo-james  
**Repository**: [https://github.com/Sibulelo-james/Chefsmenu](https://github.com/Sibulelo-james/Chefsmenu)

An intuitive, modern Android application developed in Java using Android Studio and Material Design 3. The Chef's Menu Manager app enables chefs and restaurant managers to efficiently create, manage, search, filter, and view statistics for menu items across multiple courses.

---

## 📱 Features Overview

### Part 2 — Core Application Requirements
- **User Interface (30 Marks)**:
  - Clear application header title: **Chef's Menu Manager**.
  - Built using official Material Design 3 components (`MaterialCardView`, `ExtendedFloatingActionButton`, `TextInputLayout`, `AutoCompleteTextView`, `ChipGroup`).
  - Consistent padding, elevation, typography, and color hierarchy.
- **Capture Menu Information (25 Marks)**:
  - Form dialog captures:
    - **Dish Name** (Text input)
    - **Description** (Multiline text input)
    - **Course** (Dropdown selection: Starter, Main Course, Dessert)
    - **Price** (Decimal currency input formatted in ZAR - R)
- **Display Menu Items (25 Marks)**:
  - High-performance `RecyclerView` listing all added dishes.
  - Distinct visual badges/tags for each course category (Starters, Main Courses, Desserts).
  - Immediate real-time UI updates upon adding menu items.
- **User Experience Enhancements (20 Marks)**:
  - Full input validation for required fields (Name, Description, Course, Price > 0).
  - Clear error hints on invalid input.
  - Interactive confirmation feedback via `Toast` and `MaterialAlertDialog`.
  - Empty state layout displayed when no items are present or match filter criteria.

---

### Final PoE — Enhanced Features
1. **Manage Menu Items (25 Marks)**:
   - **Edit Menu Item**: Tap the "Edit" button on any menu card to modify dish name, description, course, or price.
   - **Delete Menu Item**: Tap the "Delete" button to prompt a confirmation dialog before removing the item.
   - **Real-Time Sync**: Any changes made are saved immediately and reflected in the UI and statistics cards.

2. **Search and Filter Menu Items (20 Marks)**:
   - **Live Search**: Instant case-insensitive search by dish name as you type in the search bar.
   - **Course Filtering**: Filter dishes by category using single-select filter chips (**All**, **Starter**, **Main Course**, **Dessert**).
   - **Reset Filter/Search**: Quick clear icon and "All" chip option to easily restore the full list view.

3. **Display Menu Statistics (20 Marks)**:
   - Dashboard card displaying real-time metrics:
     - **Total Number of Menu Items**
     - **Average Price of All Menu Items** (Formatted to 2 decimal places, e.g., `R 135.00`)
     - **Course Breakdown Count** (Starters, Main Courses, Desserts count)

4. **Code Quality & Architecture Enhancements (20 Marks)**:
   - ViewBinding enabled for type-safe UI interaction.
   - Modular architecture (`MenuItem` data model, `MenuRepository` data source, `MenuItemAdapter` UI component, `AddEditDishDialogFragment` dialog view).
   - Data persistence using `SharedPreferences` with JSON serialization so created dishes persist across app restarts.
   - Pre-loaded sample items on initial app launch for immediate demonstration.

---

## 🛠️ Refactoring & Change Log (Since Part 2)

| Version / Phase | Feature / Refactoring Change | Description |
|---|---|---|
| **Part 2 Initial** | Core CRUD & UI | Implemented initial dish creation, list view, basic validation, and empty state screen. |
| **Final PoE Refactor** | Data Persistence | Integrated `SharedPreferences` + JSON serialization to persist dishes permanently across app sessions. |
| **Final PoE Refactor** | BottomSheet Dialog | Upgraded standard add/edit interface into a modern `BottomSheetDialogFragment` with live error clearing. |
| **Final PoE Feature** | Edit & Update | Added pre-populated editing capability for existing items with unique ID tracking (`UUID`). |
| **Final PoE Feature** | Safe Deletion | Added deletion confirmation dialog (`MaterialAlertDialogBuilder`) to prevent accidental removal. |
| **Final PoE Feature** | Real-Time Search & Filter | Implemented dynamic search text watching combined with single-selection `ChipGroup` filtering. |
| **Final PoE Feature** | Statistics Dashboard | Added real-time dynamic menu calculations (Total items, Average price, Course breakdown counts). |
| **Final PoE Code Quality** | Material Design 3 | Refactored UI layouts with custom course badge background drawables, consistent typography, and responsive spacing. |

---

## 🚀 How to Run the Application

1. Open the project folder in **Android Studio**.
2. Synchronize Gradle files (`Gradle Sync`).
3. Select an Android Emulator or physical Android device (API 24+).
4. Click **Run** (`Shift + F10` or `Ctrl + R`).
