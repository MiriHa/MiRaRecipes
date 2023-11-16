package de.miRa.mirarecipes

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import de.miRa.mirarecipes.recipes.RecipesViewModel
import de.miRa.mirarecipes.ui.composables.RecipeListScreen
import de.miRa.mirarecipes.ui.theme.MiRaRecipesTheme
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {

    private val recipesViewModel = RecipesViewModel()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if (BuildConfig.DEBUG) {
//            Firebase.database.useEmulator("127.0.0.1", 9000)
//            Firebase.auth.useEmulator("127.0.0.1", 9099)
//        }

        auth = Firebase.auth

        auth.signInWithEmailAndPassword("lugi6@live.de", "123456")
            .addOnCompleteListener(this) {task ->
                if (task.isSuccessful) {
                    Log.d("FIREBASE", "signInWithEmial:success")
                }
                else{
                    Log.d("FIREBASE", "${auth.currentUser} --- signInWithEmial:failure")
//                    exitProcess(-1)
                }

            }
        setContent {
            MiRaRecipesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RecipeListScreen(recipesViewModel, /*navController*/)

                }
            }
        }
    }
}
