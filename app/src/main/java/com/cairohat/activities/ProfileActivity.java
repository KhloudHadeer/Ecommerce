package com.cairohat.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.DrawableWrapper;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.cairohat.R;
import com.cairohat.adapters.DrawerExpandableListAdapter;
import com.cairohat.app.App;
import com.cairohat.app.MyAppPrefsManager;
import com.cairohat.constant.ConstantValues;
import com.cairohat.customs.CircularImageView;
import com.cairohat.customs.DialogLoader;
import com.cairohat.customs.LocaleHelper;
import com.cairohat.customs.NotificationBadger;
import com.cairohat.databases.User_Info_DB;
import com.cairohat.fragments.About;
import com.cairohat.fragments.Categories_1;
import com.cairohat.fragments.Categories_3;
import com.cairohat.fragments.Categories_4;
import com.cairohat.fragments.Categories_5;
import com.cairohat.fragments.Categories_6;
import com.cairohat.fragments.ContactUs;
import com.cairohat.fragments.HomePage_1;
import com.cairohat.fragments.HomePage_2;
import com.cairohat.fragments.HomePage_3;
import com.cairohat.fragments.HomePage_4;
import com.cairohat.fragments.HomePage_5;
import com.cairohat.fragments.Languages;
import com.cairohat.fragments.My_Addresses;
import com.cairohat.fragments.My_Cart;
import com.cairohat.fragments.My_Orders;
import com.cairohat.fragments.News;
import com.cairohat.fragments.Products;
import com.cairohat.fragments.SearchFragment;
import com.cairohat.fragments.SettingsFragment;
import com.cairohat.fragments.Update_Account;
import com.cairohat.fragments.WishList;
import com.cairohat.models.device_model.AppSettingsDetails;
import com.cairohat.models.drawer_model.Drawer_Items;
import com.cairohat.models.user_model.UserDetails;
import com.cairohat.models.user_model.Userdata2;
import com.cairohat.network.APIClient;
import com.cairohat.receivers.AlarmReceiver;
import com.cairohat.utils.NotificationScheduler;
import com.cairohat.utils.Utilities;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class ProfileActivity extends AppCompatActivity {


    NavigationView navigation;
    private LinearLayout mdrawerLayout;
    private ActionBarDrawerToggle mToggle;
    Toolbar toolbar;
    ActionBar actionBar;
    LinearLayout drawer_header;
    View header;
    SharedPreferences sharedPreferences;
    DialogLoader dialogLoader;
    String username;
    String usermail;
    CircularImageView drawer_profile_image;
    TextView drawer_profile_name;
    TextView drawer_profile_email;
    String imagee;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Add Advertisement");

        toolbar = (Toolbar) findViewById(R.id.myToolbar);
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);


        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle("My Profile");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mdrawerLayout = (LinearLayout) findViewById(R.id.drawer_layout);
        navigation = (NavigationView) findViewById(R.id.navigation_view);
        dialogLoader = new DialogLoader(ProfileActivity.this);


//        Menu nav_Menu = navigation.getMenu();

//        mToggle=new ActionBarDrawerToggle(ProfileActivity.this,mdrawerLayout,R.string.open,R.string.close);
//        mdrawerLayout.addDrawerListener(mToggle);
//        mToggle.syncState();


        header = navigation.getHeaderView(0);
        drawer_header = header.findViewById(R.id.drawer_header);
        drawer_profile_image = header.findViewById(R.id.drawer_profile_image);
        drawer_profile_name = header.findViewById(R.id.drawer_profile_name);
        drawer_profile_email = header.findViewById(R.id.drawer_profile_email);


        setCustomerInfo();
        // Check if the User is Authenticated


            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    Fragment fragment;
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    switch (id) {

                        case R.id.prof_account:
                            fragment = new Update_Account();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.main_fragment, fragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    .commit();
                            break;


                        case R.id.prof_addresses:
                            fragment = new My_Addresses();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.main_fragment, fragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    .commit();
                            break;


                        case R.id.prof_favourites:
                            fragment = new WishList();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.main_fragment, fragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    .commit();
                            break;


                        case R.id.prof_orders:
                            fragment = new My_Orders();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.main_fragment, fragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    .commit();
                            break;

                    }
                    return false;
                }
            });


        }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent=new Intent(ProfileActivity.this,MainActivity.class);
                ProfileActivity.this.startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setCustomerInfo() {
        username = sharedPreferences.getString("userName", null);
        usermail = sharedPreferences.getString("userEmail", null);
        dialogLoader.showProgressDialog();
        Call<Userdata2> call = APIClient.getInstance()
                .processProfile
                        (
                                usermail.trim()

                        );

        call.enqueue(new Callback<Userdata2>() {
            @Override
            public void onResponse(Call<Userdata2> call, retrofit2.Response<Userdata2> response) {
                dialogLoader.hideProgressDialog();
                Userdata2 userDetails = response.body();

//                Glide.with(ProfileActivity.this)
//                        .load(ConstantValues.URL+userInfo.getCustomersPicture()).asBitmap()
//                        .placeholder(R.drawable.profile)
//                        .error(R.drawable.profile)
//                        .into(drawer_profile_image);
//
                imagee = userDetails.getimage();
                if (ConstantValues.IS_USER_LOGGED_IN) {
                    // Check User's Info from SharedPreferences
                    if (sharedPreferences.getString("userEmail", null) != null) {




                        // Set User's Name, Email and Photo
                        drawer_profile_email.setText(username);
                        drawer_profile_name.setText(usermail);
//                Glide.with(this)
//                        .load(ConstantValues.URL+userInfo.getCustomersPicture()).asBitmap()
//                        .placeholder(R.drawable.profile)
//                        .error(R.drawable.profile)
//                        .into(drawer_profile_image);

                        Glide.with(ProfileActivity.this).load(Uri.parse(imagee)).into(drawer_profile_image);
                        Toast.makeText(ProfileActivity.this, imagee, Toast.LENGTH_LONG).show();


                    } else {
                        // Set Default Name, Email and Photo
                        drawer_profile_image.setImageResource(R.drawable.profile);
                        drawer_profile_name.setText(getString(R.string.login_or_signup));
                        drawer_profile_email.setText(getString(R.string.login_or_create_account));
                    }


                }
            }

            @Override
            public void onFailure(Call<Userdata2> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "failure :" + t.toString(), Toast.LENGTH_LONG).show();


            }
        });

    }
}
