package com.owner.my_interapec;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CardListAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<CardItem> cardList;

    public CardListAdapter(Context context, int layout, ArrayList<CardItem> cardList) {
        this.context = context;
        this.layout = layout;
        this.cardList = cardList;
    }

    @Override
    public int getCount() {
        return cardList.size();
    }

    @Override
    public Object getItem(int position) {
        return cardList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtnombre;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);


            holder.txtnombre = (TextView)row.findViewById(R.id.tvnombre);
            holder.imageView = (ImageView)row.findViewById(R.id.imgthumbnail);
            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        final CardItem item = cardList.get(position);

        holder.txtnombre.setText(item.getName());

        byte[] itemimage = item.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(itemimage,0, itemimage.length);
        holder.imageView.setImageBitmap(bitmap);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardItem itemData = cardList.get(position);
               // Toast.makeText(v.getContext(), itemData.getName(),Toast.LENGTH_SHORT).show();
                Intent itemAct = new Intent(v.getContext(),ItemActivity.class);
                String idStr = String.valueOf(item.getId());

                itemAct.putExtra("itemId",idStr);
                v.getContext().startActivity(itemAct);
            }
        });

        return row;
    }
}
