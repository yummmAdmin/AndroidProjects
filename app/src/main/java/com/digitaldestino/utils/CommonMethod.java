package com.digitaldestino.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.digitaldestino.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonMethod {
    public static ProgressDialog showProgressDialog(ProgressDialog mProgressDialog, Context context, String title) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.setMessage(title);
        mProgressDialog.show();
        return mProgressDialog;
    }

    public static void hideProgressDialog(ProgressDialog mProgressDialog) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    //**************** it is used for validate email address***************//
    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        String expression = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
      //  String expression = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }

        return isValid;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern emailPattern = Pattern
                .compile("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$");
        Matcher m = emailPattern.matcher(phoneNumber);
        return m.matches();
    }

    public static boolean isValidName(String name) {
        Pattern namePattern = Pattern
                .compile("[a-zA-Z][a-zA-Z ]*");
        Matcher m = namePattern.matcher(name);
        return m.matches();
    }

    public static void hideKeyBoard(Context context) {
        InputMethodManager manager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            if (manager != null) {
                manager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception ex) {
        }

    }


    /* public static boolean isValidPassword(final String password) {
         Pattern pattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,})");
         Matcher matcher = pattern.matcher(password);

         return matcher.matches();
     }*/
    public static boolean isValidPassword(final String password) {
        Pattern pattern = Pattern.compile("((?=.*\\d)(?=.*[a-z]).{6,})");
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public static void showAlertWithNoButtons(Context context, String title, String msg) {
        new AlertDialog.Builder(context)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(msg)
                .show();
    }

    public static void alertErrorAndExit(Context context, String msg) {
        AlertDialog.Builder d = new AlertDialog.Builder(context);
        d.setCancelable(false);
        d.setTitle("Oppps!");
        d.setMessage(msg + "!\n" + "App will be terminated!");
        d.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                android.os.Process.killProcess(android.os.Process.myPid()); //exit app (by killing its process)
            }
        });
        d.show();
    }

    public static void showAlert(Context context, String title, String string) {
        // TODO Auto-generated method stub
        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(string);
        alert.setCancelable(false);
        alert.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    public static void showAlertDialog(Context context, String title) {
        new AwesomeErrorDialog(context)
                .setTitle(R.string.app_name)
                .setMessage(title)
                .setColoredCircle(R.color.dialogErrorBackgroundColor)
                .setDialogIconAndColor(R.drawable.ic_dialog_error, R.color.white)
                .setCancelable(true).setButtonText(context.getResources().getString(R.string.dialog_ok_button))
                .setButtonBackgroundColor(R.color.dialogErrorBackgroundColor)
                .setButtonText(context.getResources().getString(R.string.dialog_ok_button))
                .setErrorButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        // click
                    }
                })
                .show();
    }


    //**************** it is used for make short length toast***************//
    public static void showToastShort(String text, Context context) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();

    }


    //**************** it is used for make long length toast***************//
    public static void showToastlong(String text, Context context) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

//    public void displayMessage(View view, String toastString) {
//        Snackbar.make(view, toastString, Snackbar.LENGTH_SHORT)
//                .setAction("Action", null).show();
//    }

    public static Date getDate(String sDate) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getTime(String sTime) {
        try {
            return new SimpleDateFormat("hh:mm aa").parse(sTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(src).openConnection();
            connection.setDoInput(true);
            connection.connect();
            return BitmapFactory.decodeStream(connection.getInputStream());
        } catch (IOException e) {
            return null;
        }
    }

    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, 0);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


    public static byte[] getBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static void callActivity(Intent intent, Context context) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    // call activity method
    public static void callNearBySelect(Context context, LinearLayout text, TextView text_nearby, ImageView img_nearby) {
        text.setBackgroundColor(Color.parseColor("#E83B33"));
        img_nearby.setBackground(context.getResources().getDrawable(R.drawable.nearby_select));
        text_nearby.setTextColor(context.getResources().getColor(R.color.white));
    }

    // call activity method
    public static void callNearByUnselect(Context context, LinearLayout text, TextView text_nearby, ImageView img_nearby) {
        text.setBackgroundColor(Color.parseColor("#ffffff"));
        img_nearby.setBackground(context.getResources().getDrawable(R.drawable.nearby));
        text_nearby.setTextColor(context.getResources().getColor(R.color.text_color));
    }

    // call activity method
    public static void callSalesSelect(Context context, LinearLayout text, TextView text_nearby, ImageView img_nearby) {
        text.setBackgroundColor(Color.parseColor("#E83B33"));
        img_nearby.setBackground(context.getResources().getDrawable(R.drawable.bysale_select));
        text_nearby.setTextColor(context.getResources().getColor(R.color.white));
    }

    // call activity method
    public static void callSalesUnselect(Context context, LinearLayout text, TextView text_nearby, ImageView img_nearby) {
        text.setBackgroundColor(Color.parseColor("#ffffff"));
        img_nearby.setBackground(context.getResources().getDrawable(R.drawable.bysale));
        text_nearby.setTextColor(context.getResources().getColor(R.color.text_color));
    }

    // call activity method
    public static void callRestaurentSelect(Context context, LinearLayout text, TextView text_nearby, ImageView img_nearby) {
        text.setBackgroundColor(Color.parseColor("#E83B33"));
        img_nearby.setBackground(context.getResources().getDrawable(R.drawable.rest_select));
        text_nearby.setTextColor(context.getResources().getColor(R.color.white));
    }

    // call activity method
    public static void callRestaurentUnselect(Context context, LinearLayout text, TextView text_nearby, ImageView img_nearby) {
        text.setBackgroundColor(Color.parseColor("#ffffff"));
        img_nearby.setBackground(context.getResources().getDrawable(R.drawable.rest));
        text_nearby.setTextColor(context.getResources().getColor(R.color.text_color));
    }

    // call activity method
    public static void callFilterSelect(Context context, LinearLayout text, TextView text_nearby, ImageView img_nearby) {
        text.setBackgroundColor(Color.parseColor("#E83B33"));
        img_nearby.setBackground(context.getResources().getDrawable(R.drawable.filter_select));
        text_nearby.setTextColor(context.getResources().getColor(R.color.white));
    }

    // call activity method
    public static void callFilterUnselect(Context context, LinearLayout text, TextView text_nearby, ImageView img_nearby) {
        text.setBackgroundColor(Color.parseColor("#ffffff"));
        img_nearby.setBackground(context.getResources().getDrawable(R.drawable.filter));
        text_nearby.setTextColor(context.getResources().getColor(R.color.text_color));
    }
}
