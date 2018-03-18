package com.cairohat.models.product_model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Attribute implements Parcelable {

    private int id;
    private String name;
    private List<String> options;


    private Option option;

    private List<Value> values = new ArrayList<Value>();


    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }


    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    public Attribute() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The option
     */

    public List<String> getoptions() {
        return options;
    }

    /**
     * 
     * @param values
     *     The values
     */
    public void setoptions(List<String> values) {
        this.options = values;
    }




    //********** Describes the kinds of Special Objects contained in this Parcelable Instance's marshaled representation *********//

    @Override
    public int describeContents() {
        return 0;
    }



    //********** Writes the values to the Parcel *********//

    @Override
    public void writeToParcel(Parcel parcel_out, int flags) {
       // parcel_out.writeParcelable(option, flags);
        parcel_out.writeList(options);
    }



    //********** Generates Instances of Parcelable class from a Parcel *********//

    public static final Creator<Attribute> CREATOR = new Creator<Attribute>() {
        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public Attribute createFromParcel(Parcel parcel_in) {
            return new Attribute(parcel_in);
        }

        // Creates a new array of the Parcelable class
        @Override
        public Attribute[] newArray(int size) {
            return new Attribute[size];
        }
    };



    //********** Retrieves the values from the Parcel *********//

    protected Attribute(Parcel parcel_in) {

       // this.option = parcel_in.readParcelable(Option.class.getClassLoader());

        this.options = new ArrayList<String>();
        parcel_in.readList(options, Value.class.getClassLoader());
    }

}
