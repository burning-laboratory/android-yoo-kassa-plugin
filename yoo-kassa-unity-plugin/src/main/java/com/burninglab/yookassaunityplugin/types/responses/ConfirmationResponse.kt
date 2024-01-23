package com.burninglab.yookassaunityplugin.types.responses

import android.os.Bundle
import com.burninglab.yookassaunityplugin.types.data.ErrorInfo
import com.burninglab.yookassaunityplugin.types.data.IapBundle
import kotlinx.serialization.Serializable

/**
 * Confirmation response data structure.
 */
@Serializable
data class ConfirmationResponse(
    /**
     * Confirmed payment id.
     */
    var id: String = "",

    /**
     * Confirmed iap bundle.
     */
    var bundle: IapBundle = IapBundle(),

    /**
     * Confirmation status.
     */
    var status: Boolean = false,


    var error: ErrorInfo = ErrorInfo()
)
