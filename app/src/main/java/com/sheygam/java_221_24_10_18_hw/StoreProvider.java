package com.sheygam.java_221_24_10_18_hw;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class StoreProvider {
    private static final StoreProvider ourInstance = new StoreProvider();
    private Context context;
    private String current = "User1";

    public static StoreProvider getInstance() {
        return ourInstance;
    }

    private StoreProvider() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void addContact(Contact contact){
//        List<Contact> list = getContacts();
//        list.add(contact);
//        saveContacts(list);
        SharedPreferences sp = context.getSharedPreferences("CONTACTS",Context.MODE_PRIVATE);
        String str = sp.getString(current,null);
        if(str == null){
            str = contact.toString();
        }else{
            str += ";" + contact.toString();
        }
        sp.edit().putString(current,str).apply();
    }

    public List<Contact> getContacts(){
        List<Contact> list = new ArrayList<>();
        SharedPreferences sp = context.getSharedPreferences("CONTACTS",Context.MODE_PRIVATE);
        String str = sp.getString(current,null);
        if(str != null) {
            String[] cntcs = str.split(";");
            for (int i = 0; i < cntcs.length; i++) {
                list.add(Contact.newInstance(cntcs[i]));
            }
        }
        return list;
    }

    public void saveContacts(List<Contact> list){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            str.append(list.get(i).toString());
            if(i < list.size()-1){
                str.append(";");
            }
        }
        SharedPreferences sp = context.getSharedPreferences("CONTACTS",Context.MODE_PRIVATE);
        sp.edit().putString(current,str.toString()).apply();
    }

    public void updateContact(Contact c, int pos){
        List<Contact> list = getContacts();
        list.set(pos,c);
        saveContacts(list);
    }

}
