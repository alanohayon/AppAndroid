package com.example.carplace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.carplace.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Home : AppCompatActivity() {

    //  Variable binding qui permet de recuperer les ref de la page activity_main.xml
    private lateinit var binding: ActivityHomeBinding

    private lateinit var navController: NavController


    // Connexion à l'authent de firebase
    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()

        // Connexion à l'authent de firebase
        auth = Firebase.auth

        //  On recupere le user courant (comme qd on verifier que la variable session existe)
        val currentUser = auth.currentUser

        //si le user est nul, donc pas connecter le ramener vers la page login
        if (currentUser == null) {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisez le binding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Récupérez la référence à BottomNavigationView à partir du fichier nav_bar.xml
        val bottomNavigationView = binding.navBar.bottomNavigationView

        // Fonction qui gere la navigation
        navigation(bottomNavigationView)
        // Après avoir initialisé votre binding dans onCreate
        navController = findNavController(R.id.homeViewPrincipal)


        val userDetail = binding.userDetail
        val btnLogout = binding.logout
        val btnGestionUser = binding.btnGestionUser

        // Connexion à l'authent de firebase
        auth = Firebase.auth
        val currentUser = auth.currentUser
        userDetail.text = currentUser?.email



        // si il click sur deconnexion alors la variable session du user sera supprimer et il sera ramené vers la page login
        btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }


//        btnGestionUser.setOnClickListener {
//            // Rediriger vers la page de gestion de l'utilisateur (ajustez la classe de l'activité selon votre structure)
//            val intent = Intent(applicationContext, UserAccount::class.java)
//            startActivity(intent)
//        }
    }

    private fun navigation(bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
//                R.id.nav_search -> {
//                    // Naviguer vers la destination "search"
//                    navController.navigate(R.id.home_to_search)
//                }
                R.id.nav_park -> {
                    // Naviguer vers la destination "position"
                    navController.navigate(R.id.home_to_park)
                }
                R.id.nav_car -> {
                    // Naviguer vers la destination "car"
                    navController.navigate(R.id.home_to_car)
                }
                R.id.nav_account -> {
                    // Naviguer vers la destination "account"
                    navController.navigate(R.id.home_to_account)
                }
            }
            true
        }
    }

}