package com.pawan.onlinevegetabledelivery;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    ListView orderList;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        orderList = findViewById(R.id.orderList);

        list = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                list
        );

        orderList.setAdapter(adapter);

        ref = FirebaseDatabase.getInstance().getReference("orders");

        // 🔥 LOAD ORDERS
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot data : snapshot.getChildren()) {

                    String items = data.child("items").getValue(String.class);
                    String payment = data.child("payment").getValue(String.class);
                    String deliveryStatus = data.child("delivery_status").getValue(String.class);
                    Integer total = data.child("total").getValue(Integer.class);

                    String order = "";

                    if (items != null) {
                        order += "🧾 " + items + "\n";
                    }

                    if (total != null) {
                        order += "💰 ₹" + total + "\n";
                    }

                    if (payment != null) {
                        order += "💳 " + payment + "\n";
                    }

                    if (deliveryStatus != null) {
                        order += "📦 " + deliveryStatus + "\n";
                    }

                    order += "----------------------";

                    list.add(order);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(OrderHistoryActivity.this, "Error loading orders ❌", Toast.LENGTH_SHORT).show();
            }
        });
    }
}