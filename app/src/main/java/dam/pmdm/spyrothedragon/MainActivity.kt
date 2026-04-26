package dam.pmdm.spyrothedragon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.graphics.drawable.AnimationDrawable
import android.widget.ImageView
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import dam.pmdm.spyrothedragon.databinding.ActivityMainBinding
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import android.media.MediaPlayer

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null
    private lateinit var contenedorGuia: FrameLayout
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.navHostFragment)

        navHostFragment?.let {
            navController = NavHostFragment.findNavController(it)
            NavigationUI.setupWithNavController(binding.navView, navController!!)
            NavigationUI.setupActionBarWithNavController(this, navController!!)
        }

        contenedorGuia = findViewById(R.id.contenedorGuia)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        binding.navView.setOnItemSelectedListener { menuItem ->
            if (contenedorGuia.visibility == View.VISIBLE) {
                false
            } else {
                selectedBottomMenu(menuItem)
            }
        }

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_characters,
                R.id.navigation_worlds,
                R.id.navigation_collectibles -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                else -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
            }
        }

        val guiaMostrada = sharedPreferences.getBoolean("guia_mostrada", false)
        if (!guiaMostrada) {
            mostrarPantallaGuia1()
        }
    }

    private fun selectedBottomMenu(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_characters ->
                navController?.navigate(R.id.navigation_characters)
            R.id.nav_worlds ->
                navController?.navigate(R.id.navigation_worlds)
            else ->
                navController?.navigate(R.id.navigation_collectibles)
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.about_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_info) {
            if (contenedorGuia.visibility == View.VISIBLE) {
                false
            } else {
                showInfoDialog()
                true
            }
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun showInfoDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.title_about)
            .setMessage(R.string.text_about)
            .setPositiveButton(R.string.accept, null)
            .show()
    }

    private fun reproducirSonido(idSonido: Int) {
        val mediaPlayer = MediaPlayer.create(this, idSonido)
        mediaPlayer?.setOnCompletionListener {
            it.release()
        }
        mediaPlayer?.start()
    }

    private fun marcarGuiaComoMostrada() {
        sharedPreferences.edit()
            .putBoolean("guia_mostrada", true)
            .apply()
    }

    private fun cerrarGuia() {
        marcarGuiaComoMostrada()

        navController?.navigate(R.id.navigation_characters)
        binding.navView.selectedItemId = R.id.nav_characters

        contenedorGuia.removeAllViews()
        contenedorGuia.visibility = View.GONE
    }

    private fun prepararOverlay() {
        contenedorGuia.visibility = View.VISIBLE
        contenedorGuia.bringToFront()
    }

    private fun mostrarPantallaGuia1() {
        prepararOverlay()
        contenedorGuia.removeAllViews()

        val vistaGuia = LayoutInflater.from(this)
            .inflate(R.layout.pantalla_guia_1, contenedorGuia, false)

        val btnComenzar = vistaGuia.findViewById<Button>(R.id.btnComenzarGuia)
        val imgGemas = vistaGuia.findViewById<ImageView>(R.id.imgGemasGuia)
        val imgHuevos = vistaGuia.findViewById<ImageView>(R.id.imgHuevosGuia)
        val imgCajas = vistaGuia.findViewById<ImageView>(R.id.imgCajasGuia)

        contenedorGuia.addView(vistaGuia)

        imgGemas.setBackgroundResource(R.drawable.secuencia_icono1_pantalla1)
        imgHuevos.setBackgroundResource(R.drawable.secuencia_icono2_pantalla1)
        imgCajas.setBackgroundResource(R.drawable.secuencia_icono3_pantalla1)

        val anim1 = imgGemas.background as AnimationDrawable
        val anim2 = imgHuevos.background as AnimationDrawable
        val anim3 = imgCajas.background as AnimationDrawable

        anim1.start()
        anim2.start()
        anim3.start()

        btnComenzar.setOnClickListener {
            reproducirSonido(R.raw.sonido_inicio)

            val animSalida = AnimationUtils.loadAnimation(this, R.anim.anim_salida_pantalla1)
            animSalida.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    mostrarPantallaGuia2()

                    contenedorGuia.post {
                        val vistaPantalla2 = contenedorGuia.getChildAt(0)
                        val animEntrada = AnimationUtils.loadAnimation(
                            this@MainActivity,
                            R.anim.anim_entrada_pantalla2
                        )
                        vistaPantalla2?.startAnimation(animEntrada)
                    }
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })

            vistaGuia.startAnimation(animSalida)
        }
    }

    private fun mostrarPantallaGuia2() {
        navController?.navigate(R.id.navigation_characters)
        binding.navView.selectedItemId = R.id.nav_characters

        prepararOverlay()
        contenedorGuia.removeAllViews()

        val vistaGuia = LayoutInflater.from(this)
            .inflate(R.layout.pantalla_guia_2, contenedorGuia, false)

        val bocadillo = vistaGuia.findViewById<View>(R.id.bocadilloPersonajes)
        val btnSiguiente = vistaGuia.findViewById<Button>(R.id.btnSiguienteGuia2)
        val txtOmitir = vistaGuia.findViewById<TextView>(R.id.txtOmitirGuia2)
        val imgLlama = vistaGuia.findViewById<ImageView>(R.id.imgLlamaPersonajes)
        val vResaltado = vistaGuia.findViewById<View>(R.id.vResaltadoPersonajes)

        btnSiguiente.setOnClickListener {
            reproducirSonido(R.raw.sonido_continuar)
            mostrarPantallaGuia3()
        }

        txtOmitir.setOnClickListener {
            reproducirSonido(R.raw.sonido_final)
            cerrarGuia()
        }

        contenedorGuia.addView(vistaGuia)

        val animBocadillo = AnimationUtils.loadAnimation(this, R.anim.anim_bocadillo_guia)
        bocadillo.startAnimation(animBocadillo)

        imgLlama.clearAnimation()
        val animacionLlama = AnimationUtils.loadAnimation(this, R.anim.anim_llama_guia)
        imgLlama.startAnimation(animacionLlama)

        vResaltado.clearAnimation()
        val animacionResaltado = AnimationUtils.loadAnimation(this, R.anim.anim_resaltado_fuego)
        vResaltado.startAnimation(animacionResaltado)
    }

    private fun mostrarPantallaGuia3() {
        navController?.navigate(R.id.navigation_worlds)
        binding.navView.selectedItemId = R.id.nav_worlds

        prepararOverlay()
        contenedorGuia.removeAllViews()

        val vistaGuia = LayoutInflater.from(this)
            .inflate(R.layout.pantalla_guia_3, contenedorGuia, false)

        val bocadillo = vistaGuia.findViewById<View>(R.id.bocadilloMundos)
        val btnSiguiente = vistaGuia.findViewById<Button>(R.id.btnSiguienteGuia3)
        val txtOmitir = vistaGuia.findViewById<TextView>(R.id.txtOmitirGuia3)
        val imgLlama = vistaGuia.findViewById<ImageView>(R.id.imgLlamaMundos)
        val vResaltado = vistaGuia.findViewById<View>(R.id.vResaltadoMundos)

        btnSiguiente.setOnClickListener {
            reproducirSonido(R.raw.sonido_continuar)
            mostrarPantallaGuia4()
        }

        txtOmitir.setOnClickListener {
            reproducirSonido(R.raw.sonido_final)
            cerrarGuia()
        }

        contenedorGuia.addView(vistaGuia)

        val animBocadillo = AnimationUtils.loadAnimation(this, R.anim.anim_bocadillo_guia)
        bocadillo.startAnimation(animBocadillo)

        imgLlama.clearAnimation()
        val animacionLlama = AnimationUtils.loadAnimation(this, R.anim.anim_llama_guia)
        imgLlama.startAnimation(animacionLlama)

        vResaltado.clearAnimation()
        val animacionResaltado = AnimationUtils.loadAnimation(this, R.anim.anim_resaltado_fuego)
        vResaltado.startAnimation(animacionResaltado)
    }

    private fun mostrarPantallaGuia4() {
        navController?.navigate(R.id.navigation_collectibles)
        binding.navView.selectedItemId = R.id.nav_collectibles

        prepararOverlay()
        contenedorGuia.removeAllViews()

        val vistaGuia = LayoutInflater.from(this)
            .inflate(R.layout.pantalla_guia_4, contenedorGuia, false)

        val bocadillo = vistaGuia.findViewById<View>(R.id.bocadilloColeccionables)
        val btnSiguiente = vistaGuia.findViewById<Button>(R.id.btnSiguienteGuia4)
        val txtOmitir = vistaGuia.findViewById<TextView>(R.id.txtOmitirGuia4)
        val imgLlama = vistaGuia.findViewById<ImageView>(R.id.imgLlamaColeccionables)
        val vResaltado = vistaGuia.findViewById<View>(R.id.vResaltadoColeccionables)

        btnSiguiente.setOnClickListener {
            reproducirSonido(R.raw.sonido_continuar)
            mostrarPantallaGuia5()
        }

        txtOmitir.setOnClickListener {
            reproducirSonido(R.raw.sonido_final)
            cerrarGuia()
        }

        contenedorGuia.addView(vistaGuia)

        val animBocadillo = AnimationUtils.loadAnimation(this, R.anim.anim_bocadillo_guia)
        bocadillo.startAnimation(animBocadillo)

        imgLlama.clearAnimation()
        val animacionLlama = AnimationUtils.loadAnimation(this, R.anim.anim_llama_guia)
        imgLlama.startAnimation(animacionLlama)

        vResaltado.clearAnimation()
        val animacionResaltado = AnimationUtils.loadAnimation(this, R.anim.anim_resaltado_fuego)
        vResaltado.startAnimation(animacionResaltado)
    }

    private fun mostrarPantallaGuia5() {
        prepararOverlay()
        contenedorGuia.removeAllViews()

        val vistaGuia = LayoutInflater.from(this)
            .inflate(R.layout.pantalla_guia_5, contenedorGuia, false)

        val bocadillo = vistaGuia.findViewById<View>(R.id.bocadilloInfo)
        val btnSiguiente = vistaGuia.findViewById<Button>(R.id.btnSiguienteGuia5)
        val txtOmitir = vistaGuia.findViewById<TextView>(R.id.txtOmitirGuia5)
        val imgFlecha = vistaGuia.findViewById<ImageView>(R.id.imgFlechaInfo)

        btnSiguiente.setOnClickListener {
            reproducirSonido(R.raw.sonido_continuar)
            mostrarPantallaGuia6()
        }

        txtOmitir.setOnClickListener {
            reproducirSonido(R.raw.sonido_final)
            cerrarGuia()
        }

        contenedorGuia.addView(vistaGuia)

        val animBocadillo = AnimationUtils.loadAnimation(this, R.anim.anim_bocadillo_guia)
        bocadillo.startAnimation(animBocadillo)

        imgFlecha.clearAnimation()
        val animacionFlecha = AnimationUtils.loadAnimation(this, R.anim.anim_flecha_info)
        imgFlecha.startAnimation(animacionFlecha)
    }

    private fun mostrarPantallaGuia6() {
        prepararOverlay()
        contenedorGuia.removeAllViews()

        val vistaGuia = LayoutInflater.from(this)
            .inflate(R.layout.pantalla_guia_6, contenedorGuia, false)

        val btnFinalizar = vistaGuia.findViewById<Button>(R.id.btnFinalizarGuia)
        btnFinalizar.setOnClickListener {
            reproducirSonido(R.raw.sonido_final)
            cerrarGuia()
        }

        contenedorGuia.addView(vistaGuia)
    }
}