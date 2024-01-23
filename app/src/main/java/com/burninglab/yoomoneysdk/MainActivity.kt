package com.burninglab.yoomoneysdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.burninglab.yookassaunityplugin.YooKassaUnityPluginActivity
import com.burninglab.yookassaunityplugin.types.data.IapBundle
import com.burninglab.yookassaunityplugin.types.data.PaymentAmount
import com.burninglab.yookassaunityplugin.types.data.PaymentAuth
import com.burninglab.yookassaunityplugin.types.data.PaymentOptions
import com.burninglab.yookassaunityplugin.types.data.ResponseConfig
import com.burninglab.yookassaunityplugin.types.misc.BigDecimalJson
import com.burninglab.yookassaunityplugin.types.requests.TokenizationRequest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentMethodType
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.SavePaymentMethod

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    public fun startPaymentProcess(view: View){
        val request = TokenizationRequest(
            authData = PaymentAuth(
                shopId = "281996",
                appKey = "test_MjgxOTk2hbC9wBL6ZELMoju3OfTA0qMGWTYBvVQjvZs",
                clientId = "example_authCenterClientId"
            ),
            bundle = IapBundle(
                id = "вв",
                title = "title",
                description = "description",
                amountData = PaymentAmount(
                    amount = BigDecimalJson(150),
                    currencyCode = "RUB"
                )
            ),
            responseConfig = ResponseConfig(
                callbackObjectName = "Yoo Kassa Plugin Callbacks",
                callbackMethodName = "OnTokenizationCompleteEventHandler"
            ),
            options = PaymentOptions(
                paymentMethods = setOf(
                    PaymentMethodType.BANK_CARD,
                    PaymentMethodType.YOO_MONEY,
                    PaymentMethodType.SBP,
                    PaymentMethodType.GOOGLE_PAY),
                savePaymentMethod = SavePaymentMethod.USER_SELECTS
            )
        )

        val serializedRequest = Json.encodeToString(request)

        val act = YooKassaUnityPluginActivity()
        act.startTokenization(this, serializedRequest)
    }
}