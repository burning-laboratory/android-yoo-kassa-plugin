package com.burninglab.yookassaunityplugin.types.data

import kotlinx.serialization.Serializable

/**
 * Yoo kassa tokenization error info data class.
 */
@Serializable
data class ErrorInfo(
    var errorCode: String? = "",
    var errorMessage: String? = ""
)
