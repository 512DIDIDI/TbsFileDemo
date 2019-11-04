package com.dididi.packrat.ui.collect

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dididi.packrat.PackRatApp
import com.dididi.packrat.data.CollectRepository
import com.dididi.packrat.data.model.collect.Collect
import kotlinx.coroutines.launch


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 27/10/2019
 * @describe 收藏页面的viewModel
 */

class CollectViewModel(private val collectRepository: CollectRepository) : ViewModel() {

    //收藏list的LiveData
    lateinit var collectLiveData: LiveData<List<Collect>>
    //数据是否改变
    val dataChangeLiveData = MutableLiveData<Int>()
    //是否加载中
    val isLoading = MutableLiveData<Boolean>()
    //收藏list，供ui adapter刷新数据
    val dataList = arrayListOf<Collect>()

    /**
     * ui获取数据
     */
    fun getCollects() {
        launch {
            //从数据依赖层读取数据
            collectLiveData = collectRepository.getCollects()
            dataList.addAll(collectLiveData.value!!)
        }
    }

    fun setCollects(collects:List<Collect>){
        launch {
            collectRepository.setCollects(collects)
        }
    }

    /**
     * launch函数处理数据前后的流程，加载 清除数据 刷新liveData源数据
     */
    private fun launch(block: suspend () -> Unit) = viewModelScope.launch {
        try {
            isLoading.value = true
            dataList.clear()
            block()
            dataChangeLiveData.value?.plus(1)
            isLoading.value = false
        } catch (t: Throwable) {
            t.printStackTrace()
            Toast.makeText(PackRatApp.context, t.message, Toast.LENGTH_SHORT).show()
            dataChangeLiveData.value?.plus(1)
            isLoading.value = false
        }
    }
}