package eg.com.blogspot.httpamrabuelhamd.findmate.NeedApartment.singleAprtmentDataPublisher;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amro mohamed on 3/4/2018.
 */

public class SingleAprtmentData implements Parcelable{
    private int resultId;
    private int thumbnailPhoto;
    private int street;
    private int dateOfUnitAddition;
    private int city;
    private int price;

    public SingleAprtmentData(int resultId, int thumbnailPhoto, int street, int dateOfUnitAddition, int city, int price) {
        this.resultId = resultId;
        this.thumbnailPhoto = thumbnailPhoto;
        this.street = street;
        this.dateOfUnitAddition = dateOfUnitAddition;
        this.city = city;
        this.price = price;
    }

    public int getResultId() {
        return resultId;
    }

    public int getThumbnailPhoto() {
        return thumbnailPhoto;
    }

    public int getStreet() {
        return street;
    }

    public int getDateOfUnitAddition() {
        return dateOfUnitAddition;
    }

    public int getCity() {
        return city;
    }

    public int getPrice() {
        return price;
    }

    //region serialization part
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.resultId);
        dest.writeInt(this.thumbnailPhoto);
        dest.writeInt(this.street);
        dest.writeInt(this.dateOfUnitAddition );
        dest.writeInt(this.city);
        dest.writeInt(this.price);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SingleAprtmentData createFromParcel(Parcel in) {
            return new SingleAprtmentData(in);
        }
        public SingleAprtmentData[] newArray(int size) {
            return new SingleAprtmentData[size];
        }
    };
    // Parcelling part
    public SingleAprtmentData(Parcel in){
        this.resultId = in.readInt();
        this.thumbnailPhoto = in.readInt();
        this.street = in.readInt();
        this.dateOfUnitAddition =in.readInt();
        this.city = in.readInt();
        this.price = in.readInt();
    }
    //endregion

}
