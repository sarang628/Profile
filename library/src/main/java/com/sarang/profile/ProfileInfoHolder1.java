package com.sarang.profile;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.sarang.profile.databinding.ItemProfileBinding;

public class ProfileInfoHolder1 extends RecyclerView.ViewHolder {
    private ItemProfileBinding itemProfileInfoBinding;
    private LifecycleOwner lifecycleOwner;

    public static ProfileInfoHolder1 create(ViewGroup parent, LifecycleOwner lifecycleOwner) {
        LayoutInflater inflator = LayoutInflater.from(parent.getContext());
        ItemProfileBinding binding = ItemProfileBinding.inflate(inflator, parent, false);
        return new ProfileInfoHolder1(binding, lifecycleOwner);
    }

    public ProfileInfoHolder1(@NonNull ItemProfileBinding itemProfileInfoBinding, LifecycleOwner lifecycleOwner) {
        super(itemProfileInfoBinding.getRoot());
        this.itemProfileInfoBinding = itemProfileInfoBinding;
        itemProfileInfoBinding.setLifecycleOwner(lifecycleOwner);
    }

    public void setViewModel(ProfileViewModel profileViewModel) {
        //itemProfileInfoBinding.setViewModel(profileViewModel);
    }
}
