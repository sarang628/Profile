package com.sarang.profile;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.sarang.profile.databinding.ItemProfileInfoBinding;

public class ProfileInfoHolder extends RecyclerView.ViewHolder {
    private ItemProfileInfoBinding itemProfileInfoBinding;
    private LifecycleOwner lifecycleOwner;

    public static ProfileInfoHolder create(ViewGroup parent, LifecycleOwner lifecycleOwner) {
        LayoutInflater inflator = LayoutInflater.from(parent.getContext());
        ItemProfileInfoBinding binding = ItemProfileInfoBinding.inflate(inflator, parent, false);
        return new ProfileInfoHolder(binding, lifecycleOwner);
    }

    public ProfileInfoHolder(@NonNull ItemProfileInfoBinding itemProfileInfoBinding, LifecycleOwner lifecycleOwner) {
        super(itemProfileInfoBinding.getRoot());
        this.itemProfileInfoBinding = itemProfileInfoBinding;
        itemProfileInfoBinding.setLifecycleOwner(lifecycleOwner);
    }

    public void setViewModel(ProfileViewModel profileViewModel) {
//        itemProfileInfoBinding.setViewModel(profileViewModel);
    }
}
