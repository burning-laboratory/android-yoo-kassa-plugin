package com.burninglab.yookassaunityplugin.types.data

import kotlinx.serialization.Serializable
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.Amount

/**
 * In app purchase bundle data.
 */
@Serializable
public data class IapBundle(
    /**
     * Custom payment id.
     */
    var id: String = "",

    /**
     * Payment bundle title.
     */
    var title: String,

    /**
     * Payment bundle description.
     */
    var description: String,

    /**
     * Payment bundle amount data.
     */
    var amountData: PaymentAmount
)
