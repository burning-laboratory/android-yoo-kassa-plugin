package com.burninglab.yookassaunityplugin.types.data

import kotlinx.serialization.Serializable

/**
 * Yoo kassa tokenization error info data class.
 */
@Serializable
data class TokenizationError(
    var errorCode: String,
    var errorMessage: String
)
