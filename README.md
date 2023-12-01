Utiliser binding, pour l'affichage d'une page, et récupérer des elements d'une page.

import com.example.CarPlace.databinding.ActivityLoginBinding

//  Variable binding qui permet de recuperer les ref de la page activity_main.xml
    private lateinit var binding: ActivityLoginBinding
    
// Initialisez le binding
    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)
    
// Recuperer les elements de le la page par id
    val progression = binding.progressBar
    val inputEmail = binding.inputEmail
    val inputMdp = binding.inputMdp
    val btnLogin = binding.btnSignIn
    val registerNow = binding.registerNow

// Lorsqu'on click sur btnLogin ...
    btnLogin.setOnClickListener {
    }
