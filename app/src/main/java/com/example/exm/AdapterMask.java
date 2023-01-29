package com.example.exm;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class AdapterMask extends BaseAdapter {

    public Context mContext;
    public List<Model> modelList;

    public AdapterMask(Context mContext, List<Model> modelList) {
        this.mContext = mContext;
        this.modelList = modelList;
    }

@Override
public int getCount()
{
    return modelList.size();
}
@Override
    public Object getItem(int position)
{
    return  modelList.get(position);
}
@Override
    public long getItemId(int position)
{
    return modelList.get(position).getID();
}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext,R.layout.item_mask,null);
        ImageView image = v.findViewById(R.id.image);
        TextView title = v.findViewById(R.id.title);
        TextView cost = v.findViewById(R.id.cost);
        TextView stockAvailability = v.findViewById(R.id.stockAvailability);
        TextView availabilityInTheStore = v.findViewById(R.id.availabilityInTheStore);
        TextView description = v.findViewById(R.id.description);
        TextView rewiews = v.findViewById(R.id.rewiews);

        Model model = modelList.get(position);
        title.setText(model.getTitle());
        cost.setText(Integer.toString(model.getCost()));
        if(model.getAvailabilityInTheStore()>5)
        {
            availabilityInTheStore.setText("Много");
        }
        else
        {
            availabilityInTheStore.setText(Integer.toString(model.getAvailabilityInTheStore()));
        }


        if(model.getStockAvailability()>5)
        {
            stockAvailability.setText("Много");
        }
        else {
            stockAvailability.setText(Integer.toString(model.getStockAvailability()));
        }

        description.setText(model.getDescription());
        rewiews.setText(model.getRewiews());

        image m = new image(mContext);
        image.setImageBitmap(m.getUserImage(model.getImage()));

        if(Admin.admin == 2){
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, UpdateActivity.class);
                    intent.putExtra("key", model);
                    mContext.startActivity(intent);
                }
            });
        }
        return v;

    }

}

