package com.burninglab.yookassaunityplugin.types.data

import kotlinx.serialization.Serializable
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentMethodType

/**
 * Yoo kassa tokenization result data struct.
 */
@Serializable
data class TokenizationResult(
    /**
     * Payment token.
     */
    var token: String?,

    /**
     * Payment method type.
     */
    var paymentMethodType: PaymentMethodType?
)
