package com1032.cw1.kf00084.kf00084_todolist.type;

import java.util.ArrayList;

/**
 * Created by Kastriot on 29/04/2015.
 */
public class Job {
    ArrayList<String> tArrayList;
    long tId;

    public Job(){}

    public void setArrayList(ArrayList<String> arrayList) {tArrayList = arrayList;}

    public long getId() {
        return tId;
    }

    public void setId(long tId) {
        this.tId = tId;
    }

    public String get(int loc){
        return tArrayList.get(loc);
    }

    public void set(int loc, String newString){
        tArrayList.set(loc, newString);
    }

    public int size(){
        return tArrayList.size();
    }

    public void add(String task){
        tArrayList.add(task);
    }

    public ArrayList<String> getArrayList(){

        return tArrayList;
    }
}
