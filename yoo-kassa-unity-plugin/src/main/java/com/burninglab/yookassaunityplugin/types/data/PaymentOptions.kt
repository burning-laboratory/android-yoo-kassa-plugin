package com.burninglab.yookassaunityplugin.types.data

import kotlinx.serialization.Serializable
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentMethodType
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.SavePaymentMethod

/**
 * Yoo kassa payment options data class.
 */
@Serializable
data class PaymentOptions(

    /**
     * List of payment methods.
     */
    var paymentMethods: Set<PaymentMethodType>,

    /**
     * Save payment method flag.
     */
    var savePaymentMethod: SavePaymentMethod
)
