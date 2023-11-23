package com.burninglab.yookassaunityplugin.types.data

import kotlinx.serialization.Serializable

/**
 * Yoo kassa payment authorization params.
 */
@Serializable
data class PaymentAuth(

    /**
     * Yoo kassa shop id.
     */
    var shopId: String,

    /**
     * Yoo kassa mobile sdk key.
     */
    var appKey: String,

    /**
     * Yoo kassa client application id.
     */
    var clientId: String
)
