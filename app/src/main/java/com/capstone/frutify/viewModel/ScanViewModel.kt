package com.capstone.frutify.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.frutify.model.SaveScanResponse
import com.capstone.frutify.model.ScanData
import com.capstone.frutify.model.ScanDataResponse
import com.capstone.frutify.network.ApiInstance
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ScanViewModel: ViewModel() {

    private val _scanData = MutableLiveData<List<ScanData>>()
    val scanData: LiveData<List<ScanData>> = _scanData

    private  val _uploadStatus = MutableLiveData<Result<String>>()
    val uploadStatus: LiveData<Result<String>> = _uploadStatus

    fun uploadScanData(
        userId: Int,
        fruitName: String,
        fruitCondition: String,
        fruitWeight: Double,
        nutritionInfo: String,
        fileUri: Uri
    ) {
        viewModelScope.launch {

            val userIdBody = RequestBody.create("text/plain".toMediaTypeOrNull(), userId.toString())
            val fruitNameBody = RequestBody.create("text/plain".toMediaTypeOrNull(), fruitName)
            val fruitConditionBody = RequestBody.create("text/plain".toMediaTypeOrNull(), fruitCondition)
            val fruitWeightBody = RequestBody.create("text/plain".toMediaTypeOrNull(), fruitWeight.toString())
            val nutritionInfoBody = RequestBody.create("text/plain".toMediaTypeOrNull(), nutritionInfo)

            // Convert Uri to File
            val file = File(fileUri.path!!)
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)

            try {
                ApiInstance.api.saveScanData(userIdBody,
                    fruitNameBody,
                    fruitConditionBody,
                    fruitWeightBody,
                    nutritionInfoBody,
                    filePart).enqueue(object : Callback<SaveScanResponse> {
                    override fun onResponse(
                        call: Call<SaveScanResponse>,
                        response: Response<SaveScanResponse>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                _uploadStatus.postValue(Result.success(it.message))
                            } ?: run {
                                _uploadStatus.postValue(Result.failure(Throwable("Empty response body")))
                            }
                        }
                        else {
                            val errorMessage = response.errorBody()?.string() ?: "Unknown error occurred"
                            _uploadStatus.postValue(Result.failure(Throwable(errorMessage)))
                        }
                    }
                    override fun onFailure(call: Call<SaveScanResponse>, t: Throwable) {
                        _uploadStatus.postValue(Result.failure(t))
                    }
                })
            } catch (e: Exception) {
                Log.e("ScanViewModel", "Error uploading data: ${e.message}")
                _uploadStatus.postValue(Result.failure(e))
            }
        }
    }

    fun fetchScanData() {
        viewModelScope.launch {
            try {
                ApiInstance.api.getScanData().enqueue(object : Callback<ScanDataResponse> {
                    override fun onResponse(
                        call: Call<ScanDataResponse>,
                        response: Response<ScanDataResponse>
                    ) {
                        _scanData.value = response.body()?.data
                        Log.d("ScanViewModel", "Scan data fetched successfully")

                    }
                    override fun onFailure(call: Call<ScanDataResponse>, t: Throwable) {
                        Log.e("ScanViewModel", "Error fetching data: ${t.message}")
                    }
                })
            }catch (e: Exception) {
                Log.e("ScanViewModel", "Error fetching data: ${e.message}")
            }
        }
    }
}