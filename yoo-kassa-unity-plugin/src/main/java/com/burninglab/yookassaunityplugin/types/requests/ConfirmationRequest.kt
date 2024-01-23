package com.burninglab.yookassaunityplugin.types.requests

import com.burninglab.yookassaunityplugin.types.data.IapBundle
import com.burninglab.yookassaunityplugin.types.data.PaymentAuth
import com.burninglab.yookassaunityplugin.types.data.ResponseConfig
import kotlinx.serialization.Serializable

import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentMethodType

/**
 * Payment confirmation request data structure.
 */
@Serializable
data class ConfirmationRequest(
    /**
     * Payment operation auth config.
     */
    var authData: PaymentAuth,

    /**
     * Payment id for confirm.
     */
    var paymentId: String,

    /**
     * Payment operation response config.
     */
    var responseConfig: ResponseConfig,

    /**
     * Payment confirmation url.
     */
    var confirmationUrl: String,

    /**
     * Payment method type.
     */
    var paymentMethodType: PaymentMethodType,

    /**
     * Payment bundle.
     */
    var bundle: IapBundle
)
