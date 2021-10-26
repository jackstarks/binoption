package com.newoption.binatraderapps.ui.fragments

import android.app.Application
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.appsflyer.AppsFlyerLib
import com.newoption.binatraderapps.AppConstants
import com.newoption.binatraderapps.MyApplication
import com.newoption.binatraderapps.data.APIClient
import com.newoption.binatraderapps.preferences.PrefDatastoreRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import java.util.*

class SplashViewModel(application: Application) : AndroidViewModel(application) {
    private val tag = SplashViewModel::class.simpleName
    private var score: String = ""
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish
    private val _dataString = MutableLiveData<String>()
    val dataString: LiveData<String>
        get() = _dataString
    var datastoreRepository: PrefDatastoreRepository = PrefDatastoreRepository(getApplication())

    private fun getDataInfoModel(): LiveData<String> {
        Log.e(tag, "datainfomodel")
        runBlocking(Dispatchers.IO) {

            val status: String = datastoreRepository.status.first()
            val country = getUserCountry(getApplication())
            try {
                val response = APIClient.apiClient.getProjectList(status, country)
                // Check if response was successful.
                if (response.result.isNotEmpty()) {
                    score = response.result
                    Log.e(tag, "$score from server")
                    if (getApplication<MyApplication>().applicationContext
                            .packageName.contains(score, ignoreCase = true)
                    ) {

                        datastoreRepository.saveAnswer(score)
                        _dataString.postValue(score)
                    } else {
                        getDataInfoModel(score)
                    }
                } else {
                    Log.e(tag, "datainfomodel empty response")
                    _eventGameFinish.postValue(true)
                }
            } catch (e: Exception) {
                Log.e(tag, "datainfomodel"+Log.getStackTraceString(e))
                _eventGameFinish.postValue(true)
            }
        }

        return dataString
    }


    private fun getDataInfoModel(urlString: String): LiveData<String> {
        Log.e(tag, "datainfomodel2")
        Log.e(
            tag, getApplication<MyApplication>().applicationContext
                .packageName
        )
        runBlocking(Dispatchers.IO) {

            try {

                var res = urlString
                val appsFlyerId = AppsFlyerLib.getInstance()
                    .getAppsFlyerUID(getApplication())
                res += appsFlyerId
                val status: String
                runBlocking(Dispatchers.IO) {
                    status = datastoreRepository.status.first()
                }
                if (status.isNotEmpty()) {
                    res += "&${AppConstants.STATUS_KEY}=$status"
                }
                val campaign: String
                runBlocking(Dispatchers.IO) {
                    campaign = datastoreRepository.campaign.first()
                }
                if (campaign.isNotEmpty()) {
                    val campaignStr = campaign.replace(" ", "_")
                    res += "&af_campaign=$campaignStr"
                    val parts = campaignStr.split("_")
                    if (parts.size > 1) {
                        res += "&" + AppConstants.BUYER_NAME_KEY + "=" + parts[1]
                    }
                }
                val adset: String
                runBlocking(Dispatchers.IO) {
                    adset = datastoreRepository.adset.first()
                }
                if (adset.isNotEmpty()) {
                    res += "&${AppConstants.ADSET_KEY}=$adset"
                }
                val adid: String
                runBlocking(Dispatchers.IO) {
                    adid = datastoreRepository.adid.first()
                }
                if (adid.isNotEmpty()) {
                    res += "&${AppConstants.AD_ID_KEY}=$adid"
                }
                val channel: String
                runBlocking(Dispatchers.IO) {
                    channel = datastoreRepository.channel.first()
                }
                if (channel.isNotEmpty()) {
                    res += "&${AppConstants.CHANNEL_KEY}=$channel"
                }
                val userId: String
                runBlocking(Dispatchers.IO) {
                    userId = datastoreRepository.userId.first()
                }
                if (userId.isNotEmpty()) {
                    res += "&${AppConstants.USER_ID_KEY}=$userId"
                }
                val userCountry = getUserCountry(getApplication())
                if (userCountry.isNotEmpty()) {
                    res += "&${AppConstants.USER_COUNTRY_KEY}=$userCountry"
                }
                val installReferrer: String
                runBlocking(Dispatchers.IO) {
                    installReferrer = datastoreRepository.installReferrer.first()
                }
                if (installReferrer.isNotEmpty()) {
                    res += "&${AppConstants.INSTALL_REFERRER_KEY}=$installReferrer"
                }
                val hashMap: HashMap<String, String>
                runBlocking(Dispatchers.IO) {
                    hashMap = datastoreRepository.param.first()
                }
                hashMap.let { cvData ->
                    cvData.map {
                        res += "&" + it.key.replace(" ", "_") + "=" + it.value.replace(" ", "_")
                    }
                }
                Log.e(tag, "checked " + res)
                val response = APIClient.apiClient2.getProjectListUrl(res)
                // Check if response was successful.
                Log.e(tag, response.isSuccessful.toString())

                if (response.isSuccessful) {
                    if (response.body()?.result?.isNotEmpty() == true) {
                        score = response.body()?.result!!
                        datastoreRepository.saveAnswer(score)
                        Log.e(tag, "$score from server2")
                        _dataString.postValue(score)
                    } else {
                        Log.e(tag, "empty2")
                        score = urlString
                        datastoreRepository.saveAnswer(score)
                        _dataString.postValue(score)
                    }
                }
                else{
                    Log.e(tag, "empty3")
                    score = urlString
                    datastoreRepository.saveAnswer(score)
                    _dataString.postValue(score)
                }
            } catch (e: Exception) {
                Log.e("datainfomodel2", Log.getStackTraceString(e))
                score = urlString
                datastoreRepository.saveAnswer(score)
                _dataString.postValue(score)
                //_eventGameFinish.postValue(true)
            }
        }

        return dataString


    }

