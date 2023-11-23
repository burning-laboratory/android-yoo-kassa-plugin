package com.burninglab.yookassaunityplugin.types.data

import kotlinx.serialization.Serializable

/**
 * Data class with response parameters.
 */
@Serializable
data class ResponseConfig(
    /**
     * Unity object name for send message.
     */
    var callbackObjectName: String,

    /**
     * Unity method name for send message.
     */
    var callbackMethodName: String
)
