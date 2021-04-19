package com.altkamul.smarttask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<JSONObject> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context = null;

    MyRecyclerViewAdapter(Context context, List<JSONObject> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JSONObject Character = mData.get(position);
        String characterName = "";
        String species = "";
        String imgUrl;
        try {
            characterName = Character.getString("name");
            species = Character.getString("species");
            imgUrl = Character.getString("image");
            holder.characterName.setText(characterName);
            holder.species.setText(species);
            Glide.with(context).load(imgUrl).into(holder.characterImg);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView characterName;
        TextView species;
        ImageView characterImg = null;
        ViewHolder(View itemView) {
            super(itemView);
            characterName = itemView.findViewById(R.id.nameTxtId);
            species = itemView.findViewById(R.id.speciesTxtId);
            characterImg = itemView.findViewById(R.id.characterImgId);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
