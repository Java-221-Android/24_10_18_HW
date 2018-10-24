package com.sheygam.java_221_24_10_18_hw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView nameTxt, emailTxt, phoneTxt,addressTxt;
    private EditText inputName, inputEmail, inputPhone,inputAddress;
    private Contact current;
    private boolean isEdited = false;
    private ViewGroup editWrapper, viewWrapper;
    private Button saveBtn, editBtn;
    private int currentPos = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        nameTxt = findViewById(R.id.name_txt);
        emailTxt = findViewById(R.id.email_txt);
        phoneTxt = findViewById(R.id.phone_txt);
        addressTxt = findViewById(R.id.address_txt);

        inputName = findViewById(R.id.input_name);
        inputEmail = findViewById(R.id.input_email);
        inputPhone = findViewById(R.id.input_phone);
        inputAddress = findViewById(R.id.input_address);

        editWrapper = findViewById(R.id.edit_container);
        viewWrapper = findViewById(R.id.view_container);

        saveBtn = findViewById(R.id.save_btn);
        editBtn = findViewById(R.id.edit_btn);

        saveBtn.setOnClickListener(this);
        editBtn.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras.getInt("MODE") == 1){
            current = new Contact("","","","");
            showEditMode();
        }else{
            current = (Contact) extras.getSerializable("CURRENT");
            currentPos = extras.getInt("POS");
            showViewMode();
        }

    }

    private void showViewMode() {
        editWrapper.setVisibility(View.GONE);
        nameTxt.setText(current.getName());
        emailTxt.setText(current.getEmail());
        phoneTxt.setText(current.getPhone());
        addressTxt.setText(current.getAddress());
        viewWrapper.setVisibility(View.VISIBLE);
    }

    private void showEditMode() {
        if(currentPos >= 0){
            isEdited = true;
        }
        inputName.setText(current.getName());
        inputEmail.setText(current.getEmail());
        inputPhone.setText(current.getPhone());
        inputAddress.setText(current.getAddress());
        viewWrapper.setVisibility(View.GONE);
        editWrapper.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.edit_btn){
            showEditMode();
        }else if(v.getId() == R.id.save_btn){

            current.setName(inputName.getText().toString());
            current.setEmail(inputEmail.getText().toString());
            current.setPhone(inputPhone.getText().toString());
            current.setAddress(inputAddress.getText().toString());
            if(current.getName().isEmpty() || current.getEmail().isEmpty() || current.getPhone().isEmpty()||current.getAddress().isEmpty()){
                Toast.makeText(this, "Fill all fields!", Toast.LENGTH_SHORT).show();
            }else {
                if (isEdited) {
                    StoreProvider.getInstance().updateContact(current, currentPos);
                } else {
                    StoreProvider.getInstance().addContact(current);
                }
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(isEdited){
            showViewMode();
            isEdited = false;
        }else {
            super.onBackPressed();
        }
    }
}
