package com.mo.composepokemonapp.data.api

import org.json.JSONObject
import retrofit2.Response

// Generic sealed class wrapper for network calls
// differentiate between success and failure responses
// and for handling loading data states
// takes the body (data) of nullable generic type and nullable String response message
sealed class ResponseState<T>(
    var data: T? = null,
    var message: String? = null,
    var hasBeenHandled: Boolean = false,
) {

    // Success class returns a non nullable data which was fetched from retrofit
    // defines that the response was correct and data was sent
    class Success<T>(
        data: T,
        val lastStep: String? = null,
        hasBeenHandled: Boolean = false,
    ) : ResponseState<T>(data)

    // Error class returns a non nullable message which defines what when wrong
    // optionally can take data as a pram if needed
    class Error<T>(
        message: String?,
        data: T? = null,
        val lastStep: String? = null,
        hasBeenHandled: Boolean = false,
    ) : ResponseState<T>(data, message)

    class NotAuthorized<T>(data: T? = null, hasBeenHandled: Boolean = false) :
        ResponseState<T>(data, null)

    class NetworkError<T>(data: T? = null, hasBeenHandled: Boolean = false) :
        ResponseState<T>(data, null)

    class UnKnownError<T>(data: T? = null) : ResponseState<T>(data, null)

    // Loading class notifies when the call was done and response was received (success or error)
    class Loading<T> : ResponseState<T>()

    class Empty<T> : ResponseState<T>()
}

val ResponseState<*>.isLoading get() = this is ResponseState.Loading
val ResponseState<*>.isSuccess get() = this is ResponseState.Success
val ResponseState<*>.isError
    get() = this is ResponseState.Error<*> ||
            this is ResponseState.NetworkError<*> ||
            this is ResponseState.NotAuthorized<*> ||
            this is ResponseState.UnKnownError<*>

fun <T> ResponseState<T>.copy(
    data: T? = null,
    message: String? = null,
    hasBeenHandled: Boolean = false,
): ResponseState<T> {
    data?.let { this.data = it }
    message?.let { this.message = it }
    this.hasBeenHandled = hasBeenHandled
    return this
}

fun <T> handleResponse(response: Response<T>?): ResponseState<T> {
    if (response?.isSuccessful == true) {
        response.body()?.let { result ->
            return ResponseState.Success(result)
        }
    }
    if (response == null) {
        return ResponseState.NetworkError()
    }

    if (response.code() == 401) {
        return ResponseState.NotAuthorized()
    }

    val errorBody = response.errorBody()?.string()
    val errorMessage = parseErrorMessage(errorBody)
    return ResponseState.Error(errorMessage , null)
}

private fun parseErrorMessage(responseBody: String?): String {
    return try {
        val json = JSONObject(responseBody)
        val message = json.getString("message")
        message
    } catch (e: Exception) {
        "General Error" // Return a default message if parsing fails
    }
}