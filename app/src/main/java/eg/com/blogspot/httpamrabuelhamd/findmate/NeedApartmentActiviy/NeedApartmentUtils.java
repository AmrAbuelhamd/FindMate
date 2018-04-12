package eg.com.blogspot.httpamrabuelhamd.findmate.NeedApartmentActiviy;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by amro mohamed on 3/2/2018.
 */

public abstract class NeedApartmentUtils {
    private static final String egyptJson = "{\"regions\":[{\"regionID\":\"1.28.\",\"regionName\":\"اسوان\",\"chCount\":15,\"subRegions\":[{\"regionID\":\"1.28.3.\",\"regionName\":\"ادفو\",\"chCount\":37},{\"regionID\":\"1.28.8.\",\"regionName\":\"ارض يعقوب\",\"chCount\":0},{\"regionID\":\"1.28.1.\",\"regionName\":\"اسوان\",\"chCount\":9},{\"regionID\":\"1.28.9.\",\"regionName\":\"الحصايا\",\"chCount\":0},{\"regionID\":\"1.28.12.\",\"regionName\":\"السيل الجديد\",\"chCount\":0},{\"regionID\":\"1.28.10.\",\"regionName\":\"السيل الريفى\",\"chCount\":0},{\"regionID\":\"1.28.15.\",\"regionName\":\"النجع البحرى\",\"chCount\":1},{\"regionID\":\"1.28.7.\",\"regionName\":\"النوبه\",\"chCount\":0},{\"regionID\":\"1.28.13.\",\"regionName\":\"بينان قبلى\",\"chCount\":1},{\"regionID\":\"1.28.4.\",\"regionName\":\"كوم أمبو\",\"chCount\":22},{\"regionID\":\"1.28.11.\",\"regionName\":\"كيما\",\"chCount\":0},{\"regionID\":\"1.28.5.\",\"regionName\":\"مدينة نصر\",\"chCount\":38},{\"regionID\":\"1.28.2.\",\"regionName\":\"مدينة نصر\",\"chCount\":0},{\"regionID\":\"1.28.14.\",\"regionName\":\"مركز أبو سمبل\",\"chCount\":1},{\"regionID\":\"1.28.6.\",\"regionName\":\"مركز دراو\",\"chCount\":9}]},{\"regionID\":\"1.25.\",\"regionName\":\"اسيوط\",\"chCount\":15,\"subRegions\":[{\"regionID\":\"1.25.4.\",\"regionName\":\"ابنوب\",\"chCount\":19},{\"regionID\":\"1.25.5.\",\"regionName\":\"ابو تيج\",\"chCount\":14},{\"regionID\":\"1.25.3.\",\"regionName\":\"اسيوط\",\"chCount\":37},{\"regionID\":\"1.25.6.\",\"regionName\":\"البدارى\",\"chCount\":20},{\"regionID\":\"1.25.8.\",\"regionName\":\"الغنايم\",\"chCount\":8},{\"regionID\":\"1.25.9.\",\"regionName\":\"القوصيه\",\"chCount\":33},{\"regionID\":\"1.25.14.\",\"regionName\":\"النخيله\",\"chCount\":0},{\"regionID\":\"1.25.1.\",\"regionName\":\"اول اسيوط\",\"chCount\":9},{\"regionID\":\"1.25.2.\",\"regionName\":\"ثان اسيوط\",\"chCount\":6},{\"regionID\":\"1.25.10.\",\"regionName\":\"ديروط\",\"chCount\":45},{\"regionID\":\"1.25.7.\",\"regionName\":\"ساحل سليم\",\"chCount\":17},{\"regionID\":\"1.25.11.\",\"regionName\":\"صدفا\",\"chCount\":18},{\"regionID\":\"1.25.15.\",\"regionName\":\"مدينة أسيوط الجديدة\",\"chCount\":0},{\"regionID\":\"1.25.13.\",\"regionName\":\"مركز الفتح\",\"chCount\":24},{\"regionID\":\"1.25.12.\",\"regionName\":\"منفلوط\",\"chCount\":27}]},{\"regionID\":\"1.2.\",\"regionName\":\"الاسكندريه\",\"chCount\":52},{\"regionID\":\"1.19.\",\"regionName\":\"الاسماعيلية\",\"chCount\":10},{\"regionID\":\"1.29.\",\"regionName\":\"الاقصـر\",\"chCount\":8},{\"regionID\":\"1.31.\",\"regionName\":\"البحر الاحمر\",\"chCount\":7},{\"regionID\":\"1.18.\",\"regionName\":\"البحيرة\",\"chCount\":29},{\"regionID\":\"1.21.\",\"regionName\":\"الجيزه\",\"chCount\":25},{\"regionID\":\"1.12.\",\"regionName\":\"الدقهلية\",\"chCount\":26},{\"regionID\":\"1.4.\",\"regionName\":\"السويس\",\"chCount\":9},{\"regionID\":\"1.13.\",\"regionName\":\"الشرقيه\",\"chCount\":26},{\"regionID\":\"1.16.\",\"regionName\":\"الغربيه\",\"chCount\":20},{\"regionID\":\"1.23.\",\"regionName\":\"الفيوم\",\"chCount\":10},{\"regionID\":\"1.1.\",\"regionName\":\"القاهره\",\"chCount\":64},{\"regionID\":\"1.14.\",\"regionName\":\"القليوبيه\",\"chCount\":18},{\"regionID\":\"1.17.\",\"regionName\":\"المنوفيه\",\"chCount\":12},{\"regionID\":\"1.24.\",\"regionName\":\"المنيا\",\"chCount\":14},{\"regionID\":\"1.32.\",\"regionName\":\"الوادى الجديد\",\"chCount\":3},{\"regionID\":\"1.22.\",\"regionName\":\"بنى سويف\",\"chCount\":11},{\"regionID\":\"1.3.\",\"regionName\":\"بورسعيد\",\"chCount\":26},{\"regionID\":\"1.35.\",\"regionName\":\"جنوب سيناء\",\"chCount\":8},{\"regionID\":\"1.11.\",\"regionName\":\"دمياط\",\"chCount\":10},{\"regionID\":\"1.26.\",\"regionName\":\"سوهاج\",\"chCount\":23},{\"regionID\":\"1.36.\",\"regionName\":\"سيناء\",\"chCount\":3},{\"regionID\":\"1.34.\",\"regionName\":\"شمال سيناء\",\"chCount\":15},{\"regionID\":\"1.27.\",\"regionName\":\"قنــا\",\"chCount\":18},{\"regionID\":\"1.15.\",\"regionName\":\"كفرالشيخ\",\"chCount\":18},{\"regionID\":\"1.33.\",\"regionName\":\"مرسى مطروح\",\"chCount\":7}],\"selectedVal\":\"\",\"countChildren\":true,\"totPath\":\"مصر\"}";

