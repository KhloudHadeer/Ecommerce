package com.cairohat.network;


import com.cairohat.models.address_model.AddressData;
import com.cairohat.models.address_model.Countries;
import com.cairohat.models.address_model.Zones;
import com.cairohat.models.banner_model.BannerData;
import com.cairohat.models.category_model.CategoryData;
import com.cairohat.models.contact_model.ContactUsData;
import com.cairohat.models.device_model.AppSettingsData;
import com.cairohat.models.filter_model.get_filters.FilterData;
import com.cairohat.models.language_model.LanguageData;
import com.cairohat.models.news_model.all_news.NewsData;
import com.cairohat.models.news_model.news_categories.NewsCategoryData;
import com.cairohat.models.pages_model.PagesData;
import com.cairohat.models.product_model.GetAllProducts;
import com.cairohat.models.coupons_model.CouponsData;
import com.cairohat.models.payment_model.PaymentMethodsData;
import com.cairohat.models.product_model.ProductDetails;
import com.cairohat.models.shipping_model.PostTaxAndShippingData;
import com.cairohat.models.order_model.OrderData;
import com.cairohat.models.payment_model.GetBrainTreeToken;
import com.cairohat.models.order_model.PostOrder;
import com.cairohat.models.product_model.ProductData;
import com.cairohat.models.search_model.SearchData;
import com.cairohat.models.shipping_model.ShippingRateData;
import com.cairohat.models.user_model.UserData;
import com.cairohat.models.user_model.Userdata2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * APIRequests contains all the Network Request Methods with relevant API Endpoints
 **/

public interface APIRequests {


    //******************** User Data ********************//

    @FormUrlEncoded
    @POST("processRegistration")
    Call<UserData> processRegistration(     @Field("customers_firstname") String customers_firstname,
                                            @Field("customers_lastname") String customers_lastname,
                                            @Field("customers_email_address") String customers_email_address,
                                            @Field("customers_password") String customers_password,
                                            @Field("customers_telephone") String customers_telephone,
                                            @Field("customers_picture") String customers_picture);



    @POST("mobiconnector/user/register")
    @FormUrlEncoded
    Call<Void> Registeration(           @Field("first_name") String customers_firstname,
                                            @Field("last_name") String customers_lastname,
                                            @Field("username") String customers_username,
                                            @Field("password") String customers_password,
                                            @Field("email") String customers_email_address/*,
                                            @Field("customers_picture") String customers_picture*/);

    @FormUrlEncoded
    @POST("mobiconnector/jwt/token")
    Call<Userdata2> processLogin(            @Field("username") String customers_email_address,
                                            @Field("password") String customers_password );


    @FormUrlEncoded
    @POST("mobiconnector/user/get_info")
    Call<Userdata2> processProfile(            @Field("username") String customers_username );



    @GET("wooconnector/product/getbestsales")
    Call<List<ProductData>> getbestsales(@Query("post_num_page") int number);


    @GET("wooconnector/product/getcategories")
    Call<List<CategoryData>> getcategproduct(@Query("cat_num_page") int num);

//    @GET("wooconnector/product/getbestviews")
//    Call<> getbestreviews();


    @GET("listing_cat")
    Call<List<CategoryData>> getcategories();



    @GET("wp/v2/listing_cat")
    Call<List<CategoryData>> getcategoriespages(@Query("page") int pageno);

    @FormUrlEncoded
    @POST("facebookRegistration")
    Call<UserData> facebookRegistration(    @Field("access_token") String access_token);

    @FormUrlEncoded
    @POST("googleRegistration")
    Call<UserData> googleRegistration(      @Field("idToken") String idToken,
                                            @Field("userId") String userId,
                                            @Field("givenName") String givenName,
                                            @Field("familyName") String familyName,
                                            @Field("email") String email,
                                            @Field("imageUrl") String imageUrl);

    @FormUrlEncoded
    @POST("processForgotPassword")
    Call<UserData> processForgotPassword(   @Field("customers_email_address") String customers_email_address );

