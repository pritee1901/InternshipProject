package com.pawan.onlinevegetabledelivery;

import android.os.Bundle;
import android.widget.*;
import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import android.os.Handler;

public class BillActivity extends AppCompatActivity {

    TextView tvSummary, tvFinalTotal;
    RadioGroup paymentGroup;
    Button btnPlaceOrder;

    int total = 0;
    double gst = 0;
    double delivery = 50; //  Delivery charges
    double finalTotal = 0;

    String summaryText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        tvSummary = findViewById(R.id.tvSummary);
        tvFinalTotal = findViewById(R.id.tvFinalTotal);
        paymentGroup = findViewById(R.id.paymentGroup);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        total = getIntent().getIntExtra("totalAmount", 0);

        StringBuilder summary = new StringBuilder();

        // ITEMS
        for (String item : CartManager.cartItems) {
            summary.append(item).append("\n");
        }

        //  CALCULATIONS
        gst = total * 0.06; // 6% GST
        finalTotal = total + gst + delivery;

        //  BILL FORMAT
        summary.append("\n----------------------\n");
        summary.append("Subtotal = ₹").append(total).append("\n");
        summary.append("GST (6%) = ₹").append(String.format("%.2f", gst)).append("\n");
        summary.append("Delivery Charges = ₹").append(delivery).append("\n");
        summary.append("----------------------\n");
        summary.append("Total Payable = ₹").append(String.format("%.2f", finalTotal));

        summaryText = summary.toString();

        tvSummary.setText(summaryText);
        tvFinalTotal.setText("Pay ₹" + String.format("%.2f", finalTotal));

        btnPlaceOrder.setOnClickListener(v -> {

            int selectedId = paymentGroup.getCheckedRadioButtonId();

            if (selectedId == -1) {
                Toast.makeText(this, "Select payment method", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedId == R.id.radioCOD) {
                placeOrder("COD");
            } else {
                startUPIPayment();
            }
        });
    }

    private void startUPIPayment() {
        Uri uri = Uri.parse("upi://pay?pa=7499319735@ibl&pn=VegetableShop&am="
                + String.format("%.2f", finalTotal) + "&cu=INR");

        Intent upiIntent = new Intent(Intent.ACTION_VIEW, uri);

        try {
            startActivityForResult(upiIntent, 1001);
        } catch (Exception e) {
            Toast.makeText(this, "No UPI app found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001) {

            if (data != null && data.getStringExtra("response") != null &&
                    data.getStringExtra("response").toLowerCase().contains("success")) {

                Toast.makeText(this, "Payment Successful ✅", Toast.LENGTH_SHORT).show();
                placeOrder("UPI");

            } else {
                showRetryDialog();
            }
        }
    }

    private void showRetryDialog() {

        new AlertDialog.Builder(this)
                .setTitle("Payment Failed ❌")
                .setMessage("Try again?")
                .setPositiveButton("Retry", (d, w) -> startUPIPayment())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void placeOrder(String paymentMethod) {

        String orderId = String.valueOf(System.currentTimeMillis());

        HashMap<String, Object> map = new HashMap<>();
        map.put("items", summaryText);
        map.put("total", finalTotal); //  FINAL TOTAL SAVE
        map.put("status", "Placed");
        map.put("payment", paymentMethod);
        map.put("delivery_status", "Preparing 🥬");

        FirebaseDatabase.getInstance()
                .getReference("orders")
                .child(orderId)
                .setValue(map);

        // AUTO STATUS UPDATE
        Handler handler = new Handler();

        handler.postDelayed(() ->
                updateStatus(orderId, "Packed 📦"), 5000);

        handler.postDelayed(() ->
                updateStatus(orderId, "Out for Delivery 🚚"), 10000);

        handler.postDelayed(() ->
                updateStatus(orderId, "Delivered ✅"), 20000);

        Toast.makeText(this, "Order Placed ✅", Toast.LENGTH_SHORT).show();

        CartManager.cartItems.clear();

        startActivity(new Intent(this, OrderPlacedActivity.class));
        finish();
    }

    private void updateStatus(String orderId, String status) {
        FirebaseDatabase.getInstance()
                .getReference("orders")
                .child(orderId)
                .child("delivery_status")
                .setValue(status);
    }
}