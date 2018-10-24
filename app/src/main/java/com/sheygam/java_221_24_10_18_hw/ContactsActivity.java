package com.sheygam.java_221_24_10_18_hw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class ContactsActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView myList;
    private Button addBtn;
    private MyAdapter adapter;
    private StoreProvider store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        store = StoreProvider.getInstance();
        store.setContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        myList = findViewById(R.id.my_list);
        addBtn = findViewById(R.id.add_btn);
        addBtn.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Contact> list = store.getContacts();
        adapter = new MyAdapter(list,this);
        myList.setAdapter(adapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = (Contact) adapter.getItem(position);
                Intent intent = new Intent(ContactsActivity.this,InfoActivity.class);
                intent.putExtra("POS",position);
                intent.putExtra("MODE",0);
                intent.putExtra("CURRENT",contact);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.add_btn){
            Intent intent = new Intent(this,InfoActivity.class);
            intent.putExtra("MODE",1);
            startActivity(intent);
        }
    }
}
