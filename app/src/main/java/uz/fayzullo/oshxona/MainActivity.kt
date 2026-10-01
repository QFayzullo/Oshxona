package uz.fayzullo.oshxona


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import uz.fayzullo.oshxona.presentation.navigation.OshxonaNavGraph
import uz.fayzullo.oshxona.ui.theme.OshxonaTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OshxonaTheme {
                OshxonaNavGraph()
            }
        }
    }
}