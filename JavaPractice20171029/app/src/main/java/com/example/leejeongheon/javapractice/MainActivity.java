package com.example.leejeongheon.javapractice;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.leejeongheon.javapractice.R.id.downButton;
import static com.example.leejeongheon.javapractice.R.styleable.View;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    GoogleMap mMap;
    MarkerOptions markerOptions = new MarkerOptions();
    MarkerOptions markerOptions2 =new MarkerOptions();
    double LatLng1x = 37.52487;
    double LatLng1y = 126.92723;
    double areaCircleX = 37.52487;
    double areaCircleY = 126.92723;
    double areaCircleRadius = 500;
    LatLng car1Position = new LatLng(LatLng1x,LatLng1y);//변수 넘겨서 변화시키니까 안됩니당
    LatLng areaCirclePosition = new LatLng(areaCircleX,areaCircleY);
    Marker car1;
    Circle areaCircle;
    Circle car1Circle;


    public double getDistance(double ax, double ay, double bx, double by ){
        return Math.sqrt(Math.abs(Math.pow((bx-ax),2)+ Math.pow((by-ay),2)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //supportMapFragement 를 통해 레이아웃에 만든  fragment의  ID를 참조하고 구글맵을 호출한다.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Button upButton = (Button) findViewById(R.id.upButton);
        Button leftButton = (Button) findViewById(R.id.leftButton);
        Button downButton = (Button) findViewById(R.id.downButton);
        Button rightButton = (Button) findViewById(R.id.rightButton);

        Button.OnClickListener listner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId())
                {
                    case R.id.upButton:

                        LatLng1x += 0.001;
                        car1.setPosition(new LatLng(LatLng1x,LatLng1y));
                        car1Circle.setCenter(new LatLng(LatLng1x,LatLng1y));
                        //car1.setVisible(false);
                        car1.setTitle("바뀐후");
                        car1.setSnippet("바뀐후");
                        car1.showInfoWindow();
                        Log.v("if","english");



                        break;

                    case R.id.downButton:
                        LatLng1x -= 0.001;

                        car1.setPosition(new LatLng(LatLng1x,LatLng1y));
                        car1Circle.setCenter(new LatLng(LatLng1x,LatLng1y));
                        Log.v("downButton","메세지");
                        break;

                    case R.id.leftButton:
                        LatLng1y -= 0.001;
                        car1.setPosition(new LatLng(LatLng1x,LatLng1y));
                        car1Circle.setCenter(new LatLng(LatLng1x,LatLng1y));
                        break;

                    case R.id.rightButton:
                        LatLng1y += 0.001;
                        car1.setPosition(new LatLng(LatLng1x,LatLng1y));
                        car1Circle.setCenter(new LatLng(LatLng1x,LatLng1y));
                        break;

                }
                double distance = distanceLatLngToMeter(areaCircleX,areaCircleY,LatLng1x,LatLng1y,"meter");
                Log.i("distance",String.valueOf(distance));
                Log.i("areaCircleRadius",String.valueOf(areaCircleRadius));
                if (distance<areaCircleRadius)
                {
                    car1.setTitle("inside");
                    car1.showInfoWindow();
                    Log.i("inside","나오나");

                } else {
                    car1.setTitle("outside");
                    car1.showInfoWindow();
                    Log.i("outside","나오자");

                }

            }
        };

        upButton.setOnClickListener(listner);
        downButton.setOnClickListener(listner);
        leftButton.setOnClickListener(listner);
        rightButton.setOnClickListener(listner);



    }
    @Override
    public void onMapReady(GoogleMap googleMap) {//onCreate()에사 gepMapAsync()를 통헤 onMapReady()가 자동호출 되면서 작업 수행

        mMap = googleMap;

        LatLng seoul = new LatLng(37.52487, 126.92723);
//        makerOptions.position(seoul)
//
//                .title("여의도!")
//                .snippet("여의도 한강 치맥")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
//                .alpha(0.5f);
        MarkerOptions car1Marker = new MarkerOptions();///마커 옵션지정
               car1Marker.position(new LatLng(37.52487, 126.92723))
                    .draggable(true)
                     .title("바뀌기전")
                        .snippet("ahffk");
        car1 = mMap.addMarker(car1Marker);//만든 마커의 의름을  car1이라 하는데 mMap 자리에 googleMap쓰는 거랑 차이를 모르겠습니다
        car1.showInfoWindow();//이게 있어야 클릭하지 않아도 title이 뜹니다




        //mMap.addMarker(markerOptions2);

        mMap.setOnMarkerClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);


        areaCircle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(areaCircleX,areaCircleY))
                .radius(areaCircleRadius)
                .strokeWidth(10)
                .strokeColor(Color.GREEN)
                .fillColor(Color.argb(128, 255, 0, 0))
                .clickable(true));
                Log.v("areacircle","message");

        car1Circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(LatLng1x, LatLng1y))
                .radius(100)
                .strokeWidth(2)
                .strokeColor(Color.GREEN)
                .fillColor(Color.argb(128, 255, 0, 0))
                .clickable(true));


    }
    /**
     * 두 지점간의 거리 계산
     *
     * @param lat1 지점 1 위도
     * @param lon1 지점 1 경도
     * @param lat2 지점 2 위도
     * @param lon2 지점 2 경도
     * @param unit 거리 표출단위
     * @return
     */
    private static double distanceLatLngToMeter(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }

        return (dist);
    }
    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }



@Override
    public boolean onMarkerClick(Marker marker) {
    Toast.makeText(this, marker.getTitle() + "\n" + marker.getPosition(), Toast.LENGTH_SHORT).show();
    return true;

}

}
