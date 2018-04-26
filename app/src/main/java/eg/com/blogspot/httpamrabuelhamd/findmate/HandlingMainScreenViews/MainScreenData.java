package eg.com.blogspot.httpamrabuelhamd.findmate.HandlingMainScreenViews;

/**
 * Created by amro mohamed on 3/1/2018.
 */

public class MainScreenData {
    private int mImageResourceId;
    private int mMainPhrase;
    private int mLittleDescription;

    public MainScreenData(int mImageResourceId, int mMainPhrase, int mLittleDescription) {
        this.mImageResourceId = mImageResourceId;
        this.mMainPhrase = mMainPhrase;
        this.mLittleDescription = mLittleDescription;
    }

    public int getmImageResourceId() {
        return mImageResourceId;
    }

    public int getmOptionName() {
        return mMainPhrase;
    }

    public int getOptionDescribtion() {
        return mLittleDescription;
    }
}
