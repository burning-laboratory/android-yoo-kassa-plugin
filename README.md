<p align="center">
    <img src="https://i.ibb.co/NYKvCFB/Yoo-Kassa-Horizontal-Logo.png" alt="Project Logo" width="726">
</p>

<p align="center">
    <img src="https://build.burning-lab.com/app/rest/builds/buildType:id:UnityPlugins_YooKassa_Android_DevelopmentBuild/statusIcon.svg" alt="Build Status">
    <a href="https://tasks.burning-lab.com/agiles/131-71/current?settings"><img src="https://img.shields.io/badge/Roadmap-YouTrack-orange" alt="Roadmap Link"></a>
    <img src="https://img.shields.io/badge/34-darkgreen?logo=android&label=Target%20SDK" alt="License">
    <img src="https://img.shields.io/badge/License-MIT-success" alt="License">
</p>

## About

An add-on to the Yoo Kassa SDK library for convenient use in Unity applications.

## Dependencies:

List of gradle dependencies for plugin.

```groovy
dependencies {
    implementation 'androidx.appcompat:appcompat:1.3.1'
    implementation 'org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0'
    implementation 'ru.yoomoney.sdk.kassa.payments:yookassa-android-sdk:6.8.0'
}
```

## Usage:

### Start tokenization process:

Start tokenization process from Unity application example.

```csharp
    public void RunTokenization()
    {
        // Configure your tokenization request.
        // See tokenization request samples in Examples section.
        string tokenizationRequest = "{}";

        AndroidJavaClass unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject currentActivity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");

        // Call tokenization process from ui thread.
        currentActivity.Call("runOnUiThread", new AndroidJavaRunnable(() =>
        {
            using (AndroidJavaObject yooKassaUnityPluginActivity = new AndroidJavaObject("com.burninglab.yookassaunityplugin.YooKassaUnityPluginActivity"))
            {
                AndroidJavaClass unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
                AndroidJavaObject currentActivity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");

                // Call start tokenization plugin method.
                yooKassaUnityPluginActivity.Call("startTokenization", currentActivity, tokenizationRequest, _disableResponseEventHandlersCalling);
            }
        }));
    }
```

### Start confirmation process:

## Examples:

### Tokenization requests

#### Serialized tokenization request example:

Example of serialized tokenization request data.

```json
{
  "authData": {
    "shopId": "Your Yoo Kassa shop id.",
    "appKey": "Your Yoo Kassa SDK key from shop settings.",
    "clientId": "example_authCenterClientId"
  },
  "bundle": {
    "id": "buying bundle id.",
    "title": "Display bundle title",
    "description": "Display bundle description",
    "amountData": {
      "amount": 150,
      "currencyCode": "RUB"
    }
  },
  "responseConfig": {
    "callbackObjectName": "Unity yoo Kassa plugin callbacks game object name",
    "callbackMethodName": "OnTokenizationCompleteEventHandler"
  },
  "options": {
    "paymentMethods": [
      "BANK_CARD",
      "YOO_MONEY",
      "SBP",
      "GOOGLE_PAY"
    ],
    "savePaymentMethod": "USER_SELECTS"
  }
}
```

### Tokenization responses

List of tokenization responses variations.

#### Success tokenization response example

Serialized response after success tokenization process complete. Sending to unity with response config settings.

```json
{
  "status": true,
  "result": {
    "token": "pt-2cf237d1-0000-50ab-8000-022373d12d5d",
    "paymentMethodType": "BANK_CARD"
  },
  "bundle": {
    "id": "Optional bundle id.",
    "title": "title",
    "description": "description",
    "amountData": {
      "amount": 150,
      "currencyCode": "RUB"
    }
  }
}
```

#### Error tokenization response example

Serialized response after error tokenization process. Sending to unity with unity response config settings.

```json
{
  "status": false,
  "error": {
    "errorCode": "CANCELED_BY_USER",
    "errorMessage": "Tokenization canceled by user."
  }
}
```

## License