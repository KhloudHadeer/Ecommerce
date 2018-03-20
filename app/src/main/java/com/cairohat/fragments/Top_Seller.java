package com.cairohat.fragments;


import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cairohat.R;

import java.util.ArrayList;
import java.util.List;

import com.cairohat.adapters.ProductAdapter;
import com.cairohat.constant.ConstantValues;
import com.cairohat.customs.EndlessRecyclerViewScroll;
import com.cairohat.models.product_model.GetAllProducts;
import com.cairohat.models.product_model.ProductData;
import com.cairohat.network.APIClient;

import retrofit2.Call;
import retrofit2.Callback;


public class Top_Seller extends Fragment {

    String customerID;
    Boolean isHeaderVisible;
    Call<List<ProductData>> networkCall;

    TextView emptyRecord, headerText;
    RecyclerView top_seller_recycler;

    ProductAdapter productAdapter;

    List<ProductData> topSellerProductsList;
    private static int numberpage= 1;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f_products_horizontal, container, false);

        // Get the Boolean from Bundle Arguments
        isHeaderVisible = getArguments().getBoolean("isHeaderVisible");

        // Get the CustomerID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");


        // Binding Layout Views
        emptyRecord = (TextView) rootView.findViewById(R.id.empty_record_text);
        headerText = (TextView) rootView.findViewById(R.id.products_horizontal_header);
        top_seller_recycler = (RecyclerView) rootView.findViewById(R.id.products_horizontal_recycler);
    
        
        // Hide some of the Views
        emptyRecord.setVisibility(View.GONE);
    
        // Check if Header must be Invisible or not
        if (!isHeaderVisible) {
            headerText.setVisibility(View.GONE);
        } else {
            headerText.setText(getString(R.string.top_seller));
        }
    
    
        topSellerProductsList = new ArrayList<ProductData>();
        

        // RecyclerView has fixed Size
        top_seller_recycler.setHasFixedSize(true);
        top_seller_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        RequestTopSellerProducts(numberpage);
        // Initialize the ProductAdapter for RecyclerView
        productAdapter = new ProductAdapter(getContext(), topSellerProductsList, true);
    
        // Set the Adapter and LayoutManager to the RecyclerView
        top_seller_recycler.setAdapter(productAdapter);




        // Request for Most Sold Products



        return rootView;
    }



    //*********** Adds Products returned from the Server to the topSellerProductsList ********//

    private void addProducts(List<ProductData> productData) {

        // Add Products to topSellerProductsList
        topSellerProductsList.addAll(productData);

        productAdapter.notifyDataSetChanged();

    }



    //*********** Request all the Products from the Server based on the Sales of Products ********//

    public void RequestTopSellerProducts(final int page) {

//        GetAllProducts getAllProducts = new GetAllProducts();
//        getAllProducts.setPageNumber(0);
//        getAllProducts.setLanguageId(ConstantValues.LANGUAGE_ID);
//        getAllProducts.setCustomersId(customerID);
//        getAllProducts.setType("top seller");

        networkCall = APIClient.getInstance()
                .getbestsales(page);


        networkCall.enqueue(new Callback<List<ProductData>>() {
            @Override
            public void onResponse(Call<List<ProductData>> call, retrofit2.Response<List<ProductData>> response) {

                if (response.isSuccessful()) {
//                    if(response.body().size() == 0){
//                       // emptyRecord.setVisibility(View.VISIBLE);
//
//                    }else {
                      addProducts(response.body());
                      if(response.body().size() == 10 ){
                          new LoadMoreTask(page+1).execute();
                      }


                   // }

                    // Check the Success status
//                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
//                        // Products have been returned. Add Products to the topSellerProductsList
//                        emptyRecord.setVisibility(View.GONE);
//                        addProducts(response.body());
//
//                    }
//                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
//                        // Products haven't been returned
//
//
//                    }
                }
            }

            @Override
            public void onFailure(Call<List<ProductData>> call, Throwable t) {
                if (!networkCall.isCanceled()) {
                    Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
                }
            }
        });
    }




    private class LoadMoreTask extends AsyncTask<String, Void, String> {

        int page_number;


        private LoadMoreTask(int page_number) {
            this.page_number = page_number;
        }


        //*********** Runs on the UI thread before #doInBackground() ********//

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        //*********** Performs some Processes on Background Thread and Returns a specified Result  ********//

        @Override
        protected String doInBackground(String... params) {

            // Request for Products based on PageNo.
            RequestTopSellerProducts(page_number);
            System.out.println("num "+page_number);


            return "All Done!";
        }


        //*********** Runs on the UI thread after #doInBackground() ********//

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}