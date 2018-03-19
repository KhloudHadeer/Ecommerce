package com.cairohat.databases;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.cairohat.models.cart_model.CartProduct;
import com.cairohat.models.cart_model.CartProductAttributes;
import com.cairohat.models.product_model.Image;
import com.cairohat.models.product_model.Option;
import com.cairohat.models.product_model.ProductData;
import com.cairohat.models.product_model.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 3/18/2018.
 */

public class Product_Fav_DB {
    SQLiteDatabase db;
    public static final String TABLE_SAVE                   = "products_fav";
    public static final String SAVE_ID                      = "save_id";
    public static final String SAVE_PRODUCT_ID              = "products_id";
    public static final String SAVE_PRODUCT_NAME            = "products_name";
    public static final String SAVE_PRODUCT_IMAGE           = "products_image";
    public static final String SAVE_PRODUCT_QUANTITY        = "products_quantity";
    public static final String SAVE_PRODUCT_PRICE           = "products_price";
    public static final String SAVE_PRODUCT_TOTAL_PRICE     = "products_total_price";
    public static final String SAVE_PRODUCT_FINAL_PRICE     = "products_final_price";
    public static final String SAVE_PRODUCT_CUS_QUANTITY    = "products_customer_quantity";
    public static final String SAVE_PRODUCT_CUSTOMER        = "products_customer";


    public static String createTableSave() {

        return "CREATE TABLE "+ TABLE_SAVE +
                "(" +
//                SAVE_ID                         + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SAVE_PRODUCT_ID                 + " INTEGER," +
                SAVE_PRODUCT_NAME               + " TEXT," +
                SAVE_PRODUCT_IMAGE              + " TEXT," +

                SAVE_PRODUCT_QUANTITY           + " INTEGER," +
                SAVE_PRODUCT_CUS_QUANTITY       + " INTEGER," +
                SAVE_PRODUCT_PRICE              + " TEXT," +

                SAVE_PRODUCT_FINAL_PRICE        + " TEXT," +
                SAVE_PRODUCT_TOTAL_PRICE        + " TEXT," +
                SAVE_PRODUCT_CUSTOMER           + " TEXT"  +

                ")";
    }



    public void addSaveItem(ProductData savedproduct , String customid) {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        ContentValues productValues = new ContentValues();
//        productValues.put(SAVE_ID,                                "");
        productValues.put(SAVE_PRODUCT_ID,                      savedproduct.getId());
        productValues.put(SAVE_PRODUCT_NAME,                    savedproduct.getName());
        //  productValues.put(CART_PRODUCT_IMAGE,                   cart.getCustomersBasketProduct().getProductsImage());
        //  productValues.put(CART_PRODUCT_URL,                     cart.getCustomersBasketProduct().getProductsUrl());
        // productValues.put(CART_PRODUCT_MODEL,                   cart.getCustomersBasketProduct().getProductsModel());
        // productValues.put(CART_PRODUCT_WEIGHT,                  cart.getCustomersBasketProduct().getProductsWeight());
        //productValues.put(CART_PRODUCT_WEIGHT_UNIT,             cart.getCustomersBasketProduct().getProductsWeightUnit());
        productValues.put(SAVE_PRODUCT_IMAGE,                   savedproduct.getImages().get(0).getImage());
        productValues.put(SAVE_PRODUCT_QUANTITY,                savedproduct.getStock_quantity());
        productValues.put(SAVE_PRODUCT_CUS_QUANTITY , savedproduct.getCustomerquantity());
        productValues.put(SAVE_PRODUCT_PRICE,                   savedproduct.getRegular_price());
        // productValues.put(CART_PRODUCT_ATTR_PRICE,              cart.getCustomersBasketProduct().getAttributesPrice());
        productValues.put(SAVE_PRODUCT_FINAL_PRICE,             savedproduct.getPrice());
        productValues.put(SAVE_PRODUCT_TOTAL_PRICE,             savedproduct.getPrice());
        productValues.put(SAVE_PRODUCT_CUSTOMER,                 customid  );
//        productValues.put(CART_PRODUCT_DESCRIPTION,             cart.getCustomersBasketProduct().getShort_description());
//        productValues.put(CART_CATEGORIES_ID,                   cart.getCustomersBasketProduct().getCategories().get(0).getId());
//        productValues.put(CART_CATEGORIES_NAME,                 cart.getCustomersBasketProduct().getCategories().get(0).getName());
//        //  productValues.put(CART_MANUFACTURERS_ID,                cart.getCustomersBasketProduct().getManufacturersId());
//        // productValues.put(CART_MANUFACTURERS_NAME,              cart.getCustomersBasketProduct().getManufacturersName());
//        // productValues.put(CART_PRODUCT_TAX_CLASS_ID,            cart.getCustomersBasketProduct().getProductsTaxClassId());
//        //  productValues.put(CART_TAX_DESCRIPTION,                 cart.getCustomersBasketProduct().getTaxDescription());
//        //productValues.put(CART_TAX_CLASS_TITLE,                 cart.getCustomersBasketProduct().getTaxClassTitle());
//        //productValues.put(CART_TAX_CLASS_DESCRIPTION,           cart.getCustomersBasketProduct().getTaxClassDescription());
//        // productValues.put(CART_PRODUCT_IS_SALE,                 cart.getCustomersBasketProduct().getIsSaleProduct());
//        productValues.put(CART_DATE_ADDED,                      Utilities.getDateTime());

         long insert = db.insert(TABLE_SAVE, null, productValues);
        System.out.println("insert "+insert);


//        int cartID = getLastCartID();
//
//
//        for (int i=0;  i<cart.getCustomersBasketProductAttributes().size();  i++)
//        {
//            CartProductAttributes cartAttributes = cart.getCustomersBasketProductAttributes().get(i);
//            Option option = cartAttributes.getOption();
//            Value value = cartAttributes.getValues().get(0);
//
//            ContentValues attributeValues = new ContentValues();
//
//            attributeValues.put(CART_PRODUCT_ID,                cart.getCustomersBasketProduct().getId());
//            attributeValues.put(ATTRIBUTE_OPTION_ID,            option.getId());
//            attributeValues.put(ATTRIBUTE_OPTION_NAME,          option.getName());
//            attributeValues.put(ATTRIBUTE_VALUE_ID,             value.getId());
//            attributeValues.put(ATTRIBUTE_VALUE_NAME,           value.getValue());
//            attributeValues.put(ATTRIBUTE_VALUE_PRICE,          value.getPrice());
//            attributeValues.put(ATTRIBUTE_VALUE_PRICE_PREFIX,   value.getPricePrefix());
//            attributeValues.put(CART_TABLE_ID,                  cartID);
//
//            db.insert(TABLE_CART_ATTRIBUTES, null, attributeValues);
//        }

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }






