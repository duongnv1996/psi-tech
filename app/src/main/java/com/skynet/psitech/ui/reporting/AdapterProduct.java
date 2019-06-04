package com.skynet.psitech.ui.reporting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skynet.psitech.R;
import com.skynet.psitech.interfaces.ICallback;
import com.skynet.psitech.models.Booking;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {
    List<Booking> list;
    Context context;



    public AdapterProduct(List<Booking> list, Context context, ICallback iCallback) {
        this.list = list;
        this.context = context;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report, parent, false))
        ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTime.setText( list.get(position).getHour_working() + "-" + list.get(position).getRepeatString());
        holder.tvStatus.setText(String.format("+%,.0fđ",list.get(position).getPrice()));
        if(list.get(position).getService_image() != null && !list.get(position).getService_image().isEmpty()){
            Picasso.with(context).load(list.get(position).getService_image()).fit().centerCrop().into(holder.circleImageView4);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvStatus)
        TextView tvStatus;
        @BindView(R.id.circleImageView4)
        CircleImageView circleImageView4;
        @BindView(R.id.textView63)
        TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
