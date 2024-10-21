package com.websarva.wings.android.asyncsample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MeibutuAdapter extends ArrayAdapter<MeibutuItem> {

    private Context context;
    private int resource;
    private List<MeibutuItem> items;

    public MeibutuAdapter(@NonNull Context context, int resource, @NonNull List<MeibutuItem> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        MeibutuItem item = items.get(position);

        TextView nameTextView = convertView.findViewById(R.id.meibutuNameTextView);
        TextView descriptionTextView = convertView.findViewById(R.id.meibutuDescriptionTextView);

        nameTextView.setText(item.getName());
        descriptionTextView.setText(item.getDescription());

        return convertView;
    }
}

