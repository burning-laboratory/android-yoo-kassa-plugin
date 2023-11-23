package com.burninglab.yookassaunityplugin.types.data

import com.burninglab.yookassaunityplugin.types.misc.BigDecimalJson
import kotlinx.serialization.Serializable

/**
 * Payment amount data class.
 */
@Serializable
data class PaymentAmount(

    /**
     * Bundle price amount
     */
    var amount: BigDecimalJson,

    /**
     * Bundle price currency code.
     */
    var currencyCode: String
)
