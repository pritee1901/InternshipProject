package com.pawan.onlinevegetabledelivery;

import java.util.ArrayList;

public class CartManager {

    public static ArrayList<String> cartItems = new ArrayList<>();

    // ADD ITEM
    public static void addItem(String item) {
        cartItems.add(item);
    }

    // GET ALL ITEMS
    public static ArrayList<String> getItems() {
        return cartItems;
    }

    // REMOVE ITEM
    public static void removeItem(int position) {
        cartItems.remove(position);
    }
}