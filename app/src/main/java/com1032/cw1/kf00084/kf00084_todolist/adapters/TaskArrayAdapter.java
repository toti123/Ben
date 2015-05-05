package com1032.cw1.kf00084.kf00084_todolist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com1032.cw1.kf00084.kf00084_todolist.R;

/**
 * Created by Kastriot on 24/04/2015.
 */
public class TaskArrayAdapter extends ArrayAdapter<String> {


    Context tContext;
    ArrayList<String> tArrayList;


    public TaskArrayAdapter(Context context, ArrayList<String> arrayList) {
        super(context, R.layout.task_list, arrayList);
        tContext = context;
        tArrayList = arrayList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(tContext).inflate(R.layout.task_list, parent, false);
            holder = new ViewHolder();

            holder.taskName = (TextView) convertView.findViewById(R.id.task_text);
            convertView.setTag(holder);

        }
        else{
            holder =  (ViewHolder) convertView.getTag();
        }

        holder.taskName.setText(tArrayList.get(position));

        return convertView;
    }

        public static class ViewHolder {
            TextView taskName;

        }
    }
