package com.digitaldestino.fragment;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.digitaldestino.R;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.utils.GPSTracker;
import com.digitaldestino.utils.HttpConnection;
import com.digitaldestino.utils.PathJSONParser;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */

public class Map_Fragment extends Fragment implements OnMapReadyCallback {
    private View view;
    private EditText edt_location;
    private Geocoder geocoder;
    private double Latitude, Longitude;
    private GPSTracker gpsTracker;
    GoogleMap mMap;
    String address;
    LatLng currentLatLng, aptLatLng;
    private double restaurant_latitude, restaurant_longitude;
    // SupportMapFragment mapFragment;

    public Map_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_map_, container, false);
        findView();
        return view;
    }

    private void findView() {
        restaurant_latitude = Double.valueOf(BMSPrefs.getString(getContext(), Constants.RESTAURANT_LATITUDE));
        restaurant_longitude = Double.valueOf(BMSPrefs.getString(getContext(), Constants.RESTAURANT_LONGITUDE));
        gpsTracker = new GPSTracker(getContext());
        try {
            getCurrentLocation();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.g_map);
        mapFragment.getMapAsync(this);
    }

    // get current location
    private void getCurrentLocation() throws IOException {
        Latitude = gpsTracker.getLatitude();
        Longitude = gpsTracker.getLongitude();
        List<Address> addresses;
        geocoder = new Geocoder(getContext(), Locale.getDefault());
        addresses = geocoder.getFromLocation(Latitude, Longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(Latitude, Longitude);
//        LatLng sydneyq = new LatLng(28.5398, 77.3973);
////        MarkerOptions markerOptions = new MarkerOptions();
////        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_grid));
//        mMap.addMarker(new MarkerOptions().position(sydney).title(address));
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(sydney)      // Sets the center of the map to Mountain View
//                .zoom(17)                   // Sets the zoom
//                //  .bearing(90)                // Sets the orientation of the camera to east
//                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
//                .build();
//
//        mMap.addMarker(new MarkerOptions().position(sydneyq).title("" + 28.5398));
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.getUiSettings().setZoomControlsEnabled(true);
//        mMap.getUiSettings().setAllGesturesEnabled(true);

        currentLatLng = new LatLng(Latitude, Longitude);

        // aptLatLng = new LatLng(28.5205, 77.2789);
        aptLatLng = new LatLng(restaurant_latitude, restaurant_longitude);


        String url = getMapsApiDirectionsUrl();
        ReadTask downloadTask = new ReadTask();
        downloadTask.execute(url);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 13));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(aptLatLng, 13));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        addMarkers();
    }

    private String getMapsApiDirectionsUrl() {

        // Origin of route
        String str_origin = "origin=" + currentLatLng.latitude + "," + currentLatLng.longitude;

        // Destination of route
        String str_dest = "destination=" + aptLatLng.latitude + "," + aptLatLng.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode + "&key=AIzaSyC1fO6VCCx5bZHebBlVWW8MNEt2kBTBov8";

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    private void addMarkers() {
        if (mMap != null) {

            mMap.addMarker(new MarkerOptions()
                    .position(currentLatLng)
                    .anchor(0.5f, 0.5f)
                    .title("User Address")
                    //.snippet("Snippet3")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_icon)));

            mMap.addMarker(new MarkerOptions()
                    .position(aptLatLng)
                    .anchor(0.5f, 0.5f)
                    .title("Restaurant Address")
                    //.snippet("Snippet3")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.path)));

        }
    }

    private class ReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                HttpConnection http = new HttpConnection();
                data = http.readUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            new ParserTask().execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(
                String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                PathJSONParser parser = new PathJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
            ArrayList<LatLng> points = null;
            PolylineOptions polyLineOptions = new PolylineOptions();

            // traversing through routes
            for (int i = 0; i < routes.size(); i++) {
                points = new ArrayList<LatLng>();
                //   polyLineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = routes.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                polyLineOptions.addAll(points);
                polyLineOptions.width(10);
                polyLineOptions.color(Color.RED);
            }

            mMap.addPolyline(polyLineOptions);
        }
    }


}
