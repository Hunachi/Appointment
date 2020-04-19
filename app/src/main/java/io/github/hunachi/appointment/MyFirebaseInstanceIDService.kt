package io.github.hunachi.appointment

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.services.sns.AmazonSNSClient
import com.amazonaws.services.sns.model.CreatePlatformEndpointRequest
import com.amazonaws.services.sns.model.InvalidParameterException
import com.amazonaws.services.sns.model.SetEndpointAttributesRequest
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

// これはdeprecatedなコードです．．．．
class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        super.onTokenRefresh()
        val token = FirebaseInstanceId.getInstance().token ?: return
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        val client = AmazonSNSClient(generateAWSCredentials())
        client.endpoint = ENDPOINT
        val endpointArn = createEndpointArn(token, client)
        val request = SetEndpointAttributesRequest()
            .withEndpointArn(endpointArn)
            .withAttributes(mapOf("Token" to token, "Enabled" to "true"))
        client.setEndpointAttributes(request)
    }

    private fun createEndpointArn(token: String?, client: AmazonSNSClient): String {
        val preference = MyPreference(applicationContext)
        val endpointArn = preference.endpointArn()
        if (endpointArn.isNotBlank()) return endpointArn
        try {
            val request = CreatePlatformEndpointRequest()
                .withPlatformApplicationArn(APPLICATION_ARN)
                .withToken(token)
            val result = client.createPlatformEndpoint(request)
            return result.endpointArn.also { preference.markEndpointArn(it) }
        } catch (e: InvalidParameterException) {
            throw e
        }
    }

    private fun generateAWSCredentials() = object : AWSCredentials {
        override fun getAWSAccessKeyId() = ACCESS_KEY
        override fun getAWSSecretKey() = SECRET_KEY
    }

    companion object {
        private const val APPLICATION_ARN = "hoge"
        private const val ENDPOINT = "hoge"
        private const val ACCESS_KEY = "hoge"
        private const val SECRET_KEY = "hoge"
    }
}