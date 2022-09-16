package com.decagonhq.decapay.common.utils.errorhelper

import android.content.Context
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.data.model.errormodel.ErrorSent
import com.google.gson.Gson
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ExceptionHandler @Inject constructor(context: Context) {

    private val context = context
    fun parse(exception: Exception): String {
        return when (exception) {
            is HttpException -> {
                return try {
                    var errorMessage = ""
                    val obj = Gson().fromJson(
                        exception.response()?.errorBody()?.string(),
                        ErrorSent::class.java
                    )

                    errorMessage += if (obj.message != null) "${obj.message}\n" else ""
                    errorMessage += if (obj.status != null) "${obj.status}\n" else ""
                    if (obj.subErrors != null) {
                        for (value in obj.subErrors) {
                            errorMessage += "${value?.message}"
                        }
                    }
                    return errorMessage.trim()
                } catch (exp: Exception) {
                    context.getString(R.string.error_exception_unknown_error)
                }
            }
            is UnknownHostException -> context.getString(R.string.error_exception_network_error)
            is SocketTimeoutException -> context.getString(R.string.error_exception_time_out_error)
            else -> exception.message ?: context.getString(R.string.error_exception_unknown_error)
        }
    }
}
