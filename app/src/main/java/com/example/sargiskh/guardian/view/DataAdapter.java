package com.example.sargiskh.guardian.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sargiskh.guardian.R;
import com.example.sargiskh.guardian.model.Results;
import com.example.sargiskh.guardian.utils.Constants;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataAdapterViewHolder> {

    private OnBottomReachedListener onBottomReachedListener;
    private List<Results> results;
    private Context context;

    public DataAdapter(OnBottomReachedListener onBottomReachedListener) {
        this.onBottomReachedListener = onBottomReachedListener;
        this.results = Collections.emptyList();
    }

    @NonNull
    @Override
    public DataAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_recycler_view, parent, false);
        return new DataAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapterViewHolder holder, int position) {
        holder.bindPeople(results.get(position));
        if (position == results.size() - 3){
            onBottomReachedListener.onBottomReached(position);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<Results> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public class DataAdapterViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textViewPillarName;
        private TextView textViewWebTitle;
        private TextView textViewWebPublicationDate;

        public DataAdapterViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewPillarName = itemView.findViewById(R.id.textViewPillarName);
            textViewWebTitle = itemView.findViewById(R.id.textViewWebTitle);
            textViewWebPublicationDate = itemView.findViewById(R.id.textViewWebPublicationDate);
        }

        public void bindPeople(final Results result) {

            Picasso.get().load(getCoverImageResourceIdByPillarName(result.pillarName))
                    .resize(80, 80)
                    .centerCrop()
                    .transform(new CropCircleTransformation())
                    .into(imageView);
            textViewPillarName.setText(result.pillarName);
            textViewWebTitle.setText(result.webTitle);
            textViewWebPublicationDate.setText(formatDateTime(result.webPublicationDate));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(result.webUrl));
                    context.startActivity(intent);
                }
            });
        }

        // Datetime formatter
        // Out date format more user friendly
        private String formatDateTime(String input) {
            SimpleDateFormat sdfIn = new SimpleDateFormat(Constants.DATE_TIME_TIME_ZONE_FORMATTER);
            sdfIn.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date dateIn = new Date();
            try {
                dateIn = sdfIn.parse(input);
            } catch (ParseException e) {
            }
            SimpleDateFormat sdfOut = new SimpleDateFormat(Constants.DATE_TIME_FORMATTER, Locale.getDefault());
            return sdfOut.format(dateIn);
        }

        // NOTE
        // In free version it is not possible to get articles' image, that is why I just added local images to decorate the list
        private int getCoverImageResourceIdByPillarName(String pillarName) {

            if (pillarName != null)
                pillarName = pillarName.toLowerCase();
            else
                pillarName = "";

            switch (pillarName) {
                case "arts":
                    return R.drawable.arts;
                case "news":
                    return R.drawable.news;
                case "sport":
                    return R.drawable.sport;
                case "opinion":
                    return R.drawable.opinion;
                case "lifestyle":
                    return R.drawable.lifestyle;
                default:
                    return R.drawable.unknown;
            }
        }

    }
}
