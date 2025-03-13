package com.example.clickforcesdktest.view.ui


import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.viewinterop.AndroidView
import com.example.clickforcesdktest.data.model.AdResponse
import com.example.clickforcesdktest.viewmodel.AdViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun AdWebView(
    viewModel: AdViewModel, adResponse: AdResponse
) {
    val context = LocalContext.current

    // Use escapeHTML to modify the context.
    fun escapeHTML(input: String): String {
        return input
            .replace("&", "&amp;")
            .replace("\"", "&quot;")
            .replace("'", "&#x27;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
    }

    val impTrack = adResponse.item?.impTrack ?: ""
    val jsContent = adResponse.item?.bannerContent?.htmlContent ?: ""


    val processedJsContent = jsContent
        .replace("%%WIDTH%%", "'%%WIDTH%%'")
        .replace("%%HEIGHT%%", "'%%HEIGHT%%'")
        .replace("%%IMP_URL%%", impTrack)


    // If there is "<meta" then use it, otherwise wrap it via iframe.
    val finalHtml = remember(processedJsContent) {
        if (processedJsContent.isNotEmpty()) {
            if (processedJsContent.contains("<meta")) {
                // Return the HTML directly.
                processedJsContent
            } else {
                val innerHTML = """
                    <html>
                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <meta http-equiv="X-UA-Compatible" content="ie=edge">
                        <style>
                            body { padding: 0; margin: 0; }
                        </style>
                    </head>
                    <body>
                      <script>$processedJsContent</script>
                    </body>
                    </html>
                """.trimIndent()

                val escaped = escapeHTML(innerHTML)
                // 包在最外層
                """
                <html>
                    <head>
                      <meta charset="UTF-8">
                      <meta name="viewport" content="width=device-width, initial-scale=1.0">
                      <meta http-equiv="X-UA-Compatible" content="ie=edge">
                    </head>
                    <body>
                        <iframe srcdoc="$escaped" frameborder="0" style="width:100%; height:100%;"></iframe>
                    </body>
                </html>
                """.trimIndent()
            }
        } else {
            // If htmlContent is null or empty, return ""
            ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        // Use AndroidView to build WebView
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.javaScriptCanOpenWindowsAutomatically = true
                }
            },
            update = { webView ->
                if (finalHtml.isNotEmpty()) {
                    webView.loadDataWithBaseURL(
                        /* baseUrl = */ null,
                        /* data = */ finalHtml,
                        /* mimeType = */ "text/html",
                        /* encoding = */ "UTF-8",
                        /* historyUrl = */ null
                    )
                }
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 5.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                painter = rememberAsyncImagePainter("https://cdn.holmesmind.com/cf.png"),
                contentDescription = "CF Logo",
                modifier = Modifier
                    .width(23.dp)
                    .height(20.dp)
                    .clickable {
                        val intent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(adResponse.item?.iconUrl))
                        context.startActivity(intent)
                    }
            )
        }
    }
}


//21963