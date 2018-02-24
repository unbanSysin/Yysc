package com.yysc.activity.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by kj00037 on 2018/1/2.
 */

public class ObservableString extends BaseObservable implements Serializable,Parcelable {
    static final long serialVersionUID = 1L;
    public String mValue;

    /**
     * Wraps the given object and creates an observable object
     *
     * @param value The value to be wrapped as an observable.
     */
    public ObservableString(String value) {
        mValue = value;
    }

    /**
     * Creates an empty observable object
     */
    public ObservableString() {
    }

    /**
     * @return the stored value.
     */
//    @Bindable
    public String get() {
        return mValue;
    }

    /**
     * Set the stored value.
     */

    public void set(String value) {
        if (value != mValue) {
            mValue = value;
            notifyChange();
        }
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {dest.writeString(this.mValue);}

    protected ObservableString(Parcel in) {this.mValue = in.readString();}

    public static final Creator<ObservableString> CREATOR = new Creator<ObservableString>() {
        @Override
        public ObservableString createFromParcel(Parcel source) {return new ObservableString(source);}

        @Override
        public ObservableString[] newArray(int size) {return new ObservableString[size];}
    };
}
