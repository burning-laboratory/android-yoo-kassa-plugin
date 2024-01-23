package com.burninglab.yookassaunityplugin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.burninglab.yookassaunityplugin.types.requests.ConfirmationRequest
import com.burninglab.yookassaunityplugin.types.requests.TokenizationRequest
import com.burninglab.yookassaunityplugin.types.responses.ConfirmationResponse
import com.burninglab.yookassaunityplugin.types.responses.TokenizationResponse
import com.unity3d.player.UnityPlayer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.yoomoney.sdk.kassa.payments.Checkout
import ru.yoomoney.sdk.kassa.payments.Checkout.createTokenizationResult
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.Amount
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentParameters
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
     * Target operation activity extra key.
     */
    private var TargetOperationKey = "target_operation_name"

    /**
     * Activity extra confirmation request key.
     */
    private var ConfirmationRequestExtraKey = "confirmation_request"

    /**
     * Tokenization operation name.
     */
    private var TokenizationOperationName = "tokenization";

    /**
     * Payment confirmation operation name.
     */
    private var ConfirmationOperationName = "confirmation";

    /**
     * True if tokenization or confirmation process started.
     */
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

        }

        if (result.resultCode == Activity.RESULT_CANCELED){
            response.status = false
            response.error.errorCode = "CANCELED_BY_USER"
            response.error.errorMessage = "Tokenization canceled by user."
        }

        if (result.resultCode == Checkout.RESULT_ERROR){
            val data: Intent? = result.data

            response.status = false
            response.error.errorCode = data?.getStringExtra(Checkout.EXTRA_ERROR_CODE)
            response.error.errorMessage = data?.getStringExtra(Checkout.EXTRA_ERROR_DESCRIPTION)
        }

        val serializedResponse = Json.encodeToString(response)

        val responseConfig = tokenizationRequest.responseConfig
        UnityPlayer.UnitySendMessage(responseConfig.callbackObjectName, responseConfig.callbackMethodName, serializedResponse);

        finish()
    }

    /**
     * Yoo Kassa confirmation activity launcher.
     */
    private var confirmationLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val response = ConfirmationResponse(
            status = result.resultCode == Activity.RESULT_OK
        )

        val extras = intent.extras

        val serializedConfirmationRequest = extras?.getString(ConfirmationRequestExtraKey)
        val confirmationRequest = Json.decodeFromString<ConfirmationRequest>(serializedConfirmationRequest.toString())

        if (result.resultCode == Activity.RESULT_OK){
            response.bundle = confirmationRequest.bundle
            response.paymentId = confirmationRequest.paymentId
        }

        if (result.resultCode == Activity.RESULT_CANCELED){
            response.error.errorCode = "CANCELED_BY_USER"
            response.error.errorMessage = "Payment confirmation canceled by user."
        }

        if (result.resultCode == Checkout.RESULT_ERROR){
            val data: Intent? = result.data

            response.error.errorCode = data?.getStringExtra(Checkout.EXTRA_ERROR_CODE)
            response.error.errorMessage = data?.getStringExtra(Checkout.EXTRA_ERROR_DESCRIPTION)
        }

        val serializedResponse = Json.encodeToString(response)

        val responseConfig = confirmationRequest.responseConfig
        UnityPlayer.UnitySendMessage(responseConfig.callbackObjectName, responseConfig.callbackMethodName, serializedResponse);

        finish()
    }

    //endregion

    //region Activity Methods

    /**
     * Activity create event handler.
     */
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    /**
     * Activity start event handler.
     */
    override fun onStart() {
        super.onStart()

        setContentView(R.layout.payment_activity)

        if (ProcessStarted) {
            return
        }

        val extras = intent.extras
        val operationName = extras?.getString(TargetOperationKey)
        if (TokenizationOperationName == operationName) {
            val serializedRequest = extras.getString(TokenizationRequestExtraKey)

            ProcessStarted = true
            createTokenizeIntent(this, serializedRequest)
        }

        if (ConfirmationOperationName == operationName){
            val serializedRequest = extras.getString(ConfirmationRequestExtraKey)

            ProcessStarted = true
            createConfirmationIntent(this, serializedRequest)
        }
    }

    //endregion

    //region Private Methods

    /**
     * Create and start payment tokenization intent.
     */
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

    /**
     * Create payment confirmation intent.
     */
    private fun createConfirmationIntent(unityPlayerActivity: Activity, serializedConfirmationRequest: String?){
        val request = Json.decodeFromString<ConfirmationRequest>(serializedConfirmationRequest.toString())

        val intent = Checkout.createConfirmationIntent(
            context = unityPlayerActivity,
            confirmationUrl = request.confirmationUrl,
            paymentMethodType = request.paymentMethodType,
            clientApplicationKey = request.authData.appKey
        )

        confirmationLauncher.launch(intent)
    }

    //endregion

    //region Public Methods

    /**
     * Start tokenization process.
     * This method adaptive for colling from unity.
     */
    public fun startTokenization(unityPlayerActivity: Activity, serializedTokenizationRequest: String){
        val intent = Intent(unityPlayerActivity, YooKassaUnityPluginActivity::class.java)
        intent.putExtra(TargetOperationKey, TokenizationOperationName)
        intent.putExtra(TokenizationRequestExtraKey, serializedTokenizationRequest)
        unityPlayerActivity.startActivity(intent)
    }

    /**
     * Start confirmation process.
     * This method adaptive for colling from unity.
     */
    public fun startConfirmation(unityPlayerActivity: Activity, serializedTokenizationRequest: String){
        val intent = Intent(unityPlayerActivity, YooKassaUnityPluginActivity::class.java)
        intent.putExtra(TargetOperationKey, ConfirmationOperationName)
        intent.putExtra(ConfirmationRequestExtraKey, serializedTokenizationRequest)
        unityPlayerActivity.startActivity(intent)
    }

    //endregion
}