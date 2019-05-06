package com.digitaldestino.search_activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitaldestino.R;
import com.digitaldestino.adapter.ArticleAdapter;
import com.digitaldestino.adapter.MenuListAdapter;
import com.digitaldestino.adapter.SearchAddAdapter;
import com.digitaldestino.adapter.SearchListAdapter;
import com.digitaldestino.dishes_restaurentUI.DishesRestaurentActivity;
import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.listener.onDeleteItemListener;
import com.digitaldestino.listener.onOrderSelectListener;
import com.digitaldestino.localdatabase.DatabaseHelper;
import com.digitaldestino.modelClass.auto_complete.AutoCompleteData;
import com.digitaldestino.modelClass.auto_complete.AutoCompleteResponse;
import com.digitaldestino.modelClass.search_dishes.SearchDish;
import com.digitaldestino.networkClass.RetrofitClient;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.utils.GPSTracker;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, onOrderSelectListener, onDeleteItemListener {
    private EditText edt_search;
    private AutoCompleteTextView edt_auto_search;
    private TextView text_search;
    private ImageView img_search;
    private String search_dishes = "", device_id, Latitude, Longitude;
    private ProgressDialog progressDialog;
    private boolean isInternetPresent = false;
    private ConnectionDetector connectionDetector;
    private List<AutoCompleteData> autoCompleteDataArrayList = new ArrayList<>();
    private List<AutoCompleteData> autoCompleteDataArrayListPopulate;
    private static final String TAG = "SearchActivity";
    private RecyclerView recycler_search_list, recycler_serarch_item;
    private SearchListAdapter searchListAdapter;
    private SearchAddAdapter searchAddAdapter;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseHelper db;
    private ArrayList<String> searchDisharrayList = new ArrayList<>();
    private ArrayList<String> searchDishes = new ArrayList<>();
    private ArrayList<String> final_list = new ArrayList<>();
    private ArrayList<String> sendDataArrayList = new ArrayList<>();
    private GPSTracker gpsTracker;
    private CustomListAdapter customListAdapter;

    private ArrayList<AutoCompleteData> searchDataArrayList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //  requestParam();
        findView();
        getCurrentLatLong();
        setAdapter();

    }

    //get current latitude and longitude
    private void getCurrentLatLong() {
        Latitude = String.valueOf(gpsTracker.getLatitude());
        Longitude = String.valueOf(gpsTracker.getLongitude());
    }


    // to initialize object
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void findView() {
        gpsTracker = new GPSTracker(getApplicationContext());
        db = new DatabaseHelper(getApplicationContext());
        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        device_id = BMSPrefs.getString(getApplicationContext(), Constants.DEVICE_ID);
        edt_auto_search = findViewById(R.id.edt_auto_search);
        text_search = findViewById(R.id.text_search);
        edt_search = findViewById(R.id.edt_search);
        img_search = findViewById(R.id.img_search);
        recycler_search_list = findViewById(R.id.recycler_search_list);
        recycler_serarch_item = findViewById(R.id.recycler_serarch_item);
        img_search.setOnClickListener(this);

//        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    CommonMethod.hideKeyBoard(SearchActivity.this);
//                   // search_dishes = edt_search.getText().toString().trim();
//                    search_dishes = edt_auto_search.getText().toString().trim();
//                    if (!search_dishes.equalsIgnoreCase("")) {
//                        db.insertRecord(search_dishes);
//                    }
//                    intentMethod();
//                }
//
//                return false;
//            }
//        });
        try {
            searchDisharrayList = db.getList();
            if (searchDisharrayList.size() > 0) {
                for (int i = 0; i < searchDisharrayList.size(); i++) {
                    searchDishes.add(searchDisharrayList.get(i));
                }
                ArrayList<String> al2 = new ArrayList<String>();
                for (String str2 : searchDishes) {
                    if (!al2.contains(str2)) {
                        al2.add(str2);
                    }
                }
                for (String str3 : al2) {
                    final_list.add(str3);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            customListAdapter = new CustomListAdapter(SearchActivity.this, R.layout.searchdish_adapter);
            edt_auto_search.setThreshold(1);
            edt_auto_search.setAdapter(customListAdapter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        edt_auto_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                try {
                    edt_auto_search.setText("");
                    AutoCompleteData autoCompleteData = new AutoCompleteData();
                    autoCompleteData.setFood_item_name(autoCompleteDataArrayList.get(position).getFood_item_name());
                    autoCompleteData.setFood_item_id(autoCompleteDataArrayList.get(position).getFood_item_id());
                    searchDataArrayList.add(autoCompleteData);
                    // CommonMethod.showToastShort(autoCompleteDataArrayList.get(position).getFood_item_id(), getApplicationContext());
                    setSearchItemAdapter();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void intentMethod() {
        if (searchDataArrayList.size() > 0) {
            for (int i = 0; i < searchDataArrayList.size(); i++) {
                sendDataArrayList.add(searchDataArrayList.get(i).getFood_item_name());
            }
        }
        Intent intent = new Intent(getApplicationContext(), DishesRestaurentActivity.class);
        intent.putExtra("search_dishes", search_dishes);
        intent.putStringArrayListExtra("sendDataArrayList", sendDataArrayList);
        startActivity(intent);
        finish();
    }

    //adapter
    private void setAdapter() {
        if (searchDishes.size() > 0) {
            text_search.setVisibility(View.VISIBLE);
            searchListAdapter = new SearchListAdapter(getApplicationContext(), final_list, this);
            linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            recycler_search_list.setLayoutManager(linearLayoutManager);
            recycler_search_list.setAdapter(searchListAdapter);
        } else {
            text_search.setVisibility(View.GONE);
        }
    }

    // adpater
    private void setSearchItemAdapter() {
        searchAddAdapter = new SearchAddAdapter(getApplicationContext(), searchDataArrayList, this);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_serarch_item.setLayoutManager(linearLayoutManager);
        //    recycler_serarch_item.setLayoutManager(new GridLayoutManager(this, 3));
        recycler_serarch_item.setAdapter(searchAddAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_search: {
                CommonMethod.hideKeyBoard(SearchActivity.this);
                if (searchDataArrayList.size() > 0) {
                    for (int i = 0; i < searchDataArrayList.size(); i++) {
                        db.insertRecord(searchDataArrayList.get(i).getFood_item_name());
                    }
                }
                intentMethod();
                break;
            }
        }
    }

    // listener
    @Override
    public void onOrderSelectItemClick(int position) {
        CommonMethod.hideKeyBoard(SearchActivity.this);
        sendDataArrayList.clear();
        searchDataArrayList.clear();
        search_dishes = final_list.get(position);
        sendDataArrayList.add(search_dishes);
        intentMethod();
    }

    // api call
    private List<AutoCompleteData> getSearchList(String term) {
        final HashMap<String, String> params = new HashMap<>();
        params.put("lat", Latitude);
        params.put("lng", Longitude);
        params.put("keyword", term);
        Log.d("searchParam", params.toString());
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.autoComplete(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AutoCompleteResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AutoCompleteResponse autoCompleteResponse) {
                        Log.d("response", autoCompleteResponse.toString());
                        String status = autoCompleteResponse.getStatus();
                        String msg = autoCompleteResponse.getMsg();
                        String key = autoCompleteResponse.getKey();
                        if (status.equalsIgnoreCase("1")) {
                            autoCompleteDataArrayList = autoCompleteResponse.getResponse_data().getAutoCompleteData();
                            customListAdapter.setData(autoCompleteDataArrayList);
                            customListAdapter.notifyDataSetChanged();
//                            if (autoCompleteDataArrayList.size() <= 0) {
//                                CommonMethod.showToastShort(getString(R.string.data_not_available), SearchActivity.this);
//                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return autoCompleteDataArrayList;
    }


    // delete item
    @Override
    public void onItemClick(int position, int food_item_id) {
        searchDataArrayList.remove(position);
        setSearchItemAdapter();
        searchAddAdapter.notifyDataSetChanged();
    }

    // adapter class
    public class CustomListAdapter extends ArrayAdapter implements Filterable {
        public CustomListAdapter(Context context, int resource) {
            super(context, resource);
            autoCompleteDataArrayListPopulate = new ArrayList<>();
        }

        public void setData(List<AutoCompleteData> list) {
            autoCompleteDataArrayListPopulate.clear();
            autoCompleteDataArrayListPopulate.addAll(list);
        }

        public int getCount() {
            return autoCompleteDataArrayListPopulate.size();
        }

        public AutoCompleteData getItem(int position) {
            return autoCompleteDataArrayListPopulate.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.searchdish_adapter, parent, false);
            try {
                AutoCompleteData autoCompleteData = autoCompleteDataArrayListPopulate.get(position);
                TextView txt_location_name = convertView.findViewById(R.id.text_search_dish);
                txt_location_name.setText(autoCompleteData.getFood_item_name());
            } catch (Exception e) {
            }
            return convertView;
        }

        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();

                    if (constraint != null) {
                        try {
                            if (isInternetPresent) {
                                autoCompleteDataArrayListPopulate = getSearchList(constraint.toString());

                            } else {
                                CommonMethod.showAlertDialog(SearchActivity.this, getString(R.string.internet_toast));
                            }
                        } catch (Exception e) {

                        }
                        filterResults.values = autoCompleteDataArrayListPopulate;
                        filterResults.count = autoCompleteDataArrayListPopulate.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results == null || results.count <= 0) {
                        CustomListAdapter.this.notifyDataSetInvalidated();
                    } else {
                        CustomListAdapter.this.notifyDataSetChanged();
                    }
                }
            };
        }
    }


}