    init {

        runBlocking(Dispatchers.IO) {
            score = datastoreRepository.answer.first()
        }
        if (score.isNotEmpty()) {
            Log.e(tag, score + "from pref")
            _dataString.postValue(score)
        } else {
            viewModelScope.launch {
                delay(10000)
                checkDataExist()
            }
        }
    }

    fun checkDataExist() {
        runBlocking(Dispatchers.IO) {
            score = datastoreRepository.answer.first()
        }
        if (score.isEmpty()) {
            getDataInfoModel()
        }

    }


    fun getUserCountry(context: Context): String {
        try {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val simCountry = tm.simCountryIso
            if (simCountry != null) { // SIM country code is available
                return simCountry.uppercase(Locale.US)
            } else if (tm.phoneType != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                val networkCountry = tm.networkCountryIso
                if (networkCountry != null) { // network country code is available
                    return networkCountry.uppercase(Locale.US)
                }
            }
        } catch (e: Exception) {
            Log.e(tag, Log.getStackTraceString(e))
        }
        return ""
    }

    fun getDeeplink(intent: Intent): String {
        if (intent.dataString != null) {
            val value = intent.dataString!!
            if (value.isNotEmpty()) {
                var deeplink = value
                if (deeplink.contains("?")) {
                    deeplink = deeplink.substring(deeplink.indexOf("?"))
                    deeplink = deeplink.replaceFirst("?", "&")
                }
                if (deeplink.contains("&target_url", true)) {
                    deeplink = deeplink.substring(0, deeplink.indexOf("&target_url"))
                }
                //SharedPref(getApplication()).saveDeeplink(deeplink)
                GlobalScope.launch {
                    datastoreRepository.saveDeeplink(deeplink)
                }
            }
        }
        val deeplink: String
        runBlocking(Dispatchers.IO) {
            deeplink = datastoreRepository.deeplink.first()
        }
        if (deeplink.isNotEmpty()) {
            return "&deep=yes$deeplink"
        }
        return "&deep=no"
    }


    fun onClick(v: View?) {

    }
}







