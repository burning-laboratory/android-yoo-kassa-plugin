package com.burninglab.yookassaunityplugin.types.responses

import kotlinx.serialization.Serializable

/**
 * Confirmation response data structure.
 */
@Serializable
data class ConfirmationResponse(
    /**
     * Confirmed payment id.
     */
    val id: String,

    /**
     * Confirmation status.
     */
    val status: Boolean,

    /**
     * Confirmation error code from Yoo Kassa
     */
    val errorCode: String?,

    /**
     * Confirmation error message from yoo kassa.
     */
    val errorMessage: String?
)
