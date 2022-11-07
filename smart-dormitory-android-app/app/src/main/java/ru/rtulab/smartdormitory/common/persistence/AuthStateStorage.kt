package ru.rtulab.smartdormitory.common.persistence

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import net.openid.appauth.AuthState

class AuthStateStorage(context: Context) {
    private companion object {
        const val AUTH_STATE_PREFS_NAME = "auth_state_prefs"
        val AUTH_STATE_KEY = stringPreferencesKey("auth_state_key")
        val USER_ID_KEY = stringPreferencesKey("user_id_key")
        val USER_PAYLOAD_KEY = stringPreferencesKey("user_payload_key")
    }

    private val dataStore = context.createDataStore(AUTH_STATE_PREFS_NAME)

    /*private fun Preferences.getAuthState() = this[AUTH_STATE_KEY]?.let {
        AuthState.jsonDeserialize(it)
    } ?: AuthState()*/

    val authStateFlow = dataStore.data

    var user: String? = null
    var password: String? = null


    init {
        CoroutineScope(Dispatchers.IO).launch {
            authStateFlow.collect {
                it[USER_ID_KEY]?.let { it1 ->
                    Log.d("COLLECTCOLLECT", it1)
                    user = it1
                }
                it[USER_PAYLOAD_KEY]?.let { it1 ->
                    password = it1
                    Log.d("COLLECTCOLLECT", password!!)
                }

            }
        }
    }

    // private fun Preferences.getUserClaims(): List<Any> = UserClaimParser.parse(this[USER_PAYLOAD_KEY])

    //val userClaimsFlow = dataStore.data.map { it.getUserClaims() }

    /*suspend fun resetAuthStateWithConfig(config: AuthorizationServiceConfiguration?) {
        val clearedAuthState = config?.let { AuthState(it) } ?: AuthState()
        dataStore.edit { prefs ->
            prefs[AUTH_STATE_KEY] = clearedAuthState.jsonSerializeString()
        }
    }*/

    suspend fun resetUserClaims() {
        dataStore.edit { prefs ->
            prefs.remove(USER_PAYLOAD_KEY)
        }
    }


    suspend fun updateAuthState(
        authState: AuthState
    ) {
        dataStore.edit { prefs ->
            prefs[AUTH_STATE_KEY] = authState.jsonSerializeString()
        }
    }

    val userIdFlow = dataStore.data.map { it[USER_ID_KEY] ?: "" }
    suspend fun resetUserId() {
        dataStore.edit { prefs ->
            prefs.remove(USER_ID_KEY)
        }
    }

    suspend fun updateUserId(user: String) {
        dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = user
        }
    }

    suspend fun resetUserPassword() {
        dataStore.edit { prefs ->
            prefs.remove(USER_PAYLOAD_KEY)
        }
    }

    suspend fun updateUserPassword(password: String) {
        /*val payload = Base64.decode(
            ("$password"),
            Base64.DEFAULT
        ).decodeToString()*/
        dataStore.edit { prefs ->
            prefs[USER_PAYLOAD_KEY] = password
        }
    }

    suspend fun endSession() {
        // val config = latestAuthState.authorizationServiceConfiguration
        //resetAuthStateWithConfig(config)
        //resetUserClaims()
    }

}