package com.gmail.wondergab12.bank2

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val REQUEST_FINE_LOCATION = 0

class MapFragment : Fragment(), OnMapReadyCallback {

    interface Callbacks

    private var callbacks: Callbacks? = null
    private var map: GoogleMap? = null
    private lateinit var mapFragment: SupportMapFragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        this.map = map
        prepareMap()
    }

    private fun prepareMap() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is already available, do work here.
            map?.isMyLocationEnabled = true
            // Get most accurate location
            val locationManager =
                requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val providers: List<String> = locationManager.getProviders(true)
            var mostAccurate: Location? = locationManager.getLastKnownLocation(providers[0])
            for (i in 1 until providers.size) {
                val location: Location? = locationManager.getLastKnownLocation(providers[i])
                mostAccurate?.let { most ->
                    location?.let { loc ->
                        if (most.accuracy < loc.accuracy) {
                            mostAccurate = loc
                        }
                    }
                }
            }
            // Zoom map to user location
            mostAccurate?.let {
                val latLng = LatLng(it.latitude, it.longitude)
                map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.88F))
            }

            mockFunction()

        } else {
            // Permission has not be granted. Request for permission.
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_FINE_LOCATION
            )
        }
    }

    private fun mockFunction() {
        Repository.init(requireActivity().applicationContext)
        val repository: Repository = Repository.getInstance()
        repository.listATM(" ", "USD").enqueue(object : Callback<List<Atm>> {
            override fun onResponse(call: Call<List<Atm>>, response: Response<List<Atm>>) {
                response.body()?.let { list ->
                    for (atm in list) {
                        Log.d("MY_TAG", atm.toString())
                    }
                }
            }

            override fun onFailure(call: Call<List<Atm>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_FINE_LOCATION) {
            // Check if the only required permission has been granted.
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted, we can do work.
                prepareMap()
            } else {
                // Permission has not be granted, we cant do work here.
                mapFragment.view?.visibility = View.GONE
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object {
        fun newInstance() = MapFragment()
    }
}
