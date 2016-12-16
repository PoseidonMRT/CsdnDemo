package com.example.tuozhaobing.recyclerdatabinding;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuozhaobing.recyclerdatabinding.bean.User;
import com.example.tuozhaobing.recyclerdatabinding.databinding.RecyclerItemBinding;

import java.util.ArrayList;

/**
 * Created by tuozhaobing on 16/12/16.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {


    private ArrayList<User> list;

    public UserAdapter(ArrayList<User> list) {
        this.list = list;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View statusContainer = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new UserAdapter.ViewHolder(statusContainer);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User status = list.get(position);
        holder.bindUser(status);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerItemBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void bindUser(User user) {
            binding.setUser(user);
        }
    }
}
