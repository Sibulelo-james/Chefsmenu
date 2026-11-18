# Chef's Menu Manager 🍽️

**Developer**: Sibulelo-james  
**Repository**: [https://github.com/Sibulelo-james/Chefsmenu](https://github.com/Sibulelo-james/Chefsmenu)

Chef's Menu Manager is a modern, intuitive Android application designed for chefs and restaurant managers to seamlessly manage menu items across multiple courses. Built with Java and Material Design 3, the app offers complete menu management, real-time analytics, filtering, and persistent local storage.

---

## 🌟 Key Capabilities

- **Menu Management (CRUD)**: Create, view, edit, and delete dishes with real-time updates and input validation.
- **Search & Filtering**: Live case-insensitive search by dish name alongside course-based chip filters (Starter, Main Course, Dessert).
- **Statistics Dashboard**: Real-time summary showing total items, average price formatted in ZAR (R), and category breakdowns.
- **Data Persistence**: Offline storage using `SharedPreferences` with JSON serialization to preserve menu data across sessions.
- **Material Design 3 UI**: Clean user interface utilizing bottom sheets, cards, dynamic badges, and responsive layouts.

---

## 🛠️ Refactoring & Change Log

| Phase | Category | Description |
|---|---|---|
| **Part 2 Initial** | Core Application | Initial dish creation, list view, form validation, and empty state screen. |
| **Final PoE** | Data Persistence | Integrated `SharedPreferences` + JSON serialization for permanent offline data retention. |
| **Final PoE** | Modern UI | Refactored add/edit form into a modern `BottomSheetDialogFragment` with dynamic error handling. |
| **Final PoE** | Item Management | Added editing capability for existing items and deletion safety via `MaterialAlertDialogBuilder`. |
| **Final PoE** | Search & Filter | Added real-time text search and single-select course filtering chips. |
| **Final PoE** | Analytics | Added real-time dashboard calculations (Total items, average price, course breakdown). |
| **Final PoE** | Architecture | Standardized repository pattern, ViewBinding, and clean code formatting. |

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Jellyfish (2023.3.1) or newer
- JDK 17
- Android SDK API level 24 (Android 7.0) or higher

### Running the Project
1. Clone the repository:
   ```bash
   git clone https://github.com/Sibulelo-james/Chefsmenu.git
   ```
2. Open the project in **Android Studio**.
3. Sync project with Gradle files.
4. Select an emulator or physical device running API 24+.
5. Click **Run** (`Shift + F10`).
