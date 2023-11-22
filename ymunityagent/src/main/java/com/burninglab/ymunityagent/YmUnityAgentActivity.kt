package com.burninglab.ymunityagent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.unity3d.player.UnityPlayer
import ru.yoomoney.sdk.kassa.payments.Checkout
import ru.yoomoney.sdk.kassa.payments.Checkout.createTokenizationResult
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.Amount
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentMethodType
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentParameters
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.SavePaymentMethod
import java.math.BigDecimal
import java.util.*

class YmUnityAgentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onStart() {
        super.onStart()

        setContentView(R.layout.payment_activity)

        createTokenizeIntent(this)
    }

    private var tokenizationLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data

            val paymentTokenizationResult = data?.let { createTokenizationResult(it) }
            val paymentToke: String? = paymentTokenizationResult?.paymentToken
            UnityPlayer.UnitySendMessage("AndroidCommunication", "ReceivePaymentToken", paymentToke);

            finish()
        }
    }

    public fun launchActivity(unityPlayerActivity: Activity){
        val intent: Intent = Intent(unityPlayerActivity, YmUnityAgentActivity::class.java)
        unityPlayerActivity.startActivity(intent)
    }

    public fun createTokenizeIntent(unityPlayerActivity: Activity){
        val paymentParameters = PaymentParameters(
            amount = Amount(BigDecimal.TEN, Currency.getInstance("RUB")),
            title = "Название товара",
            subtitle = "Описание товара",
            clientApplicationKey = "test_MjgxOTk2hbC9wBL6ZELMoju3OfTA0qMGWTYBvVQjvZs", // ключ для клиентских приложений из личного кабинета ЮKassa (https://yookassa.ru/my/api-keys-settings)
            shopId = "281996", // идентификатор магазина ЮKassa
            savePaymentMethod = SavePaymentMethod.OFF, // флаг выключенного сохранения платежного метода,
            paymentMethodTypes = setOf(PaymentMethodType.YOO_MONEY, PaymentMethodType.BANK_CARD, PaymentMethodType.SBERBANK),
            authCenterClientId = "example_authCenterClientId", // идентификатор, полученный при регистрации приложения на сайте https://yookassa.ru
        )
        val intent: Intent = Checkout.createTokenizeIntent(unityPlayerActivity, paymentParameters)
        tokenizationLauncher.launch(intent)
    }
}