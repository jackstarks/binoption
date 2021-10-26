package com.newoption.binatraderapps.ui.fragments

import android.app.Application
import android.content.res.Resources
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.newoption.binatraderapps.MyApplication
import com.newoption.binatraderapps.R
import com.newoption.binatraderapps.model.PostRepository
import com.newoption.binatraderapps.model.User

import java.util.*
import kotlin.collections.ArrayList


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = MainViewModel::class.simpleName
    private var score: String = ""

    private val _eventGameFinish = MutableLiveData<Int>()
    val eventGameFinish: LiveData<Int>
        get() = _eventGameFinish
    private val _dataString = MutableLiveData<String>()
    val dataString: LiveData<String>
        get() = _dataString
    private var _users = MutableLiveData<MutableList<User>>()
    val users: LiveData<MutableList<User>>
        get() = _users


    init {
        updateUsers()
    }


    fun onClick(v: View?) {
        if (v?.id == R.id.iv_back) {

            _eventGameFinish.postValue(-2)
        }
        if (v?.id == R.id.leftImage) {
            _eventGameFinish.postValue(-1)
        }
        if (v?.id == R.id.rightImage) {
            _eventGameFinish.postValue(1)

        }
    }
    fun itemClick(item:User){
        Toast.makeText(getApplication(),item.body,Toast.LENGTH_LONG).show()
    }
    fun clearTouch() {
        _eventGameFinish.postValue(0)
    }

    fun updateUsers() {
        val users: MutableList<User> =
            ArrayList()
        val data = PostRepository().mainData
        val data2 = PostRepository().mainData2
        val res: Resources = getApplication<MyApplication>().resources
        val random = Random(System.currentTimeMillis());
        val titlesArr = res.getStringArray(R.array.titles)
        val bodiesArr = res.getStringArray(R.array.bodies)

        for (i in 0..data.size - 1) {
            users.add(User(data[i], titlesArr[i], bodiesArr[i],data2[i]))
        }
        this._users.postValue(users);
    }


}











