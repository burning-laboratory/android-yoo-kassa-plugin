package com.burninglab.yookassaunityplugin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.burninglab.yookassaunityplugin.types.requests.TokenizationRequest
import com.burninglab.yookassaunityplugin.types.responses.TokenizationResponse
import com.unity3d.player.UnityPlayer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.yoomoney.sdk.kassa.payments.Checkout
import ru.yoomoney.sdk.kassa.payments.Checkout.createTokenizationResult
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.Amount
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentMethodType
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentParameters
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.SavePaymentMethod
import java.util.*

/**
 * Service activity for yoo kassa android plugin.
 */
class YooKassaUnityPluginActivity : AppCompatActivity() {

    //region Private Fields

    /**
     * Activity extra tokenization request key.
     */
    private var TokenizationRequestExtraKey = "tokenization_request"

    /**
     * Key for getting send unity message flag from activity extras.
     */
    private var DisableCallUnityExtraKey = "disable_call_unity"

    private var ProcessStarted = false;

    //endregion

    //region Activity Launchers

    /**
     * Yoo kassa tokenization activity launcher.
     */
    private var tokenizationLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val response = TokenizationResponse(
            status = result.resultCode == Activity.RESULT_OK
        )

        val extras = intent.extras
        val serializedTokenizationRequest = extras?.getString(TokenizationRequestExtraKey)
        val tokenizationRequest = Json.decodeFromString<TokenizationRequest>(serializedTokenizationRequest.toString())

        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data

            val paymentTokenizationResult = data?.let { createTokenizationResult(it) }

            response.result.token = paymentTokenizationResult?.paymentToken
            response.result.paymentMethodType = paymentTokenizationResult?.paymentMethodType

            response.bundle = tokenizationRequest.bundle

        }else{
            response.error.errorCode = "CANCELED_BY_USER"
            response.error.errorMessage = "Tokenization canceled by user."
        }

        val serializedResponse = Json.encodeToString(response)

        val disableUnityCall = extras?.getBoolean(DisableCallUnityExtraKey)
        if (disableUnityCall == false){
            val responseConfig = tokenizationRequest.responseConfig

            UnityPlayer.UnitySendMessage(responseConfig.callbackObjectName, responseConfig.callbackMethodName, serializedResponse);
        }

        finish()
    }

    //endregion

    //region Activity Methods

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onStart() {
        super.onStart()

        setContentView(R.layout.payment_activity)

        if (!ProcessStarted)
        {
            val extras = intent.extras
            val serializedRequest = extras?.getString(TokenizationRequestExtraKey)

            createTokenizeIntent(this, serializedRequest)
            ProcessStarted = true;
        }
    }

    //endregion

    //region Private Methods

    private fun createTokenizeIntent(unityPlayerActivity: Activity, serializedTokenizationRequest: String?){
        val request = Json.decodeFromString<TokenizationRequest>(serializedTokenizationRequest.toString())

        val paymentParameters = PaymentParameters(
            amount = Amount(
                request.bundle.amountData.amount,
                Currency.getInstance(request.bundle.amountData.currencyCode)
            ),
            title = request.bundle.title,
            subtitle = request.bundle.description,
            clientApplicationKey = request.authData.appKey,
            shopId = request.authData.shopId,
            savePaymentMethod = request.options.savePaymentMethod,
            paymentMethodTypes = request.options.paymentMethods,
            authCenterClientId = request.authData.clientId,
        )
        val intent: Intent = Checkout.createTokenizeIntent(unityPlayerActivity, paymentParameters)
        tokenizationLauncher.launch(intent)
    }

    //endregion

    //region Public Methods

    public fun startTokenization(unityPlayerActivity: Activity, serializedTokenizationRequest: String, disableUnityCall: Boolean = false){
        val intent = Intent(unityPlayerActivity, YooKassaUnityPluginActivity::class.java)
        intent.putExtra(DisableCallUnityExtraKey, disableUnityCall)
        intent.putExtra(TokenizationRequestExtraKey, serializedTokenizationRequest)
        unityPlayerActivity.startActivity(intent)
    }

    //endregion
}