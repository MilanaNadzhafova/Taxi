package com.example.projecttaxi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainer;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.FrameMetrics;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.maps.model.PolylineOptions;

import java.sql.SQLData;
import java.util.concurrent.TimeUnit;

public class GPSForm extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    ImageView picMenu;
    View Map;
    View Lay;
    CardView cardHistory;
    CardView cardSettings;
    CardView cardProfile;
    TableLayout CarBlock;
    int touch =1;
    Button btnBook;


    static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 23;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_p_s_form);
        checkPermission();
        CarBlock = (TableLayout)findViewById(R.id.CarBlock);
        Map = (View)findViewById(R.id.map);
        Lay = (View) findViewById(R.id.firstLayout);
        Lay.setVisibility(View.GONE);
        btnBook = (Button)findViewById(R.id.btnBook);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GPSForm.this,WaitTimer.class);
                startActivity(intent);
            }
        });
        cardProfile = (CardView)findViewById(R.id.cardForProfile);
        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationStartEnd();
                picMenu.setVisibility(View.VISIBLE);
                ObjectAnimator y= ObjectAnimator.ofFloat(picMenu, "alpha", 0f,1f );
                y.setDuration(2000);
                y.start();
            }
        });
        cardHistory = (CardView)findViewById(R.id.cardHistory);
        cardHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GPSForm.this,History.class);
                startActivity(intent);
            }
        });
        cardSettings = (CardView)findViewById(R.id.cardSettings);
        cardSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GPSForm.this,SettingsForm.class);
                startActivity(intent);
            }
        });
        picMenu = (ImageView)findViewById(R.id.picMenu);
        picMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationStart();
                picMenu.setVisibility(View.GONE);
                Lay.setVisibility(View.VISIBLE);
            }
        });

    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);
                } else {

                }
                return;
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }
        LatLng latlng=new LatLng(54.983021,  82.890261);
        CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(
                latlng, 17);
        mMap.animateCamera(cameraUpdate);
        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(54.984106, 82.890640)).icon(
                        BitmapDescriptorFactory.fromResource(R.drawable.carmargenta)));
                DrawLine();
                picMenu.setVisibility(View.GONE);
                if(touch==1)
                {
                    DurationCarBlock();
                    touch++;
                }

                return false;
            }
        });
        /*mMap.setOnMyLocationChangeListener(location -> {
            LatLng latlng=new LatLng(location.getLatitude(),location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latlng);
            markerOptions.title("My Marker");
            mMap.clear();
            CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(
                    latlng, 15);
            mMap.animateCamera(cameraUpdate);
            mMap.addMarker(markerOptions);
            LoadCars();
        });
        */

        LoadCars();
    }
    public void LoadCars() {
        Marker marker;

        mMap.addMarker(new MarkerOptions().position(new LatLng(54.983021,  82.890261)).icon(
                BitmapDescriptorFactory.defaultMarker()));
       mMap.addMarker(new MarkerOptions().position(new LatLng(54.983794, 82.890136)).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.car)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(54.985045, 82.893110)).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.car1)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(54.984195, 82.889937)).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.car2)));

    }
    public void DrawLine()
    {
        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(54.984115, 82.890638)).radius(5)
                .fillColor(Color.RED).strokeColor(Color.RED)
                .strokeWidth(5);
        mMap.addCircle(circleOptions);
        CircleOptions circleOptions1 = new CircleOptions()
                .center(new LatLng(54.986828, 82.889919)).radius(5)
                .fillColor(Color.BLUE).strokeColor(Color.BLUE)
                .strokeWidth(5);
        mMap.addCircle(circleOptions1);
        PolylineOptions polylineOptions = new PolylineOptions()
                .add(new LatLng(54.984115, 82.890638)).add(new LatLng(54.985328, 82.892671))
                .add(new LatLng(54.985328, 82.892671)).add(new LatLng(54.986828, 82.889919))
                .color(Color.MAGENTA).width(30);
        mMap.addPolyline(polylineOptions);
    }
    private void AnimationStart()
    {
        float translationX = Map.getTranslationX();
        float translationY = Map.getTranslationY();
        ObjectAnimator y= ObjectAnimator.ofFloat(Map, "translationY", translationY, translationY+420);
        ObjectAnimator x= ObjectAnimator.ofFloat(Map, "translationX", translationX, translationX+770);
        AnimatorSet as = new AnimatorSet();
        as.playTogether(x,y);
        as.setDuration(1000);
        as.start();
    }
    private void AnimationStartEnd()
    {
        float translationX = Map.getTranslationX();
        float translationY = Map.getTranslationY();
        ObjectAnimator y= ObjectAnimator.ofFloat(Map, "translationY", translationY, translationY-420);
        ObjectAnimator x= ObjectAnimator.ofFloat(Map, "translationX", translationX, translationX-770);
        AnimatorSet as = new AnimatorSet();
        as.playTogether(x,y);
        as.setDuration(1000);
        as.start();
    }
    private void DurationCarBlock()
    {
        float translationY = CarBlock.getTranslationY();
        ObjectAnimator y= ObjectAnimator.ofFloat(CarBlock, "translationY", translationY, translationY-1350);
        y.setDuration(1000);
        y.start();
    }



}