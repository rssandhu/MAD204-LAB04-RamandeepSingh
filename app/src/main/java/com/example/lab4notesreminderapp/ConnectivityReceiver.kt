package com.example.lab4notesreminderapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast

/**
 * BroadcastReceiver that listens for connectivity changes and logs/toasts status.
 */
class ConnectivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val isConnected = isNetworkAvailable(context)
        val message = if (isConnected) "Network available" else "Network lost"
        Log.d("ConnectivityReceiver", message)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Checks if device has active internet connection.
     * @param context Application context
     * @return True if connected, else false.
     */
    private fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = cm.activeNetwork ?: return false
            val capabilities = cm.getNetworkCapabilities(network)
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true ||
                    capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
        } else {
            val networkInfo = cm.activeNetworkInfo
            networkInfo != null && networkInfo.isConnected
        }
    }
}
