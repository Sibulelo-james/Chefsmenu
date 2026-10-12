package com.example.chefsmenu;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chefsmenu.databinding.ItemMenuDishBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder> {

    public interface OnMenuItemActionListener {
        void onEdit(MenuItem item);
        void onDelete(MenuItem item);
    }

    private final Context context;
    private final List<MenuItem> menuItems = new ArrayList<>();
    private final OnMenuItemActionListener actionListener;

    public MenuItemAdapter(Context context, OnMenuItemActionListener actionListener) {
        this.context = context;
        this.actionListener = actionListener;
    }

    public void setItems(List<MenuItem> newItems) {
        this.menuItems.clear();
        if (newItems != null) {
            this.menuItems.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMenuDishBinding binding = ItemMenuDishBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new MenuItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
        MenuItem item = menuItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemMenuDishBinding binding;

        public MenuItemViewHolder(@NonNull ItemMenuDishBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MenuItem item) {
            binding.tvDishName.setText(item.getDishName());
            binding.tvDescription.setText(item.getDescription());
            binding.tvPrice.setText(String.format(Locale.getDefault(), "R %.2f", item.getPrice()));
            binding.tvCourseBadge.setText(item.getCourse());

            // Apply custom course pill badge styling based on course type
            int bgColorRes;
            int textColorRes;

            if ("Starter".equalsIgnoreCase(item.getCourse())) {
                bgColorRes = R.color.starter_bg;
                textColorRes = R.color.starter_text;
            } else if ("Main Course".equalsIgnoreCase(item.getCourse()) || "Main".equalsIgnoreCase(item.getCourse())) {
                bgColorRes = R.color.main_bg;
                textColorRes = R.color.main_text;
            } else { // Dessert
                bgColorRes = R.color.dessert_bg;
                textColorRes = R.color.dessert_text;
            }

            int bgColor = ContextCompat.getColor(context, bgColorRes);
            int textColor = ContextCompat.getColor(context, textColorRes);

            binding.tvCourseBadge.setBackgroundTintList(ColorStateList.valueOf(bgColor));
            binding.tvCourseBadge.setTextColor(textColor);

            binding.btnEdit.setOnClickListener(v -> {
                if (actionListener != null) {
                    actionListener.onEdit(item);
                }
            });

            binding.btnDelete.setOnClickListener(v -> {
                if (actionListener != null) {
                    actionListener.onDelete(item);
                }
            });
        }
    }
}