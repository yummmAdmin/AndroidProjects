package com.digitaldestino.fragment;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.digitaldestino.R;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.utils.SmartTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Info_Fragment extends Fragment implements View.OnClickListener {
    private View view;
    private SmartTextView text_description, text_info;
    private String res_title_details, res_food_specialities, res_hours, res_additional_info,res_history_image="";
    private TextView text_hours, text_add_info, text_food_specialities;
    private ImageView history_image;

    public Info_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_info_2, container, false);
        findView();
        return view;
    }

    // initialize object here,,,
    private void findView() {
        res_title_details = BMSPrefs.getString(getContext(), "res_title_details");
        res_food_specialities = BMSPrefs.getString(getContext(), "res_food_specialities");
        res_hours = BMSPrefs.getString(getContext(), "res_hours");
        res_additional_info = BMSPrefs.getString(getContext(), "res_additional_info");
        res_history_image = BMSPrefs.getString(getContext(), "res_history_image");
        text_food_specialities = view.findViewById(R.id.text_food_specialities);
        text_description = view.findViewById(R.id.text_description);
        text_hours = view.findViewById(R.id.text_hours);
        text_add_info = view.findViewById(R.id.text_add_info);
        text_info = view.findViewById(R.id.text_info);
        history_image=view.findViewById(R.id.res_history_image);


        text_description.setText(res_title_details);
        text_info.setText(res_food_specialities);
        try {
            Glide.with(getContext())
                    .load(Constants.ARTICLE_IMAGE_PATH+"restaurants/"+res_history_image)
                    .error(R.drawable.placeholder)
                    .skipMemoryCache(true)
                    .into(history_image);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        text_hours.setOnClickListener(this);
        text_add_info.setOnClickListener(this);
        text_food_specialities.setOnClickListener(this);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_hours: {
                text_hours.setTextColor(Color.parseColor("#ffffff"));
                text_add_info.setTextColor(Color.parseColor("#6E6E6E"));
                text_food_specialities.setTextColor(Color.parseColor("#6E6E6E"));

                text_hours.setBackgroundResource(R.color.res_info_color);
                text_add_info.setBackgroundResource(R.color.white);
                text_food_specialities.setBackgroundResource(R.color.white);
                text_info.setText(res_hours);
                break;
            }

            case R.id.text_add_info: {
                text_hours.setTextColor(Color.parseColor("#6E6E6E"));
                text_add_info.setTextColor(Color.parseColor("#ffffff"));
                text_food_specialities.setTextColor(Color.parseColor("#6E6E6E"));

                text_hours.setBackgroundResource(R.color.white);
                text_food_specialities.setBackgroundResource(R.color.white);
                text_add_info.setBackgroundResource(R.color.res_info_color);
                text_info.setText(res_additional_info);
                break;
            }
            case R.id.text_food_specialities: {
                text_hours.setTextColor(Color.parseColor("#6E6E6E"));
                text_add_info.setTextColor(Color.parseColor("#6E6E6E"));
                text_food_specialities.setTextColor(Color.parseColor("#ffffff"));

                text_hours.setBackgroundResource(R.color.white);
                text_add_info.setBackgroundResource(R.color.white);
                text_food_specialities.setBackgroundResource(R.color.res_info_color);

                text_info.setText(res_food_specialities);
                break;
            }
        }
    }
}
