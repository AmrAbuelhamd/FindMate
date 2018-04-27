package eg.com.blogspot.httpamrabuelhamd.findmate.NeedApartment.singleAprtmentDataPublisher;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import eg.com.blogspot.httpamrabuelhamd.findmate.CustomItemClickListener;
import eg.com.blogspot.httpamrabuelhamd.findmate.R;

/**
 * Created by amro mohamed on 3/4/2018.
 */
//TODO this class needs a lot of modifications
public class SingleAprtmentDataAdapter extends RecyclerView.Adapter<SingleAprtmentDataAdapter.ViewHolder> {
    private List<SingleAprtmentData> mAprtmentData;
    private CustomItemClickListener listener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView street, dateOfUnitAddition, city, price;
        public ImageView thumbnailPhoto;
        public ViewHolder(View v) {
            super(v);
            street = v.findViewById(R.id.street);
            dateOfUnitAddition = v.findViewById(R.id.date_added);
            city = v.findViewById(R.id.city);
            price = v.findViewById(R.id.price);
            thumbnailPhoto = v.findViewById(R.id.apartment_image);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SingleAprtmentDataAdapter(ArrayList<SingleAprtmentData> aprtmentData, CustomItemClickListener l) {
        mAprtmentData = aprtmentData;
        listener = l;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SingleAprtmentDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_aprtment_card_item, parent, false);

        final ViewHolder vh = new ViewHolder(v);

        //setting listener
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, vh.getAdapterPosition());            }
        });
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        SingleAprtmentData aprtmentData = mAprtmentData.get(position);
        holder.street.setText(aprtmentData.getStreet());
        holder.city.setText(aprtmentData.getCity());
        holder.dateOfUnitAddition.setText(aprtmentData.getDateOfUnitAddition());
        holder.price.setText(aprtmentData.getPrice());
        holder.thumbnailPhoto.setImageResource(aprtmentData.getThumbnailPhoto());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mAprtmentData.size();
    }
}