package com.owner.my_interapec;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecordListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<RowModel> recordlist;

    public RecordListAdapter(Context _context, int _layout, ArrayList<RowModel> _recordlist) {
        this.context = _context;
        this.layout = _layout;
        this.recordlist = _recordlist;
    }

    @Override
    public int getCount() {
        return recordlist.size();
    }

    @Override
    public Object getItem(int position) {
        return recordlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtName, txtDescription;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = new ViewHolder();

        if(row==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            holder.imageView = row.findViewById(R.id.imgIcon);
            holder.txtName = row.findViewById(R.id.tvfullname);
            holder.txtDescription = row.findViewById(R.id.tvfullphrase);
            row.setTag(holder);
        } else {
            holder = (ViewHolder)row.getTag();
        }

        RowModel model = recordlist.get(position);

        holder.txtName.setText(model.getName());
        holder.txtDescription.setText(model.getPhrase());

        byte[] recordimage = model.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(recordimage, 0, recordimage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
