package com.burninglab.yookassaunityplugin.types.responses

import com.burninglab.yookassaunityplugin.types.data.ErrorInfo
import com.burninglab.yookassaunityplugin.types.data.IapBundle
import kotlinx.serialization.Serializable

/**
 * Confirmation response data structure.
 */
@Serializable
data class ConfirmationResponse(
    /**
     * Confirmation status.
     */
    var status: Boolean,

    /**
     * Confirmed payment id.
     */
    var paymentId: String = "",

    /**
     * Confirmed iap bundle.
     */
    var bundle: IapBundle = IapBundle(),

    /**
     * Payment confirmation error info.
     */
    var error: ErrorInfo = ErrorInfo()
)
