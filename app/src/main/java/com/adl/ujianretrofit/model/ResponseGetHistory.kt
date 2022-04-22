package com.adl.ujianretrofit.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetHistory(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("data")
	val data: DataHistory? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class DataHistory(

	@field:SerializedName("absen")
	val absen: List<AbsenItemHistory?>? = null
) : Parcelable

@Parcelize
data class AbsenItemHistory(

	@field:SerializedName("foto")
	val foto: String? = null,

	@field:SerializedName("tanggal_masuk")
	val tanggalMasuk: String? = null,

	@field:SerializedName("tanggal_keluar")
	val tanggalKeluar: String? = null,

	@field:SerializedName("lokasi_GPS")
	val lokasiGPS: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("username")
	val username: String? = null
) : Parcelable