    @FormUrlEncoded
    @POST("updateCustomerInfo")
    Call<UserData> updateCustomerInfo(      @Field("customers_id") String customers_id,
                                            @Field("customers_firstname") String customers_firstname,
                                            @Field("customers_lastname") String customers_lastname,
                                            @Field("customers_telephone") String customers_telephone,
                                            @Field("customers_dob") String customers_dob,
                                            @Field("customers_picture") String customers_picture,
                                            @Field("customers_old_picture") String customers_old_picture,
                                            @Field("customers_password") String customers_password );


    @POST("mobiconnector/user/update_profile")
    @FormUrlEncoded
    Call<Userdata2> processUpdateProfile(
            @Header("Authorization") String customers_token,
            @Field("first_name") String customers_firstname,
            @Field("last_name") String customers_lastname,
            @Field("user_email") String customers_email,
            @Field("wiloke_address") String customers_address,
            @Field("wiloke_city") String customers_city,
            @Field("wiloke_zipcode") String customers_zipcode,
            @Field("wiloke_country") String customers_country,
            @Field("wiloke_phone") String customers_phone,
            @Field("user_pass") String customers_password,
            @Field("user_profile_picture") String customers_pic
    );


    //******************** Address Data ********************//
    
    @POST("getCountries")
    Call<Countries> getCountries();

    @FormUrlEncoded
    @POST("getZones")
    Call<Zones> getZones(                   @Field("zone_country_id") String zone_country_id);

    @FormUrlEncoded
    @POST("getAllAddress")
    Call<AddressData> getAllAddress(        @Field("customers_id") String customers_id);

    @FormUrlEncoded
    @POST("addShippingAddress")
    Call<AddressData> addUserAddress(       @Field("customers_id") String customers_id,
                                            @Field("entry_firstname") String entry_firstname,
                                            @Field("entry_lastname") String entry_lastname,
                                            @Field("entry_street_address") String entry_street_address,
                                            @Field("entry_postcode") String entry_postcode,
                                            @Field("entry_city") String entry_city,
                                            @Field("entry_country_id") String entry_country_id,
                                            @Field("entry_zone_id") String entry_zone_id,
                                            @Field("customers_default_address_id") String customers_default_address_id );

    @FormUrlEncoded
    @POST("updateShippingAddress")
    Call<AddressData> updateUserAddress(    @Field("customers_id") String customers_id,
                                            @Field("address_id") String address_id,
                                            @Field("entry_firstname") String entry_firstname,
                                            @Field("entry_lastname") String entry_lastname,
                                            @Field("entry_street_address") String entry_street_address,
                                            @Field("entry_postcode") String entry_postcode,
                                            @Field("entry_city") String entry_city,
                                            @Field("entry_country_id") String entry_country_id,
                                            @Field("entry_zone_id") String entry_zone_id,
                                            @Field("customers_default_address_id") String customers_default_address_id );

    @FormUrlEncoded
    @POST("updateDefaultAddress")
    Call<AddressData> updateDefaultAddress( @Field("customers_id") String customers_id,
                                            @Field("address_book_id") String address_book_id );

    @FormUrlEncoded
    @POST("deleteShippingAddress")
    Call<AddressData> deleteUserAddress(    @Field("customers_id") String customers_id,
                                            @Field("address_book_id") String address_book_id );

    

    //******************** Category Data ********************//

    @FormUrlEncoded
    @POST("allCategories")
    Call<CategoryData> getAllCategories(    @Field("language_id") int language_id);
    


    //******************** Product Data ********************//

    @POST("getAllProducts")
    Call<ProductData> getAllProducts(       @Body GetAllProducts getAllProducts);

    @GET("wooconnector/product/getproduct")
    Call<List<ProductData>> getallproduct(@Query("post_num_page") int num);


    @GET("wooconnector/product/getproductbycategory")
    Call<ProductDetails> getproductbycategory(@Query("post_category") int numcateg ,
                                              @Query("post_num_page") int numpage);


    @GET("wooconnector/product/getdealofday")
    Call<List<ProductData>> getproductsdeals(@Query("post_num_page") int numpage);


