package com.assignment.attendance;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
    Context context;
    ArrayList<ModelValues> list;
    public CustomListAdapter(Context context, ArrayList<ModelValues> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertview = inflater.inflate(R.layout.activity_adapter_configurer,null);
        TextView fnameList = (TextView) convertview.findViewById(R.id.first);
        TextView deptList = (TextView) convertview.findViewById(R.id.last);
        TextView dateList = (TextView) convertview.findViewById(R.id.dd);
        TextView timeList = (TextView) convertview.findViewById(R.id.dt);

        ModelValues modelValues = list.get(position);
        fnameList.setText(modelValues.getFirstname() +" "+ modelValues.getLastname());
        deptList.setText(modelValues.getDepartment());
        dateList.setText(modelValues.getDate());
        timeList.setText(modelValues.getTime());

        return convertview;
    }
}
