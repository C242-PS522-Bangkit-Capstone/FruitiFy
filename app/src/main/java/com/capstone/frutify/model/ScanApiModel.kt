package com.capstone.frutify.model

data class SaveScanResponse(
    val message: String,
)

data class ScanDataResponse(
    val message: String,
    val data: List<ScanData>
)

data class ScanData(
    val data_id: Int,
    val user_id: Int,
    val fruit_name: String,
    val fruit_image_url: String,
    val scan_date: String,
    val fruit_condition: String,
    val fruit_weight: String,
    val nutrition_info: String,
    val created_at: String,
    val updated_at: String,
)