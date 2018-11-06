package comp5216.myrecovery;

/**
 * Created by jessica on 2017/10/18.
 */

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private ArrayList<String> listFeeling;
    private ArrayList<Integer> listEmojis;
    private AppCompatActivity activity;

    public ImageAdapter(AppCompatActivity activity, ArrayList<String> listFeeling, ArrayList<Integer> listEmojis) {
        super();
        this.listFeeling = listFeeling;
        this.listEmojis = listEmojis;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listFeeling.size();
    }

    @Override
    public String getItem(int position) {
        // TODO Auto-generated method stub
        return listFeeling.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public static class ViewHolder
    {
        public ImageView imgViewEmo;
        public TextView txtViewFel;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder view;
        LayoutInflater inflator = activity.getLayoutInflater();

        if(convertView==null)
        {
            view = new ViewHolder();
            convertView = inflator.inflate(R.layout.moodgrid, null);

            view.txtViewFel = (TextView) convertView.findViewById(R.id.textView1);
            view.imgViewEmo = (ImageView) convertView.findViewById(R.id.imageView1);

            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }

        view.txtViewFel.setText(listFeeling.get(position));
        view.imgViewEmo.setImageResource(listEmojis.get(position));

        return convertView;
    }
}

