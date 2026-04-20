package com.pawan.onlinevegetabledelivery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ListView cartList;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    DatabaseReference ref;
    Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartList = findViewById(R.id.cartList);
        btnCheckout = findViewById(R.id.btnCheckout);

        list = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                list
        );

        cartList.setAdapter(adapter);

        // 🔥 Firebase Reference
        ref = FirebaseDatabase.getInstance().getReference("cart");

        // 🔥 FETCH DATA (FIXED)
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                list.clear();

                if (snapshot.exists()) {

                    for (DataSnapshot data : snapshot.getChildren()) {

                        String item = data.getValue(String.class);

                        if (item != null && !item.trim().isEmpty()) {
                            list.add(item);
                        }
                    }

                } else {
                    Toast.makeText(CartActivity.this, "Cart Empty 🛒", Toast.LENGTH_SHORT).show();
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(CartActivity.this, "Error loading data", Toast.LENGTH_SHORT).show();
            }
        });

        // 🔥 DELETE ITEM (IMPROVED)
        cartList.setOnItemLongClickListener((parent, view, position, id) -> {

            String item = list.get(position);

            ref.orderByValue().equalTo(item)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            for (DataSnapshot data : snapshot.getChildren()) {
                                data.getRef().removeValue();
                            }

                            Toast.makeText(CartActivity.this, "Item Deleted ❌", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {}
                    });

            return true;
        });

        // 🔥 CHECKOUT BUTTON
        btnCheckout.setOnClickListener(v -> {
            if (list.isEmpty()) {
                Toast.makeText(this, "Cart is Empty!", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(CartActivity.this, BillActivity.class));
            }
        });
    }
}