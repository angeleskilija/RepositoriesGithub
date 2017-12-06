package com.example.ilijaangeleski.repositoriesgithub.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ilijaangeleski.repositoriesgithub.R;
import com.example.ilijaangeleski.repositoriesgithub.model.GitRepo;
import com.example.ilijaangeleski.repositoriesgithub.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ilija Angeleski on 12/6/2017.
 */

public class RepositoryRecyclerViewAdapter extends RecyclerView.Adapter<RepositoryRecyclerViewAdapter.MyViewHolder> {
    static List<GitRepo> items;
    private Context context;
    private int layoutResourceId;
    private OnUserItemClick listener;

    public RepositoryRecyclerViewAdapter(List<GitRepo> items, Context context, int layoutResourceId) {
        this.items = items;
        this.context = context;
        this.layoutResourceId = layoutResourceId;
    }

    public interface OnUserItemClick {
        void onRepositoryClick(GitRepo user, ImageView profileImage);
    }

    @Override
    public RepositoryRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutResourceId, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RepositoryRecyclerViewAdapter.MyViewHolder holder, int position) {
        final GitRepo current = items.get(position);
        holder.repositoryName.setText(current.getName());
        holder.description.setText(current.getDescription());
        holder.numberForks.setText(current.getForks());
        Picasso.with(context).load(current.getOwner().getAvatar_url())
                .transform(new CircleTransform()).placeholder(R.mipmap.ic_profile).error(R.mipmap.ic_profile)
                .into(holder.avatar);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onRepositoryClick(current, holder.avatar);
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.repositoryName)
        TextView repositoryName;
        @BindView(R.id.numberForks)
        TextView numberForks;
        @BindView(R.id.description)
        TextView description;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static List<GitRepo> getItems() {
        return items;
    }

    public void setOnUserItemClick(OnUserItemClick listener) {
        this.listener = listener;
    }
}