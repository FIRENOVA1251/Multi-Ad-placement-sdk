package com.example.clickforcesdktest.view.ui


import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clickforcesdktest.data.model.AdRequest
import com.example.clickforcesdktest.sdk.AdSdk
import com.example.clickforcesdktest.viewmodel.AdViewModel


@Composable
fun AppNavigation(viewModel: AdViewModel) {
    // 创建 NavController
    val navController = rememberNavController()

    // 设置 NavHost
    NavHost(navController = navController, startDestination = "input") {
        // 输入页面
        composable("input") {
            InputScreen(navController)
        }
        // 显示页面，接收 Zone ID
        composable("display/{option}") { backStackEntry ->

            val option = backStackEntry.arguments?.getString("option") ?: "N/A"
            DisplayScreen(viewModel, navController, option)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(navController: NavController) {
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Enter Zone ID") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Radio Buttons for 320x50 and 300x250
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedOption = 1 },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedOption == 1,
                    onClick = { selectedOption = 1 }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    "Banner Size: 300 x 250", modifier = Modifier
                        .padding(start = 4.dp)
                        .weight(1f)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedOption = 2 },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedOption == 2,
                    onClick = { selectedOption = 2 }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    "Banner Size: 320 x 50", modifier = Modifier
                        .padding(start = 4.dp)
                        .weight(1f)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedOption = 3 },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedOption == 3,
                    onClick = { selectedOption = 3 }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    "Banner Size: 336 x 280", modifier = Modifier
                        .padding(start = 4.dp)
                        .weight(1f)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedOption = 4 },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedOption == 4,
                    onClick = { selectedOption = 4 }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    "Banner Size: 320 x 100", modifier = Modifier
                        .padding(start = 4.dp)
                        .weight(1f)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedOption = 5 },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedOption == 5,
                    onClick = { selectedOption = 5 }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    "Full Banner Size: 320 x 480", modifier = Modifier
                        .padding(start = 4.dp)
                        .weight(1f)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedOption = 6 },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedOption == 6,
                    onClick = { selectedOption = 6 }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    "Banner Flip: 320 x 100", modifier = Modifier
                        .padding(start = 4.dp)
                        .weight(1f)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedOption = 7 },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedOption == 7,
                    onClick = { selectedOption = 7 }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    "MultiMediaTowerAd: 320 x 250", modifier = Modifier
                        .padding(start = 4.dp)
                        .weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(100.dp))
            Text("Please enter Zone ID", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = textState,
                onValueChange = { textState = it },
                placeholder = { Text("Zone ID") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(25.dp))
            Button(
                onClick = {
                    if (textState.text.isNotEmpty() && selectedOption != null) {

                        val adRequest = AdRequest(
                            zoneId = textState.text,
                            appId = "com.cf.CFiAdBottomViewSwiftSample",
                            osType = "5",
                            deviceType = "2",
                            idfa = "00000000-0000-0000-0000-000000000000",
                            mfidfa = "50FBB5EF-BDD4-CE99-3EA9-1351E15BCACA-FEE7",
                            network = "2",
                            width = "1179",
                            height = "2556",
                            dpi = "489",

                            osName = "iPhone",
                            osVersion = "17.5",
                            osTimestamp = "1730964961",
                            osModel = "iPhone15,2",
                            country = "TW",
                            deviceStorage = "255866785792"
                        )
                        AdSdk.loadAds(adRequest)
                        navController.navigate("display/${selectedOption}")
                    }
                },
                enabled = textState.text.isNotEmpty() && selectedOption != null
            ) {
                Text("Enter")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayScreen(
    viewModel: AdViewModel,
    navController: NavController,
    option: String
) {

    var isAdVisible by remember { mutableStateOf(true) }

    if (option == "5") {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("AD Display") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) { // 返回上一页
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )

            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // 從這邊開始顯示廣告UI
                Text("This is the main screen content.")
            }
        }

        AdScreen(viewModel, option)
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("AD Display") },
                    navigationIcon = {
                        IconButton(onClick = {
                            isAdVisible = false
                            navController.popBackStack()
                        }) { // 返回上一页
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )

            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // 從這邊開始顯示廣告UI
                if (isAdVisible) {
                    AdScreen(viewModel, option)
                }

            }
        }
    }

}
