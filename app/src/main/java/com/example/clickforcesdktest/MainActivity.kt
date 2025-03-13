package com.example.clickforcesdktest

import com.example.clickforcesdktest.viewmodel.AdViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.clickforcesdktest.sdk.AdSdk
import com.example.clickforcesdktest.ui.theme.ClickForceSDKTestTheme
import com.example.clickforcesdktest.view.ui.AppNavigation

class MainActivity : ComponentActivity() {

    // 取得 ViewModel
    private val adViewModel by viewModels<AdViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AdSdk.init(this, adViewModel)

        setContent {
            ClickForceSDKTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    AppNavigation(viewModel = adViewModel)
//                    Column {
//                        AdScreen(viewModel = adViewModel, "1")
//                        AdScreen(viewModel = adViewModel, "2")
//                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello123 $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ClickForceSDKTestTheme {
        Greeting("Android")
    }
}