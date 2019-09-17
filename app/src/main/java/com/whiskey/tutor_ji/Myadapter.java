package com.whiskey.tutor_ji;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Myadapter extends ArrayAdapter<stateVO> {
    private Context mContext;
    private ArrayList<stateVO> listState;
    private Myadapter myAdapter;
    private boolean isFromView = false;
    private int arr[];
//    int arr[] ={0,0,0, 0,0,0, 0,0,0, 0,0,0};

    public Myadapter(Context context, int resource, List<stateVO> objects, int a[]) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<stateVO>) objects;
        this.myAdapter = this;
        this.arr = a;

    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_item, null);
            holder = new ViewHolder();
            holder.mTextView = (TextView) convertView
                    .findViewById(R.id.text);
            holder.mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(listState.get(position).getTitle());

        // To check weather checked event fire from getview() or user input
        isFromView = true;
        holder.mCheckBox.setChecked(listState.get(position).isSelected());
        isFromView = false;

        if ((position == 0)) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }
        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();

                if(isChecked)
                {
                    if(getPosition<13&&getPosition>0) {
                        arr[getPosition - 1] = 1;
                        Toast.makeText(mContext, getPosition + " " + listState.get(position).getTitle()+"a", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    if(getPosition<13&&getPosition>0) {
                        arr[getPosition - 1] = 0;
                        Toast.makeText(mContext, getPosition + " " + listState.get(position).getTitle()+"b", Toast.LENGTH_SHORT).show();
                    }
                }
//                Toast.makeText(mContext, ""+listState.get(position).getTitle(), Toast.LENGTH_SHORT).show();

                if (!isFromView) {
                    listState.get(position).setSelected(isChecked);
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }
}