    public ArrayList<ProductData> getSavedItems(String customerid) {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        Cursor cursor =  db.rawQuery( "SELECT * FROM "+ TABLE_SAVE + " WHERE " + SAVE_PRODUCT_CUSTOMER + " = ?", new String[]{customerid}, null);

        ArrayList<ProductData> savelist = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
              //  CartProduct cart = new CartProduct();
                ProductData product = new ProductData();
//                String infd = cursor.getString(0);
               // String iybh = cursor.getString(1);

                product.setId(cursor.getInt(0));
                product.setName(cursor.getString(1));
                List<Image> images = new ArrayList<>();
                Image image = new Image();
                image.setImage(cursor.getString(2));
                images.add(image);
                product.setImages(images);
                // product.setProductsUrl(cursor.getString(4));
                //product.setProductsModel(cursor.getString(5));
                //product.setProductsWeight(cursor.getString(6));
                //product.setProductsWeightUnit(cursor.getString(7));
                product.setStock_quantity(cursor.getInt(3));
                product.setCustomerquantity(cursor.getInt(4));
                product.setRegular_price(cursor.getString(5));
                //  product.setAttributesPrice(cursor.getString(11));
                product.setPrice(cursor.getString(6));
                product.setPrice(cursor.getString(7));
               // product.setDescription(cursor.getString(14));
                //   product.setCategoriesId(cursor.getInt(15));
                //  product.setCategoriesName(cursor.getString(16));
                //  product.setManufacturersId(cursor.getInt(17));
                //  product.setManufacturersName(cursor.getString(18));
                //  product.setTaxClassId(cursor.getInt(19));
                //  product.setTaxDescription(cursor.getString(20));
                //  product.setTaxClassTitle(cursor.getString(21));
                //  product.setTaxClassDescription(cursor.getString(22));
                //  product.setIsSaleProduct(cursor.getString(23));





//                cart.setCustomersBasketId(cursor.getInt(0));
//                cart.setCustomersBasketDateAdded(cursor.getString(24));
//
//                cart.setCustomersBasketProduct(product);

                ///////////////////////////////////////////////////
//
//                List<CartProductAttributes> cartProductAttributesList = new ArrayList<>();
//
//                Cursor c =  db.rawQuery( "SELECT * FROM "+ TABLE_CART_ATTRIBUTES +" WHERE "+ CART_TABLE_ID +" = ?", new String[]{String.valueOf(cursor.getInt(0))});
//
//                if (c.moveToFirst()) {
//                    do {
//                        CartProductAttributes cartProductAttributes = new CartProductAttributes();
//                        Option option = new Option();
//                        Value value = new Value();
//                        List<Value> valuesList = new ArrayList<>();
//
//                        option.setId(c.getInt(1));
//                        option.setName(c.getString(2));
//                        value.setId(c.getInt(3));
//                        value.setValue(c.getString(4));
//                        value.setPrice(c.getString(5));
//                        value.setPricePrefix(c.getString(6));
//
//                        valuesList.add(value);
//
//                        cartProductAttributes.setProductsId(c.getString(0));
//                        cartProductAttributes.setOption(option);
//                        cartProductAttributes.setValues(valuesList);
//                        cartProductAttributes.setCustomersBasketId(c.getInt(7));
//
//                        cartProductAttributesList.add(cartProductAttributes);
//
//                    } while (c.moveToNext());
//                }
//
//                // close cursor
//                c.close();
//
//
//                cart.setCustomersBasketProductAttributes(cartProductAttributesList);
//
//                cartList.add(cart);
                savelist.add(product);
//
//
            } while (cursor.moveToNext());
        }

        // close cursor and DB
        cursor.close();
        DB_Manager.getInstance().closeDatabase();

        return savelist;
    }


    public void deleteSavedItem(int save_id) {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        db.delete(TABLE_SAVE, SAVE_PRODUCT_ID +" = ?", new String[]{String.valueOf(save_id)});
       // db.delete(TABLE_CART_ATTRIBUTES, CART_TABLE_ID +" = ?", new String[]{String.valueOf(cart_id)});

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }

}
