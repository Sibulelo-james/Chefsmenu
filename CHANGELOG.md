# Final PoE — Change Log

## Implemented
- Edit existing menu items with pre-filled form fields
- Delete menu items with a confirmation dialog
- Persistent menu storage using SharedPreferences and JSON
- Live dish-name search and course filter chips
- Real-time total, average price, and course breakdown statistics
- Empty state for no dishes or no search/filter matches
- Material 3 bottom-sheet add/edit form with validation
- Locale-safe search and improved RecyclerView DiffUtil updates
- Input limits for dish names and descriptions
- Unit tests for menu item IDs and equality

## Refactoring Since Part 2
- Improved repository filtering and course matching
- Centralized CRUD behavior in MenuRepository
- Updated RecyclerView to efficiently calculate item changes
- Strengthened form validation for supported courses and valid prices

## How to Demonstrate
1. Add a dish using the extended FAB.
2. Edit it from the card action button.
3. Search by name and filter by course.
4. Observe real-time statistics updates.
5. Delete a dish and confirm the dialog.
6. Close and reopen the app to verify persistence.
