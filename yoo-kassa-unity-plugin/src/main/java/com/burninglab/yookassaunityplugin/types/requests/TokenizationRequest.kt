package com.burninglab.yookassaunityplugin.types.requests

import com.burninglab.yookassaunityplugin.types.data.IapBundle
import com.burninglab.yookassaunityplugin.types.data.PaymentAuth
import com.burninglab.yookassaunityplugin.types.data.PaymentOptions
import com.burninglab.yookassaunityplugin.types.data.ResponseConfig
import kotlinx.serialization.Serializable

@Serializable
data class TokenizationRequest(
    var authData: PaymentAuth,
    var bundle: IapBundle,
    var responseConfig: ResponseConfig,
    var options: PaymentOptions
)
