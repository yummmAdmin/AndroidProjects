package com.digitaldestino.book_table;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.digitaldestino.R;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.dishes_restaurentUI.DishesRestaurentActivity;
import com.digitaldestino.notification.NotificationActivity;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.wishlist.WishlistActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class BookTableActivity extends AppCompatActivity implements View.OnClickListener, BookTableContract.View {
    private ImageView img_back, img_bookmark, img_notification;
    private Button btn_booktable;
    private TextView text_edit;
    private RelativeLayout main_layout;
    private EditText edt_date, edt_time, edt_person, edt_name, edt_address, edt_number;
    private String Persons, final_Time, final_Date, Name, Address, Mobile, session_token, restaurant_id,
            final_order_date_time, user_name, user_address, user_number, device_token;
    private String order_timezone, current_time;
    private int year, month, dayOfMonth;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private ProgressDialog progressDialog;
    private final String TAG = "BookTableActivity";
    private BookTablePresenter bookTablePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_table);
        findView();
        setData();
    }

    // to initialize object here...
    private void findView() {
        DateFormat df = new SimpleDateFormat("HH:mm");
        current_time = df.format(Calendar.getInstance().getTime());
        // CommonMethod.showToastShort(current_time,getApplicationContext());
        order_timezone = TimeZone.getDefault().getID();
        session_token = BMSPrefs.getString(BookTableActivity.this, Constants.SESSION_TOKEN);
        restaurant_id = BMSPrefs.getString(BookTableActivity.this, Constants.RESTAURANT_ID);
        user_name = BMSPrefs.getString(BookTableActivity.this, Constants.USER_NAME);
        user_address = BMSPrefs.getString(BookTableActivity.this, Constants.USER_Address);
        user_number = BMSPrefs.getString(BookTableActivity.this, Constants.USER_NUMBER);
        device_token = BMSPrefs.getString(BookTableActivity.this, Constants.DEVICE_TOKEN);
        connectionDetector = new ConnectionDetector(getApplicationContext());
        img_back = findViewById(R.id.img_back);
        edt_date = findViewById(R.id.edt_date);
        edt_time = findViewById(R.id.edt_time);
        edt_person = findViewById(R.id.edt_person);
        edt_name = findViewById(R.id.edt_name);
        edt_address = findViewById(R.id.edt_address);
        btn_booktable = findViewById(R.id.btn_booktable);
        edt_number = findViewById(R.id.edt_number);
        text_edit = findViewById(R.id.text_edit);
        main_layout = findViewById(R.id.main_layout);
        img_bookmark = findViewById(R.id.img_bookmark);
        img_notification = findViewById(R.id.img_notification);
        edt_time.setOnClickListener(this);
        img_back.setOnClickListener(this);
        edt_date.setOnClickListener(this);
        btn_booktable.setOnClickListener(this);
        text_edit.setOnClickListener(this);
        main_layout.setOnClickListener(this);
        img_bookmark.setOnClickListener(this);
        img_notification.setOnClickListener(this);

        edt_person.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1 && s.toString().startsWith("0")) {
                    s.clear();
                }
            }
        });

    }

    // manage listener....
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back: {
                onBackPressed();
                break;
            }
            case R.id.edt_date: {
                datePicker();
                break;
            }
            case R.id.edt_time: {
                timePicker();
                break;
            }
            case R.id.btn_booktable: {
                CommonMethod.hideKeyBoard(BookTableActivity.this);
                String result = validateForm();
                if (result.equalsIgnoreCase("success")) {
                    isInternetPresent = connectionDetector.isConnectingToInternet();
                    if (isInternetPresent) {
                        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        try {
                            final_order_date_time = final_Date + " " + final_Time;
                            Date date = format.parse(final_order_date_time);
                            requestParam(date.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        CommonMethod.showAlertDialog(BookTableActivity.this, getString(R.string.internet_toast));
                    }
                } else {
                    CommonMethod.showAlertDialog(BookTableActivity.this, result);
                }
                break;
            }
            case R.id.text_edit: {
                edt_name.setEnabled(true);
                edt_address.setEnabled(true);
                edt_number.setEnabled(true);
                edt_name.setCursorVisible(true);
                edt_address.setCursorVisible(true);
                edt_number.setCursorVisible(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    edt_name.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.text_color));
                    edt_address.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.text_color));
                    edt_number.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.text_color));
                }
                break;
            }
            case R.id.main_layout: {
                edt_name.setEnabled(false);
                edt_address.setEnabled(false);
                edt_number.setEnabled(false);
                break;
            }
            case R.id.img_bookmark: {
                callActivity(new Intent(getApplicationContext(), WishlistActivity.class));
                break;
            }
            case R.id.img_notification: {
                callActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                break;
            }
        }
    }

    // handle backpress
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }


    // date picker
    private void datePicker() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(BookTableActivity.this, R.style.datepicker,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        edt_date.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }


    // time picker
    private void timePicker() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(BookTableActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                edt_time.setText(selectedHour + ":" + selectedMinute);
//                String sda=selectedHour + ":" + selectedMinute;
//                if(sda.compareTo(current_time)>=0) {
//                    edt_time.setText(selectedHour + ":" + selectedMinute);
//                }
//                else
//                {
//                    edt_time.setText("");
//                    CommonMethod.showToastShort("shdjkf",getApplicationContext());
//                }
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    // set data to edit text
    private void setData() {
        edt_name.setText(user_name);
        edt_address.setText(user_address);
        edt_number.setText(user_number);
    }

    // call activity method
    private void callActivityFinish(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    // call activity method
    private void callActivity(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }


    // validate form
    private String validateForm() {
        Persons = edt_person.getText().toString().trim();
        final_Date = edt_date.getText().toString().trim();
        final_Time = edt_time.getText().toString().trim();
        Name = edt_name.getText().toString().trim();
        Address = edt_address.getText().toString().trim();
        Mobile = edt_number.getText().toString().trim();
        if (Persons.equals("") || Persons.isEmpty()) {
            edt_person.setFocusable(true);
            edt_person.requestFocus();
            return getString(R.string.valid_person);
        }
        if (final_Date.equals("") || final_Date.isEmpty()) {
            edt_date.setFocusable(true);
            edt_date.requestFocus();
            return getString(R.string.valid_date);
        }
        if (final_Time.equals("") || final_Time.isEmpty()) {
            edt_time.setFocusable(true);
            edt_time.requestFocus();
            return getString(R.string.valid_time);
        }
        if (Name.equals("") || Name.isEmpty()) {
            edt_name.setFocusable(true);
            edt_name.requestFocus();
            return getString(R.string.valid_name);
        }
        if (Address.equals("") || Address.isEmpty()) {
            edt_address.setFocusable(true);
            edt_address.requestFocus();
            return getString(R.string.valid_address);
        }
        if (Mobile.isEmpty() || Mobile.length() < 6) {
            edt_number.setFocusable(true);
            edt_number.requestFocus();
            return getString(R.string.valid_phone);
        }
        return "success";
    }

    // request parameter
    private void requestParam(long date) {
        HashMap<String, String> params = new HashMap<>();
        params.put("order_timezone", "" + order_timezone);
        params.put("order_date_time", "" + date);
//        params.put("session_token", session_token);
        params.put("name", Name);
        params.put("address", Address);
        params.put("mobile", Mobile);
        params.put("restaurant_id", restaurant_id);
        params.put("no_of_person", Persons);
        params.put("device_token", device_token);
        params.put("device_type", "1");
        Log.d("param", params.toString());
        bookTablePresenter = new BookTablePresenter(this);
        bookTablePresenter.requestBookTable(params);
    }

    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, BookTableActivity.this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    @Override
    public void setDataToViews(String status, String msg, String key) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("booktable")) {
            CommonMethod.showToastShort(msg, getApplicationContext());
            callActivityFinish(new Intent(getApplicationContext(), DishesRestaurentActivity.class));
        } else if (status.equalsIgnoreCase("0") && key.equalsIgnoreCase("invalid_session")) {
            BMSPrefs.putString(getApplicationContext(), Constants._ID, "");
            BMSPrefs.putString(getApplicationContext(), Constants.Seeker_id, "");
            CommonMethod.showToastShort(msg, getApplicationContext());
            callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
        } else {
            CommonMethod.showToastShort(msg, getApplicationContext());
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.d(TAG, throwable.getMessage());
    }
}