    @FormUrlEncoded
    @POST("likeProduct")
    Call<ProductData> likeProduct(          @Field("liked_products_id") int liked_products_id,
                                            @Field("liked_customers_id") String liked_customers_id );

    @FormUrlEncoded
    @POST("unlikeProduct")
    Call<ProductData> unlikeProduct(        @Field("liked_products_id") int liked_products_id,
                                            @Field("liked_customers_id") String liked_customers_id );
    

    @FormUrlEncoded
    @POST("getFilters")
    Call<FilterData> getFilters(            @Field("categories_id") int categories_id,
                                            @Field("language_id") int language_id);


    @FormUrlEncoded
    @POST("getSearchData")
    Call<SearchData> getSearchData(         @Field("searchValue") String searchValue,
                                            @Field("language_id") int language_id);


    
    //******************** News Data ********************//

    @FormUrlEncoded
    @POST("getAllNews")
    Call<NewsData> getAllNews(              @Field("language_id") int language_id,
                                            @Field("page_number") int page_number,
                                            @Field("is_feature") int is_feature,
                                            @Field("categories_id") String categories_id);

    @FormUrlEncoded
    @POST("allNewsCategories")
    Call<NewsCategoryData> allNewsCategories(@Field("language_id") int language_id,
                                            @Field("page_number") int page_number);



    //******************** Order Data ********************//
    
    @POST("addToOrder")
    Call<OrderData> addToOrder(             @Body PostOrder postOrder);

    @FormUrlEncoded
    @POST("getOrders")
    Call<OrderData> getOrders(              @Field("customers_id") String customers_id,
                                            @Field("language_id") int language_id);


    @FormUrlEncoded
    @POST("getCoupon")
    Call<CouponsData> getCouponInfo(        @Field("code") String code);


    @GET("getPaymentMethods")
    Call<PaymentMethodsData> getPaymentMethods();

    @GET("generateBraintreeToken")
    Call<GetBrainTreeToken> generateBraintreeToken();



    //******************** Banner Data ********************//

    @GET("getBanners")
    Call<BannerData> getBanners();


    
    //******************** Tax & Shipping Data ********************//

    @POST("getRate")
    Call<ShippingRateData> getShippingMethodsAndTax(
                                            @Body PostTaxAndShippingData postTaxAndShippingData);



    //******************** Contact Us Data ********************//

    @FormUrlEncoded
    @POST("contactUs")
    Call<ContactUsData> contactUs(          @Field("name") String name,
                                            @Field("email") String email,
                                            @Field("message") String message);


    
    //******************** Languages Data ********************//
    
    @GET("getLanguages")
    Call<LanguageData> getLanguages();


    
    //******************** App Settings Data ********************//

    @GET("siteSetting")
    Call<AppSettingsData> getAppSetting();
    
    
    
    //******************** Static Pages Data ********************//
    
    @FormUrlEncoded
    @POST("getAllPages")
    Call<PagesData> getStaticPages(         @Field("language_id") int language_id);
    
    
    
    //******************** Notifications Data ********************//
    
    @FormUrlEncoded
    @POST("registerDevices")
    Call<UserData> registerDeviceToFCM(     @Field("device_id") String device_id,
                                            @Field("device_type") String device_type,
                                            @Field("ram") String ram,
                                            @Field("processor") String processor,
                                            @Field("device_os") String device_os,
                                            @Field("location") String location,
                                            @Field("device_model") String device_model,
                                            @Field("manufacturer") String manufacturer,
                                            @Field("customers_id") String customers_id);


    @FormUrlEncoded
    @POST("notify_me")
    Call<ContactUsData> notify_me(          @Field("is_notify") String is_notify,
                                            @Field("device_id") String device_id);


    @FormUrlEncoded
    @POST("cronPeoples")
    Call<UserData> cronPeoples(             /*@Field("customers_id") String customers_id,*/
                                            @Field("customer_name") String customer_name,
                                            @Field("customer_number") String customer_number);
}

