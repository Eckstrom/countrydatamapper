package gatech.edu.countrydatamapper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import gatech.edu.countrydatamapper.viewmodel.CountryViewModel
import gatech.edu.countrydatamapper.ui.CountryCollectionView
import gatech.edu.countrydatamapper.ui.theme.CountrydatamapperTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountrydatamapperTheme {
                val viewModel: CountryViewModel = hiltViewModel()
                CountryCollectionView(viewModel = viewModel)
            }
        }
    }
}