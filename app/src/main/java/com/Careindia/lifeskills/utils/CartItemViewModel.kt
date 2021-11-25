package com.careindia.lifeskills.utils

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField

//class CartItemViewModel(
//    var cartItem: CartItem,
//                        val name: ObservableField<String> = ObservableField(cartItem.name),
//                        val description: ObservableField<String> = ObservableField(cartItem.description),
//                        val image: ObservableField<Drawable> = ObservableField(cartItem.image),
//                        val price: ObservableField<Double> = ObservableField(cartItem.quantifiedPrice),
//                        val quantity: ObservableField<Int> = ObservableField(cartItem.quantity),
//                        val quantityEntries: ObservableField<List<Int>> = ObservableField(cartItem.maxQuantity.populateList())
//) {
//
//    fun update(cartItem: CartItem) {
//        this.cartItem = cartItem
//        name.set(cartItem.name)
//        description.set(cartItem.description)
//        image.set(cartItem.image)
//        price.set(cartItem.quantifiedPrice)
//        quantity.set(cartItem.quantity)
//        quantityEntries.set(cartItem.maxQuantity.populateList())
//    }
//}