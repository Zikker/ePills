package devs.erasmus.epills.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;

import java.util.Calendar;
import java.util.List;

import devs.erasmus.epills.R;
import devs.erasmus.epills.model.IntakeMoment;

/**
 * Created by colla on 06/11/2017.
 */

public class PillCardAdapter extends RecyclerView.Adapter<PillCardAdapter.MyViewHolder> {
    private Context mContext;
    private List<IntakeMoment> pillList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            count = view.findViewById(R.id.count);
            thumbnail = view.findViewById(R.id.thumbnail);
            overflow = view.findViewById(R.id.overflow);
        }
    }


    public PillCardAdapter(Context mContext, List<IntakeMoment> pillList) {
        this.mContext = mContext;
        this.pillList = pillList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pill_cards, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        IntakeMoment medicineQuantity = pillList.get(position);

        String info_pill = mContext.getString(R.string.pill_info_card,String.valueOf(medicineQuantity.getQuantity()) ,
                String.valueOf(medicineQuantity.getStartDate().getHours()),
                String.format("%02d",  medicineQuantity.getStartDate().getMinutes()));


        holder.title.setText(medicineQuantity.getMedicine().getName());
        holder.count.setText(info_pill);

        // loading album cover using Glide library
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.pill_placeholder);
        requestOptions.error(R.drawable.pill_placeholder);
        requestOptions.centerCrop();

        Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(medicineQuantity.getMedicine().getImage()).into(holder.thumbnail);


        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "HI", Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    public int getItemCount() {
        return pillList.size();
    }
}
