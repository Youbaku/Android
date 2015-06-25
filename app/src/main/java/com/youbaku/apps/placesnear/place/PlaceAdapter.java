//
//  PlaceAdapter
//
//  Places Near
//  Created by Mobigo Bilişim Teknolojileri
//  Copyright (c) 2015 Mobigo Bilişim Teknolojileri. All rights reserved.
//

package com.youbaku.apps.placesnear.place;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.larvalabs.svgandroid.SVGParser;
import com.youbaku.apps.placesnear.App;
import com.youbaku.apps.placesnear.R;
import com.youbaku.apps.placesnear.apicall.VolleySingleton;

import java.util.ArrayList;

public class PlaceAdapter extends ArrayAdapter<Place> {
    private ArrayList<Place> list;
    private int color=0;

    private ImageLoader mImageLoader;

    public PlaceAdapter(Context context, ArrayList<Place> list, int ratingColor) {
        super(context, R.layout.place_list_item);
        this.list=list;
        color=ratingColor;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.place_list_item, null);

            com.larvalabs.svgandroid.SVG like= SVGParser.getSVGFromResource(getContext().getResources(),R.raw.icon_like,App.SVGOldColor,color);
            com.larvalabs.svgandroid.SVG com= SVGParser.getSVGFromResource(getContext().getResources(),R.raw.icon_comments,App.SVGOldColor,color);
            com.larvalabs.svgandroid.SVG dist= SVGParser.getSVGFromResource(getContext().getResources(),R.raw.icon_distance,App.SVGOldColor,color);

            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(App.dpTopx(getContext(),50),App.dpTopx(getContext(),50));

            ImageView iLike=((ImageView)convertView.findViewById(R.id.like_view_place_list_item));
            ViewCompat.setLayerType(iLike,ViewCompat.LAYER_TYPE_SOFTWARE,null);
            iLike.setImageDrawable(like.createPictureDrawable());
            iLike.setLayoutParams(params);

            ImageView iCom=((ImageView)convertView.findViewById(R.id.comment_view_place_list_item));
            ViewCompat.setLayerType(iCom,ViewCompat.LAYER_TYPE_SOFTWARE,null);
            iCom.setImageDrawable(com.createPictureDrawable());
            iCom.setLayoutParams(params);

            ImageView iDist=((ImageView)convertView.findViewById(R.id.location_view_place_list_item));
            ViewCompat.setLayerType(iDist,ViewCompat.LAYER_TYPE_SOFTWARE,null);
            iDist.setImageDrawable(dist.createPictureDrawable());
            iDist.setLayoutParams(params);

            ((TextView)convertView.findViewById(R.id.like_text_place_list_item)).setTextColor(color);
            ((TextView)convertView.findViewById(R.id.           location_text_place_list_item)).setTextColor(color);
            ((TextView)convertView.findViewById(R.id.comment_text_place_list_item)).setTextColor(color);
        }


        ((TextView)convertView.findViewById(R.id.name_place_list_item)).setText(list.get(position).getName());
        ((TextView)convertView.findViewById(R.id.category_place_list_item)).setText(list.get(position).address);
        //((TextView)convertView.findViewById(R.id.like_text_place_list_item)).setText(list.get(position).likes+"");
        //((TextView)convertView.findViewById(R.id.location_text_place_list_item)).setText(App.getDistanceString(PlaceFilter.getInstance().metrics,list.get(position).distance[0]));
       ((TextView)convertView.findViewById(R.id.comment_text_place_list_item)).setText(list.get(position).comments.size()+"");
        //((TextView)convertView.findViewById(R.id.comment_text_place_list_item)).setText(list.get(position).getId()+"");


        //Image Location
        String url = "http://youbaku.com/uploads/places_header_images//"+list.get(position).getImgUrl(); // URL of the image
        mImageLoader = VolleySingleton.getInstance().getImageLoader();

        final NetworkImageView im = ((NetworkImageView) convertView.findViewById(R.id.image_place_list_item));
        im.setImageUrl(url,mImageLoader);


        WindowManager windowManager = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display= windowManager.getDefaultDisplay();
        LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)im.getLayoutParams();
        int width=0;

        if(Build.VERSION.SDK_INT>12){
            /**
            * Constructs a new String by converting the specified array of
            * bytes using the platform's default character encoding.*/

            Point s=new Point();
            display.getSize(s);
            width=s.x;
        }else{
            width=display.getWidth();
        }

        int height=(width/16)*9;
        params.height=height;
        System.out.println(width+" x "+height);
        im.setLayoutParams(params);

       /*

        im.setImageDrawable(getContext().getResources().getDrawable(R.drawable.placeholder_placelist));
        if(list.get(position).photos!=null && list.get(position).photos.size()>0) {
            Picasso.with(getContext())
                    .load(list.get(position).photos.get(0).url)
                    .placeholder(R.drawable.placeholder_placelist)
                    .into(im);
        }
*/     TextView rateTxt=(TextView)convertView.findViewById(R.id.rating_place_list_item);

        if(list.get(position).rating > 3.5 && list.get(position).rating <=5.0 ){
            rateTxt.setBackgroundColor(Color.parseColor(App.GreenColor));
        }
        else if(list.get(position).rating >= 3.0 && list.get(position).rating <=3.5 ){
            rateTxt.setBackgroundColor(Color.parseColor(App.YellowColor));
        }
        else{
            rateTxt.setBackgroundColor(Color.parseColor(App.ButtonColor));
        }

        rateTxt.setText(list.get(position).rating + "/5.0");

        return convertView;
    }
}
