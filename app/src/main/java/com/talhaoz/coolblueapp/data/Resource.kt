package com.talhaoz.coolblueapp.data

import com.talhaoz.coolblueapp.viewmodel.FailedType

sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Failed<T>(val message: String?, val type: FailedType) : Resource<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> failed(message: String?, type: FailedType) = Failed<T>(message, type)
    }
}



