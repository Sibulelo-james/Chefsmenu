package com.example.chefsmenu;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.chefsmenu.databinding.ActivityMainBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MenuItemAdapter.OnMenuItemActionListener {

    private ActivityMainBinding binding;
    private MenuRepository menuRepository;
    private MenuItemAdapter menuItemAdapter;

    private String currentSearchQuery = "";
    private String currentCourseFilter = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        menuRepository = new MenuRepository(this);

        setupToolbar();
        setupRecyclerView();
        setupSearchAndFilter();
        setupAddDishFab();

        refreshUI();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
    }

    private void setupRecyclerView() {
        menuItemAdapter = new MenuItemAdapter(this, this);
        binding.rvMenuItems.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMenuItems.setAdapter(menuItemAdapter);
    }

    private void setupSearchAndFilter() {
        // Search text watcher
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentSearchQuery = s != null ? s.toString() : "";
                refreshUI();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Filter chips selection
        binding.chipGroupCourse.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) {
                currentCourseFilter = "All";
            } else {
                int checkedId = checkedIds.get(0);
                if (checkedId == R.id.chipStarter) {
                    currentCourseFilter = "Starter";
                } else if (checkedId == R.id.chipMain) {
                    currentCourseFilter = "Main Course";
                } else if (checkedId == R.id.chipDessert) {
                    currentCourseFilter = "Dessert";
                } else {
                    currentCourseFilter = "All";
                }
            }
            refreshUI();
        });
    }

    private void setupAddDishFab() {
        binding.fabAddDish.setOnClickListener(v -> showAddEditDialog(null));
    }

    private void showAddEditDialog(MenuItem itemToEdit) {
        AddEditDishDialogFragment dialog = AddEditDishDialogFragment.newInstance(itemToEdit);
        dialog.setOnDishSavedListener((item, isEdit) -> {
            if (isEdit) {
                menuRepository.updateMenuItem(item);
                Toast.makeText(this, R.string.msg_dish_updated, Toast.LENGTH_SHORT).show();
            } else {
                menuRepository.addMenuItem(item);
                Toast.makeText(this, R.string.msg_dish_added, Toast.LENGTH_SHORT).show();
            }
            refreshUI();
        });
        dialog.show(getSupportFragmentManager(), "AddEditDishDialog");
    }

    @Override
    public void onEdit(MenuItem item) {
        showAddEditDialog(item);
    }

    @Override
    public void onDelete(MenuItem item) {
        String confirmMessage = getString(R.string.delete_confirm_msg, item.getDishName());
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.delete_confirm_title)
                .setMessage(confirmMessage)
                .setNegativeButton(R.string.btn_cancel, null)
                .setPositiveButton(R.string.btn_delete, (dialog, which) -> {
                    menuRepository.deleteMenuItem(item.getId());
                    Toast.makeText(this, R.string.msg_dish_deleted, Toast.LENGTH_SHORT).show();
                    refreshUI();
                })
                .show();
    }

    private void refreshUI() {
        // Update Filtered List
        List<MenuItem> filteredList = menuRepository.filterAndSearch(currentSearchQuery, currentCourseFilter);
        menuItemAdapter.setItems(filteredList);

        // Update Empty State
        if (filteredList.isEmpty()) {
            binding.rvMenuItems.setVisibility(View.GONE);
            binding.layoutEmptyState.setVisibility(View.VISIBLE);
        } else {
            binding.rvMenuItems.setVisibility(View.VISIBLE);
            binding.layoutEmptyState.setVisibility(View.GONE);
        }

        // Update Statistics
        binding.tvTotalItems.setText(String.valueOf(menuRepository.getTotalItemsCount()));
        binding.tvAvgPrice.setText(String.format(Locale.getDefault(), "R %.2f", menuRepository.getAveragePrice()));

        int starters = menuRepository.getCourseCount("Starter");
        int mains = menuRepository.getCourseCount("Main Course");
        int desserts = menuRepository.getCourseCount("Dessert");

        binding.tvStartersCount.setText(String.format(Locale.getDefault(), "Starters: %d", starters));
        binding.tvMainsCount.setText(String.format(Locale.getDefault(), "Mains: %d", mains));
        binding.tvDessertsCount.setText(String.format(Locale.getDefault(), "Desserts: %d", desserts));
    }
}