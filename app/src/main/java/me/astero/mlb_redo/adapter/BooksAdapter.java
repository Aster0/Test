package me.astero.mlb_redo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import me.astero.mlb_redo.R;
import me.astero.mlb_redo.database.Database;
import me.astero.mlb_redo.database.data.BookData;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {



    private List<BookData> dataset;

    public BooksAdapter(List<BookData> dataset)
    {
        this.dataset = dataset;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_books, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {


        holder.title.setText(dataset.get(position).name);
        holder.author.setText(dataset.get(position).author);

        Glide.with(holder.itemView).load(


                Database.IP_ADDRESS + dataset.get(position).imageUrl).into(holder.icon);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView title, author;
        public ImageView icon;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);


            title = itemView.findViewById(R.id.bookTitle);
            author = itemView.findViewById(R.id.bookAuthor);
            icon = itemView.findViewById(R.id.bookIcon);
        }





    }
}
