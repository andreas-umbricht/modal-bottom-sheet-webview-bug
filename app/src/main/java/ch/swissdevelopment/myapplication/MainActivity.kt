package ch.swissdevelopment.myapplication

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import ch.swissdevelopment.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
                    var showWebView by remember { mutableStateOf(false) }

                    Column(modifier = Modifier.padding(innerPadding)) {
                        Button(onClick = { showWebView = true }) {
                            Text("Open")
                        }

                        WebView()
                    }

                    if (showWebView) {
                        ModalBottomSheet(
                            sheetState = sheetState,
                            onDismissRequest = {
                                showWebView = false
                            },
                            dragHandle = {},
                            shape = RectangleShape,
                        ) {
                            WebView()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WebView() {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
            }
        },
        update = { webView ->
            webView.loadUrl("https://www.srf.ch/")
        },
        modifier = Modifier
            .fillMaxSize()
    )
}