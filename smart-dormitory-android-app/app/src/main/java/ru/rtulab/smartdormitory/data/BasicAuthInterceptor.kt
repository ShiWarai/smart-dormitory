package ru.rtulab.smartdormitory.data

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ru.rtulab.smartdormitory.common.persistence.AuthStateStorage


class BasicAuthInterceptor(authStateStorage:AuthStateStorage) : Interceptor {
    private var credentials: String? = null;
    private val auth: AuthStateStorage = authStateStorage;
    override fun intercept(chain: Interceptor.Chain): Response {
        if(credentials == null)
            this.credentials = Credentials.basic(auth.user.toString(), auth.password.toString())

        val request: Request = chain.request()
        val authenticatedRequest: Request = request.newBuilder()
            .header("Authorization", credentials.orEmpty()).build()
        return chain.proceed(authenticatedRequest)
    }

}