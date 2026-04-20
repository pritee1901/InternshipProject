package com.pawan.onlinevegetabledelivery;

import android.os.Bundle;
import android.widget.*;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;

public class MainActivity extends AppCompatActivity {

    Button pPlus,pMinus,oPlus,oMinus,gPlus,gMinus;
    Button tPlus,tMinus,cPlus,cMinus,caPlus,caMinus,bPlus,bMinus;
    Button chPlus,chMinus,capPlus,capMinus,carPlus,carMinus;
    Button sPlus,sMinus,coPlus,coMinus,giPlus,giMinus;

    ImageButton btnAdmin, btnUser;

    TextView pQtyText,oQtyText,gQtyText;
    TextView tQtyText,cQtyText,caQtyText,bQtyText;
    TextView chQtyText,capQtyText,carQtyText;
    TextView sQtyText,coQtyText,giQtyText;
    TextView tvTotal;

    // 🔢 QUANTITY
    int pQty=0,oQty=0,gQty=0,tQty=0,cQty=0,caQty=0,bQty=0,chQty=0,capQty=0,carQty=0,sQty=0,coQty=0,giQty=0;

    // 🔥 STOCK FROM FIREBASE
    int pStock=0,oStock=0,gStock=0,tStock=0,cStock=0,caStock=0,bStock=0,chStock=0,capStock=0,carStock=0,sStock=0,coStock=0,giStock=0;

    // 💰 PRICE
    int pPrice=13,oPrice=15,gPrice=120,tPrice=20,cPrice=18,caPrice=25,bPrice=22,chPrice=40,capPrice=35,carPrice=30,sPrice=10,coPrice=10,giPrice=80;

