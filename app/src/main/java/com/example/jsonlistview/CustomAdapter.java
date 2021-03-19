package com.example.jsonlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context applicationContext;
    int sample;
    List<DemoStudent> s;

    public CustomAdapter(Context applicationContext, int sample, List<DemoStudent> s) {
        this.applicationContext = applicationContext;
        this.sample = sample;
        this.s = s;

    }

    @Override
    public int getCount() {
        return s.size();
    }

    @Override
    public Object getItem(int position) {
        return s.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.sample,parent,false);
        }

        TextView name,dept,country;

        name = convertView.findViewById(R.id.nameID);
        dept = convertView.findViewById(R.id.deptID);
        country = convertView.findViewById(R.id.countryID);

        name.setText(s.get(position).getName());
        dept.setText(s.get(position).getDepartment());
        country.setText(s.get(position).getCountry());


        return convertView;
    }
}
