package com.example.appreforma4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProformaAdapter extends RecyclerView.Adapter<ProformaAdapter.ViewHolder> {
    private List<ProformaItem> itemList;

    public ProformaAdapter(List<ProformaItem> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proforma, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProformaItem item = itemList.get(position);
        String text = String.format("ID: %s | %s | S/. %.2f | Cant: %d | Total: S/. %.2f",
                item.getCodigo(), item.getProducto(), item.getPrecio(), item.getCantidad(), item.getTotal());
        holder.tvItemInfo.setText(text);
    }

    @Override
    public int getItemCount() { return itemList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemInfo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemInfo = itemView.findViewById(R.id.tvItemInfo);
        }
    }
}
