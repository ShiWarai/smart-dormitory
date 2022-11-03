package ru.rtulab.smartdormitory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.rtulab.smartdormitory.presentation.SmartDormitory
import ru.rtulab.smartdormitory.presentation.navigation.LocalNavController
import ru.rtulab.smartdormitory.presentation.ui.authtorization.Authtorization
import ru.rtulab.smartdormitory.presentation.viewmodel.LocalActivity
import ru.rtulab.smartdormitory.ui.theme.SmartDormitoryTheme

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val authState = authViewModel.authState.collectAsState().value

            SmartDormitoryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    if(authState){
                        CompositionLocalProvider(
                            LocalNavController provides rememberNavController(),
                            LocalActivity provides this
                        ) {
                            SmartDormitory()
                        }
                    }else{
                        Authtorization(authViewModel::onLoginEvent)
                    }


                }
            }
        }
    }
}