    /**
     * extract the governorates from the json
     * @return Array of strings
     */
    static ArrayList<String> getGovernorates(){
        //tODO try change this approach later of creating a lot of Arrraylists
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            JSONArray regionsArray = getJsonArray();
            arrayList =  traversingJsonArray(regionsArray);
        }catch (Exception e){

        }
        return arrayList;
    }
    private static JSONArray getJsonArray()throws Exception{
        JSONObject rootJsonObject = new JSONObject(egyptJson);
        JSONArray regionsArray = rootJsonObject.getJSONArray("regions");
        return regionsArray;
    }
    private static ArrayList<String> traversingJsonArray(JSONArray jsonArray)throws Exception{
        ArrayList<String> egyptGovernorates = new ArrayList<>();

        for (int i=0; i < jsonArray.length(); i++){
            JSONObject governorateObject = jsonArray.getJSONObject(i);
            egyptGovernorates.add(governorateObject.getString("regionName"));
        }
        return egyptGovernorates;
    }
    static ArrayList<String> getSubregions(int position){
        position--;
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            JSONArray regionsArray = getJsonArray();
            JSONObject specificRegion = regionsArray.getJSONObject(position);
            JSONArray subRegoin = specificRegion.getJSONArray("subRegions");
            Log.v("TESTY","sdfsa"+subRegoin.getJSONObject(0).getString("regionName"));
            arrayList=  traversingJsonArray(subRegoin);
        }catch (Exception e){
            Log.v("exception","e= "+e);
        }
        return arrayList;
    }
}