    FirebaseDatabase database;
    DatabaseReference stockRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 🔥 ADD THIS AFTER setContentView(...)
        btnAdmin = findViewById(R.id.btnAdmin);
        btnUser = findViewById(R.id.btnUser);

// 🔥 CLICK LISTENER
        btnAdmin.setOnClickListener(v -> {
            Toast.makeText(this,"Admin Clicked",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, AdminLoginActivity.class));
        });

        btnUser.setOnClickListener(v -> {
            Toast.makeText(this,"User Clicked",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        });

        // 🔥 FIREBASE CONNECT
        database = FirebaseDatabase.getInstance("https://onlinevegetabledelivery-22a27-default-rtdb.asia-southeast1.firebasedatabase.app/");
        stockRef = database.getReference("stock");

        // 🔥 FETCH STOCK LIVE
        stockRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot s) {

                pStock = getValue(s,"potato");
                oStock = getValue(s,"onion");
                gStock = getValue(s,"garlic");

                tStock = getValue(s,"tomato");
                cStock = getValue(s,"cabbage");
                caStock = getValue(s,"cauliflower");
                bStock = getValue(s,"brinjal");

                chStock = getValue(s,"chilli");
                capStock = getValue(s,"capsicum");
                carStock = getValue(s,"carrot");

                sStock = getValue(s,"spinach");
                coStock = getValue(s,"coriander");
                giStock = getValue(s,"ginger");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivity.this,"Firebase Error",Toast.LENGTH_SHORT).show();
            }
        });

        // 🔘 NAVIGATION
        LinearLayout navAdd=findViewById(R.id.navAdd);
        LinearLayout navCart=findViewById(R.id.navCart);
        LinearLayout navOrders=findViewById(R.id.navOrders);
        LinearLayout navTrack=findViewById(R.id.navTrack);

        navAdd.setOnClickListener(v -> {

            StringBuilder order=new StringBuilder();

            if(pQty>0) order.append("Potato x "+pQty+"\n");
            if(oQty>0) order.append("Onion x "+oQty+"\n");
            if(gQty>0) order.append("Garlic x "+gQty+"\n");
            if(tQty>0) order.append("Tomato x "+tQty+"\n");
            if(cQty>0) order.append("Cabbage x "+cQty+"\n");
            if(caQty>0) order.append("Cauliflower x "+caQty+"\n");
            if(bQty>0) order.append("Brinjal x "+bQty+"\n");
            if(chQty>0) order.append("Chilli x "+chQty+"\n");
            if(capQty>0) order.append("Capsicum x "+capQty+"\n");
            if(carQty>0) order.append("Carrot x "+carQty+"\n");
            if(sQty>0) order.append("Spinach x "+sQty+"\n");
            if(coQty>0) order.append("Coriander x "+coQty+"\n");
            if(giQty>0) order.append("Ginger x "+giQty+"\n");

            if(order.length()==0){
                Toast.makeText(this,"Select items first",Toast.LENGTH_SHORT).show();
                return;
            }

            CartManager.cartItems.clear();
            CartManager.cartItems.add(order.toString());

            Toast.makeText(this,"Added to Cart ✅",Toast.LENGTH_SHORT).show();
        });

        navCart.setOnClickListener(v -> {
            Intent i=new Intent(this,BillActivity.class);
            i.putExtra("totalAmount",calculateTotal());
            startActivity(i);
        });

        navOrders.setOnClickListener(v -> startActivity(new Intent(this,OrderHistoryActivity.class)));
        navTrack.setOnClickListener(v -> startActivity(new Intent(this,DeliveryStatusActivity.class)));

        // 🔘 BUTTON INIT
        pPlus=findViewById(R.id.pPlus); pMinus=findViewById(R.id.pMinus);
        oPlus=findViewById(R.id.oPlus); oMinus=findViewById(R.id.oMinus);
        gPlus=findViewById(R.id.gPlus); gMinus=findViewById(R.id.gMinus);
        tPlus=findViewById(R.id.tPlus); tMinus=findViewById(R.id.tMinus);
        cPlus=findViewById(R.id.cPlus); cMinus=findViewById(R.id.cMinus);
        caPlus=findViewById(R.id.caPlus); caMinus=findViewById(R.id.caMinus);
        bPlus=findViewById(R.id.bPlus); bMinus=findViewById(R.id.bMinus);
        chPlus=findViewById(R.id.chPlus); chMinus=findViewById(R.id.chMinus);
        capPlus=findViewById(R.id.capPlus); capMinus=findViewById(R.id.capMinus);
        carPlus=findViewById(R.id.carPlus); carMinus=findViewById(R.id.carMinus);
        sPlus=findViewById(R.id.sPlus); sMinus=findViewById(R.id.sMinus);
        coPlus=findViewById(R.id.coPlus); coMinus=findViewById(R.id.coMinus);
        giPlus=findViewById(R.id.giPlus); giMinus=findViewById(R.id.giMinus);

        pQtyText=findViewById(R.id.pQty);
        oQtyText=findViewById(R.id.oQty);
        gQtyText=findViewById(R.id.gQty);
        tQtyText=findViewById(R.id.tQty);
        cQtyText=findViewById(R.id.cQty);
        caQtyText=findViewById(R.id.caQty);
        bQtyText=findViewById(R.id.bQty);
        chQtyText=findViewById(R.id.chQty);
        capQtyText=findViewById(R.id.capQty);
        carQtyText=findViewById(R.id.carQty);
        sQtyText=findViewById(R.id.sQty);
        coQtyText=findViewById(R.id.coQty);
        giQtyText=findViewById(R.id.giQty);

        tvTotal=findViewById(R.id.tvTotal);

        // 🔥 APPLY STOCK LIMIT LOGIC
        setStockListeners(pPlus,pMinus,pQtyText,0);
        setStockListeners(oPlus,oMinus,oQtyText,1);
        setStockListeners(gPlus,gMinus,gQtyText,2);
        setStockListeners(tPlus,tMinus,tQtyText,3);
        setStockListeners(cPlus,cMinus,cQtyText,4);
        setStockListeners(caPlus,caMinus,caQtyText,5);
        setStockListeners(bPlus,bMinus,bQtyText,6);
        setStockListeners(chPlus,chMinus,chQtyText,7);
        setStockListeners(capPlus,capMinus,capQtyText,8);
        setStockListeners(carPlus,carMinus,carQtyText,9);
        setStockListeners(sPlus,sMinus,sQtyText,10);
        setStockListeners(coPlus,coMinus,coQtyText,11);
        setStockListeners(giPlus,giMinus,giQtyText,12);
    }

    private int getValue(DataSnapshot s,String key){
        Integer val=s.child(key).getValue(Integer.class);
        return val==null?0:val;
    }

    private int getStock(int t){
        switch(t){
            case 0:return pStock; case 1:return oStock; case 2:return gStock;
            case 3:return tStock; case 4:return cStock; case 5:return caStock;
            case 6:return bStock; case 7:return chStock; case 8:return capStock;
            case 9:return carStock; case 10:return sStock; case 11:return coStock;
            case 12:return giStock;
        }
        return 0;
    }

    private void setStockListeners(Button plus,Button minus,TextView txt,int type){
        plus.setOnClickListener(v -> {
            if(getQty(type) < getStock(type)){
                increase(type);
                txt.setText(""+getQty(type));
                updateTotal();
            } else {
                Toast.makeText(this,"Out of Stock ❌",Toast.LENGTH_SHORT).show();
            }
        });

        minus.setOnClickListener(v -> {
            if(getQty(type)>0){
                decrease(type);
                txt.setText(""+getQty(type));
                updateTotal();
            }
        });
    }

    private void increase(int t){
        switch(t){
            case 0:pQty++;break; case 1:oQty++;break; case 2:gQty++;break;
            case 3:tQty++;break; case 4:cQty++;break; case 5:caQty++;break;
            case 6:bQty++;break; case 7:chQty++;break; case 8:capQty++;break;
            case 9:carQty++;break; case 10:sQty++;break; case 11:coQty++;break;
            case 12:giQty++;break;
        }
    }

    private void decrease(int t){
        switch(t){
            case 0:pQty--;break; case 1:oQty--;break; case 2:gQty--;break;
            case 3:tQty--;break; case 4:cQty--;break; case 5:caQty--;break;
            case 6:bQty--;break; case 7:chQty--;break; case 8:capQty--;break;
            case 9:carQty--;break; case 10:sQty--;break; case 11:coQty--;break;
            case 12:giQty--;break;
        }
    }

    private int getQty(int t){
        switch(t){
            case 0:return pQty; case 1:return oQty; case 2:return gQty;
            case 3:return tQty; case 4:return cQty; case 5:return caQty;
            case 6:return bQty; case 7:return chQty; case 8:return capQty;
            case 9:return carQty; case 10:return sQty; case 11:return coQty;
            case 12:return giQty;
        }
        return 0;
    }

    private int calculateTotal(){
        return (pQty*pPrice)+(oQty*oPrice)+(gQty*gPrice)+
                (tQty*tPrice)+(cQty*cPrice)+(caQty*caPrice)+
                (bQty*bPrice)+(chQty*chPrice)+(capQty*capPrice)+
                (carQty*carPrice)+(sQty*sPrice)+(coQty*coPrice)+(giQty*giPrice);
    }

    private void updateTotal(){
        tvTotal.setText("Total = ₹"+calculateTotal());
    }
}