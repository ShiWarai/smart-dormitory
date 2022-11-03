package ru.rtulab.smartdormitory.data

import java.io.IOException
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ru.rtulab.smartdormitory.common.persistence.AuthStateStorage


class BasicAuthInterceptor(authStateStorage:AuthStateStorage) : Interceptor {
    private val credentials: String = Credentials.basic(/*authStateStorage.user ?:*/"1234567", /*authStateStorage.password ?:*/"1111")
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val authenticatedRequest: Request = request.newBuilder()
            .header("Authorization", credentials).build()
        return chain.proceed(authenticatedRequest)
    }

}