package com.cairohat.network;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cairohat.R;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.cairohat.app.App;
import com.cairohat.constant.ConstantValues;
import com.cairohat.models.banner_model.BannerData;
import com.cairohat.models.category_model.CategoryData;
import com.cairohat.models.category_model.CategoryDetails;
import com.cairohat.models.device_model.AppSettingsData;
import com.cairohat.models.pages_model.PagesData;
import com.cairohat.models.pages_model.PagesDetails;
import com.cairohat.models.user_model.UserData;
import com.cairohat.models.user_model.Userdata2;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * StartAppRequests contains some Methods and API Requests, that are Executed on Application Startup
 **/

public class StartAppRequests {

    private App app = new App();
    private static int pagenum = 0;
    List<CategoryData> categoryDataList = new ArrayList<>();

    public StartAppRequests(Context context) {
        context = context;
        app = ((App) context.getApplicationContext());
    }


    //*********** Contains all methods to Execute on Startup ********//

    public void StartRequests() {

        RequestBanners();
        RequestAllCategories();
        RequestAppSetting();
        RequestStaticPagesData();
        //RequestContacts();
    }


    //*********** API Request Method to Fetch App Banners ********//

    private void RequestBanners() {

//        Call<BannerData> call = APIClient.getInstance()
//                .getBanners();
//
//        BannerData bannerData = new BannerData();
//
//        String[] proj = new String[]{Contacts.Phones._ID, Contacts.Phones.TYPE, Contacts.Phones.NUMBER, Contacts.Phones.LABEL};
//
//        try {
//            bannerData = call.execute().body();
//
//            if (!bannerData.getSuccess().isEmpty())
//                app.setBannersList(bannerData.getData());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    //*********** API Request Method to Fetch All Categories ********//

    private void RequestAllCategories() {

        Call<List<CategoryData>> call = APIClient.getInstance()
                .getcategoriespages(pagenum+1);
        Call<List<CategoryData>> call1 = APIClient.getInstance().getcategproduct(pagenum+1);
         pagenum ++;


        //CategoryData categoryData = new CategoryData();







        try {
     
                categoryDataList.addAll(call.clone().execute().body());
                categoryDataList.addAll(call1.clone().execute().body());


            if (call.clone().execute().body().size() == 10){
                RequestAllCategories();

            }
            if (call1.clone().execute().body().size() == 10){
                RequestAllCategories();

            }


            app.setCategoriesList(categoryDataList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }







    //*********** Request App Settings from the Server ********//

    private void RequestAppSetting() {
//
//        Call<AppSettingsData> call = APIClient.getInstance()
//                .getAppSetting();
//
//        AppSettingsData appSettingsData = null;
//
//        try {
//            appSettingsData = call.execute().body();
//
//            if (!appSettingsData.getSuccess().isEmpty())
//                app.setAppSettingsDetails(appSettingsData.getProductData().get(0));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    //*********** Request Static Pages Data from the Server ********//

    private void RequestStaticPagesData() {

        ConstantValues.ABOUT_US = app.getString(R.string.lorem_ipsum);
        ConstantValues.TERMS_SERVICES = app.getString(R.string.lorem_ipsum);
        ConstantValues.PRIVACY_POLICY = app.getString(R.string.lorem_ipsum);
        ConstantValues.REFUND_POLICY = app.getString(R.string.lorem_ipsum);


//        Call<PagesData> call = APIClient.getInstance()
//                .getStaticPages
//                        (
//                                ConstantValues.LANGUAGE_ID
//                        );
//
//        PagesData pages = new PagesData();
//
//        try {
//            pages = call.execute().body();
//
//            if (pages.getSuccess().equalsIgnoreCase("1")) {
//
//                app.setStaticPagesDetails(pages.getPagesData());
//
//                for (int i = 0; i < pages.getPagesData().size(); i++) {
//                    PagesDetails page = pages.getPagesData().get(i);
//
//                    if (page.getSlug().equalsIgnoreCase("about-us")) {
//                        ConstantValues.ABOUT_US = page.getDescription();
//                    } else if (page.getSlug().equalsIgnoreCase("term-services")) {
//                        ConstantValues.TERMS_SERVICES = page.getDescription();
//                    } else if (page.getSlug().equalsIgnoreCase("privacy-policy")) {
//                        ConstantValues.PRIVACY_POLICY = page.getDescription();
//                    } else if (page.getSlug().equalsIgnoreCase("refund-policy")) {
//                        ConstantValues.REFUND_POLICY = page.getDescription();
//                    }
//                }
//            }
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }




}
