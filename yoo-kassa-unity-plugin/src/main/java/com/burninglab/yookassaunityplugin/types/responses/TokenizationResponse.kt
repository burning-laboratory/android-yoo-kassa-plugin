package com.burninglab.yookassaunityplugin.types.responses

import com.burninglab.yookassaunityplugin.types.data.TokenizationError
import com.burninglab.yookassaunityplugin.types.data.TokenizationResult
import kotlinx.serialization.Serializable
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentMethodType

@Serializable
data class TokenizationResponse(
    var status: Boolean = false,

    var result: TokenizationResult = TokenizationResult(
        token = "",
        paymentMethodType = PaymentMethodType.GOOGLE_PAY
    ),

    var error: TokenizationError = TokenizationError(
        errorCode = "",
        errorMessage = ""
    )
)
