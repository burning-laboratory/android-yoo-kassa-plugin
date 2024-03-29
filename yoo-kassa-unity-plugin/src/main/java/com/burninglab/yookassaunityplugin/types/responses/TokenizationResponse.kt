package com.burninglab.yookassaunityplugin.types.responses

import com.burninglab.yookassaunityplugin.types.data.IapBundle
import com.burninglab.yookassaunityplugin.types.data.ErrorInfo
import com.burninglab.yookassaunityplugin.types.data.TokenizationResult
import kotlinx.serialization.Serializable
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentMethodType

@Serializable
data class TokenizationResponse(
    var status: Boolean,

    var result: TokenizationResult = TokenizationResult(
        token = "",
        paymentMethodType = PaymentMethodType.GOOGLE_PAY
    ),

    var bundle: IapBundle = IapBundle(),

    var error: ErrorInfo = ErrorInfo(
        errorCode = "",
        errorMessage = ""
    )
)
