package project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.shokoofeadeli.onlineshop.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static project.service.ServiceGenerator.API_BASE_URL;

public class ImagePagerAdapter extends PagerAdapter {
    public final static int SHOWCAPTION = 1;
    public final static int HIDECAPTION = 2;
    private HashMap<String, String> slider_map;
    private int captionState;
    private Object[] key;
    private Context context;
    private LayoutInflater layoutInflater;

    public ImagePagerAdapter(Context context, HashMap<String, String> slider_map, int captionState) {
        this.context = context;
        this.slider_map = slider_map;
        this.captionState = captionState;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        key = slider_map.keySet().toArray();
    }

    @Override
    public int getCount() {
        return slider_map.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.slider_image, null);
        TextView text = view.findViewById(R.id.text);
        ImageView image = view.findViewById(R.id.image);
        if (captionState == SHOWCAPTION) {
            text.setText(key[position].toString());
        }
        if (captionState == HIDECAPTION) {
            text.setText("");
        }
        String imageURL = API_BASE_URL + slider_map.get(key[position]);
        Picasso.with(context).load(imageURL).into(image);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
