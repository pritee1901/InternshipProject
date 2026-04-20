package com.pawan.onlinevegetabledelivery;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    // 🔥 ORDER SECTION
    ListView listOrders;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    DatabaseReference ref;

    ArrayList<String> orderIds;

    // 🔥 STOCK SECTION (13 ITEMS)
    EditText etPotato, etOnion, etGarlic, etTomato, etCabbage, etCauliflower, etBrinjal;
    EditText etChilli, etCapsicum, etCarrot, etSpinach, etCoriander, etGinger;

    Button btnUpdateStock;
    DatabaseReference stockRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // =========================
        // 🔥 ORDER SECTION
        // =========================
        listOrders = findViewById(R.id.listOrders);

        list = new ArrayList<>();
        orderIds = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                list
        );

        listOrders.setAdapter(adapter);

        ref = FirebaseDatabase.getInstance().getReference("orders");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                list.clear();
                orderIds.clear();

                for (DataSnapshot data : snapshot.getChildren()) {

                    String id = data.getKey();
                    String items = String.valueOf(data.child("items").getValue());
                    String total = String.valueOf(data.child("total").getValue());
                    String status = String.valueOf(data.child("delivery_status").getValue());

                    String orderText =
                            "Items:\n" + items +
                                    "\nTotal: ₹" + total +
                                    "\nStatus: " + status +
                                    "\n---------------------";

                    list.add(orderText);
                    orderIds.add(id);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(AdminActivity.this,"Error loading",Toast.LENGTH_SHORT).show();
            }
        });

        listOrders.setOnItemClickListener((parent, view, position, id) -> {

            String orderId = orderIds.get(position);

            String[] options = {
                    "Preparing 🥬",
                    "Out for Delivery 🚚",
                    "Delivered ✅"
            };

            new AlertDialog.Builder(this)
                    .setTitle("Update Status")
                    .setItems(options, (dialog, which) -> {

                        String selectedStatus = options[which];

                        ref.child(orderId)
                                .child("delivery_status")
                                .setValue(selectedStatus);

                        Toast.makeText(this,"Status Updated ✅",Toast.LENGTH_SHORT).show();
                    })
                    .show();
        });

        // =========================
        // 🔥 STOCK SECTION (FULL 13 ITEMS)
        // =========================
        etPotato = findViewById(R.id.etPotato);
        etOnion = findViewById(R.id.etOnion);
        etGarlic = findViewById(R.id.etGarlic);
        etTomato = findViewById(R.id.etTomato);
        etCabbage = findViewById(R.id.etCabbage);
        etCauliflower = findViewById(R.id.etCauliflower);
        etBrinjal = findViewById(R.id.etBrinjal);

        etChilli = findViewById(R.id.etChilli);
        etCapsicum = findViewById(R.id.etCapsicum);
        etCarrot = findViewById(R.id.etCarrot);

        etSpinach = findViewById(R.id.etSpinach);
        etCoriander = findViewById(R.id.etCoriander);
        etGinger = findViewById(R.id.etGinger);

        btnUpdateStock = findViewById(R.id.btnUpdateStock);

        stockRef = FirebaseDatabase.getInstance().getReference("stock");

        btnUpdateStock.setOnClickListener(v -> {

            stockRef.child("potato").setValue(getVal(etPotato));
            stockRef.child("onion").setValue(getVal(etOnion));
            stockRef.child("garlic").setValue(getVal(etGarlic));
            stockRef.child("tomato").setValue(getVal(etTomato));
            stockRef.child("cabbage").setValue(getVal(etCabbage));
            stockRef.child("cauliflower").setValue(getVal(etCauliflower));
            stockRef.child("brinjal").setValue(getVal(etBrinjal));

            stockRef.child("chilli").setValue(getVal(etChilli));
            stockRef.child("capsicum").setValue(getVal(etCapsicum));
            stockRef.child("carrot").setValue(getVal(etCarrot));

            stockRef.child("spinach").setValue(getVal(etSpinach));
            stockRef.child("coriander").setValue(getVal(etCoriander));
            stockRef.child("ginger").setValue(getVal(etGinger));

            Toast.makeText(this,"All Stock Updated ✅🔥",Toast.LENGTH_SHORT).show();
        });
    }

    // 🔥 HELPER FUNCTION
    private int getVal(EditText e){
        if(e.getText().toString().isEmpty()) return 0;
        return Integer.parseInt(e.getText().toString());
    }
}