package com.example.frootshop

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.frootshop.MVVM.MainViewModel
import com.example.frootshop.Repository.AuthHelper

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.frootshop.databinding.ActivityHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.*

class HomeActivity : AppCompatActivity(), OnMapReadyCallback ,    GoogleMap.OnMarkerClickListener{
    var setup=false;
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityHomeBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.cartbtn.setOnClickListener(){
            startActivity(Intent(this,Cart::class.java))
            overridePendingTransition(R.anim.slidein, R.anim.slideout)
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.getUiSettings().setZoomControlsEnabled(true)
        map.setOnMarkerClickListener(this)
            CoroutineScope(Dispatchers.Main).launch {
                async {
                    while (!setup) {
                        setUpMap()
                        delay(10000)
                    }
                }
            }
       //AuthHelper.CurrentUser.loclatitude==0.0 && AuthHelper.CurrentUser.loclongitude==0.0
        // Add a marker in Sydney and move the camera
        val newlocation = LatLng(AuthHelper.CurrentUser.loclatitude!!, AuthHelper.CurrentUser.loclongitude!!)
        map.addMarker(MarkerOptions().icon(
            BitmapDescriptorFactory.fromBitmap(
            BitmapFactory.decodeResource(resources, R.mipmap.ic_user_location))).position(newlocation).title("Marker"))

    }
    override fun onBackPressed() {
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        if(p0.title!="You"){
            AuthHelper.SelectedShop=p0.title!!.toInt()
            startActivity(Intent(this,Shop::class.java))
            overridePendingTransition(R.anim.slidein, R.anim.slideout)
        }
        return false;
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        setup=true
        // 1
        map.isMyLocationEnabled = true

// 2
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            // 3
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                // Add a marker in Sydney and move the camera
                val newlocation = LatLng(AuthHelper.CurrentUser.loclatitude!!, AuthHelper.CurrentUser.loclongitude!!)
                map.addMarker(MarkerOptions().icon(
                    BitmapDescriptorFactory.fromBitmap(
                        BitmapFactory.decodeResource(resources, R.mipmap.ic_user_location))).position(newlocation).title("You"))
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                AuthHelper.CurrentUser.loclongitude=location.longitude
                AuthHelper.CurrentUser.loclatitude=location.latitude
                AuthHelper.UpdateUser()
                for(shop in AuthHelper.ShopsList){
                    map.addMarker(MarkerOptions().icon(
                        BitmapDescriptorFactory.fromBitmap(
                            BitmapFactory.decodeResource(resources, R.mipmap.mystoreicon))).position(
                        LatLng(shop.latloc,shop.longloc)
                    ).title(AuthHelper.ShopsList.indexOf(shop).toString()))
                }
            }
        }

    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

}