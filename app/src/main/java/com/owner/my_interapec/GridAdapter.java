package com.owner.my_interapec;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GridAdapter extends BaseAdapter {
    Context context;
    private final String[] values;
    private final int[] images;
    View view;
    LayoutInflater layoutInflater;

    public GridAdapter(Context context, String[] values, int[] images) {
        this.context = context;
        this.values = values;
        this.images = images;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Holder holder = new Holder();
        if(convertView == null) {
            view = new View(context);
            view = layoutInflater.inflate(R.layout.single_item,null);
            holder.img = (ImageView) view.findViewById(R.id.imgView);
            holder.tv = (TextView) view.findViewById(R.id.txtView);
            holder.img.setImageResource(images[position]);
            holder.tv.setText(values[position]);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position) {
                        case 0:
                            Intent a = new Intent(v.getContext(), Galeria.class);
                            v.getContext().startActivity(a);
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        return view;
    }

    public class Holder {
        TextView tv;
        ImageView img;
    }
}
