package com.example.chefsmenu;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.chefsmenu.databinding.DialogAddEditDishBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddEditDishDialogFragment extends BottomSheetDialogFragment {

    private static final String ARG_MENU_ITEM = "arg_menu_item";

    public interface OnDishSavedListener {
        void onDishSaved(MenuItem menuItem, boolean isEdit);
    }

    private DialogAddEditDishBinding binding;
    private MenuItem existingItem;
    private OnDishSavedListener listener;

    public static AddEditDishDialogFragment newInstance(@Nullable MenuItem menuItem) {
        AddEditDishDialogFragment fragment = new AddEditDishDialogFragment();
        if (menuItem != null) {
            Bundle args = new Bundle();
            args.putSerializable(ARG_MENU_ITEM, menuItem);
            fragment.setArguments(args);
        }
        return fragment;
    }

    public void setOnDishSavedListener(OnDishSavedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            existingItem = (MenuItem) getArguments().getSerializable(ARG_MENU_ITEM);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(d -> {
            BottomSheetDialog bsd = (BottomSheetDialog) d;
            View bottomSheet = bsd.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogAddEditDishBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Populate course dropdown options
        String[] courses = new String[]{
                getString(R.string.course_starter),
                getString(R.string.course_main),
                getString(R.string.course_dessert)
        };
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, courses);
        binding.actvCourse.setAdapter(courseAdapter);

        // Pre-fill fields if editing
        if (existingItem != null) {
            binding.tvDialogTitle.setText(R.string.edit_dish_title);
            binding.etDishName.setText(existingItem.getDishName());
            binding.etDescription.setText(existingItem.getDescription());
            binding.actvCourse.setText(existingItem.getCourse(), false);
            binding.etPrice.setText(String.valueOf(existingItem.getPrice()));
            binding.btnSave.setText(R.string.btn_save);
        } else {
            binding.tvDialogTitle.setText(R.string.add_dish_title);
        }

        // Text change listeners to clear error states when typing
        setupClearErrorListeners();

        binding.btnCancel.setOnClickListener(v -> dismiss());
        binding.btnSave.setOnClickListener(v -> validateAndSave());
    }

    private void setupClearErrorListeners() {
        TextWatcher clearErrorWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.tilDishName.setError(null);
                binding.tilDescription.setError(null);
                binding.tilCourse.setError(null);
                binding.tilPrice.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        binding.etDishName.addTextChangedListener(clearErrorWatcher);
        binding.etDescription.addTextChangedListener(clearErrorWatcher);
        binding.actvCourse.addTextChangedListener(clearErrorWatcher);
        binding.etPrice.addTextChangedListener(clearErrorWatcher);
    }

    private void validateAndSave() {
        String dishName = binding.etDishName.getText() != null ? binding.etDishName.getText().toString().trim() : "";
        String description = binding.etDescription.getText() != null ? binding.etDescription.getText().toString().trim() : "";
        String course = binding.actvCourse.getText() != null ? binding.actvCourse.getText().toString().trim() : "";
        String priceStr = binding.etPrice.getText() != null ? binding.etPrice.getText().toString().trim() : "";

        boolean isValid = true;

        if (dishName.isEmpty()) {
            binding.tilDishName.setError(getString(R.string.error_dish_name_required));
            isValid = false;
        } else if (dishName.length() > 60) {
            binding.tilDishName.setError(getString(R.string.error_dish_name_too_long));
            isValid = false;
        }

        if (description.isEmpty()) {
            binding.tilDescription.setError(getString(R.string.error_description_required));
            isValid = false;
        } else if (description.length() > 300) {
            binding.tilDescription.setError(getString(R.string.error_description_too_long));
            isValid = false;
        }

        boolean validCourse = course.equals(getString(R.string.course_starter))
                || course.equals(getString(R.string.course_main))
                || course.equals(getString(R.string.course_dessert));
        if (!validCourse) {
            binding.tilCourse.setError(getString(R.string.error_course_required));
            isValid = false;
        }

        double price = 0.0;
        if (priceStr.isEmpty()) {
            binding.tilPrice.setError(getString(R.string.error_invalid_price));
            isValid = false;
        } else {
            try {
                price = Double.parseDouble(priceStr);
                if (!Double.isFinite(price) || price <= 0 || price > 100000) {
                    binding.tilPrice.setError(getString(R.string.error_invalid_price));
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                binding.tilPrice.setError(getString(R.string.error_invalid_price));
                isValid = false;
            }
        }

        if (!isValid) {
            return;
        }

        boolean isEdit = existingItem != null;
        MenuItem resultItem;
        if (isEdit) {
            resultItem = new MenuItem(existingItem.getId(), dishName, description, course, price);
        } else {
            resultItem = new MenuItem(dishName, description, course, price);
        }

        if (listener != null) {
            listener.onDishSaved(resultItem, isEdit);
        }

        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}