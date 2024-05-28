package br.com.CustomizeMe.customizeme

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.CustomizeMe.customizeme.adapter.PersonalizacaoAdapter
import br.com.CustomizeMe.customizeme.databinding.ActivityMainBinding
import br.com.CustomizeMe.customizeme.fragments.CoresFragment
import br.com.CustomizeMe.customizeme.interfaces.OnClickListener
import br.com.CustomizeMe.customizeme.interfaces.OnDadosEnviadosListener
import br.com.CustomizeMe.customizeme.model.CamadaPersonalizavel
import com.caverock.androidsvg.SVG
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.ArrayList

class MainActivity : AppCompatActivity() , OnDadosEnviadosListener  {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter = PersonalizacaoAdapter()
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    private var frameLayoutSheet: FrameLayout? = null
    private var onDadosEnviadosListener: OnDadosEnviadosListener? = null
    private val fragmentCores = CoresFragment()
    private var listaDeCores : MutableList<String> = mutableListOf()
    private var mapImagem : Map<String,List<String>> = mapOf()
    private var itens : MutableList<CamadaPersonalizavel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        frameLayoutSheet = binding.frameLayoutSheet
        bottomSheetBehavior = BottomSheetBehavior.from(frameLayoutSheet!!)
        bottomSheetBehavior?.setState(BottomSheetBehavior.STATE_HIDDEN)
        configRecyclerview()
       // val svgUrl = "https://apkdoandroidonline.com/totem/estampa.svg"
        val svgUrl = "https://apkdoandroidonline.com/camisas_personalizaveis/camisa_pesca_manga_longa_masculino/textura/camisa/camisa_pesca_masculino.png"
        val svgUrl2 = "https://apkdoandroidonline.com/camisas_personalizaveis/camisa_pesca_manga_longa_masculino/textura/gola/gola_redonda_textura_pesca.png"
        val svgUrl3 = "https://apkdoandroidonline.com/camisas_personalizaveis/camisa_pesca_manga_longa_masculino/molde/camisa/camisa_pesca_mangas_longa.svg"
        val svgUrl4 = "https://apkdoandroidonline.com/camisas_personalizaveis/camisa_pesca_manga_longa_masculino/molde/gola/gola_redonda_pesca.svg"
        val svgUrl5 = "https://apkdoandroidonline.com/camisas_personalizaveis/camisa_pesca_manga_longa_masculino/molde/gola/gola_redonda_fundo_pesca.svg"
        val svgUrl6 = "https://apkdoandroidonline.com/camisas_personalizaveis/camisa_pesca_manga_longa_masculino/molde/detalhe/camisa_pesca_detalhe.svg"
        loadSvgAndAddImageView(svgUrl4)
        loadSvgAndAddImageView(svgUrl5)
        loadSvgAndAddImageView(svgUrl6)
      //  loadSvgAndAddImageView(svgUrl3)
        loadSvgAndAddImageView(svgUrl)
        loadSvgAndAddImageView(svgUrl2)
    }

    fun loadSvgAndAddImageView(urlString: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val fileType = when {
                urlString.endsWith(".svg", ignoreCase = true) -> "SVG"
                urlString.endsWith(".png", ignoreCase = true) -> "PNG"
                else -> "Outro Tipo"
            }

            var bitmap : Bitmap? = null;

            if (fileType == "SVG") {
                // Processar o SVG
                 bitmap = try {
                    withContext(Dispatchers.IO) {
                        val url = URL(urlString)
                        val connection = url.openConnection() as HttpURLConnection
                        connection.connect()

                        val inputStream = connection.inputStream
                        val svgXml = inputStreamToXML(inputStream)
                        val valoresFill = obterValoresFillColorDaStringSVG(svgXml)
                       // val coresSubstitutas  = listOf("#fff", "#fff200")
                      //  valoresFill?.let {  listaDeCores.addAll(it) };
                     //   val coresSubstitutas  = valoresFill
                      //  val svgModificado = substituirCoresNaStringSVG(svgXml, valoresFill, coresSubstitutas)

                        itens.add(CamadaPersonalizavel(0,"camisa",svgXml,valoresFill, emptyList()))

                        criarBitmapDeSVG(svgXml)
                    }
                } catch (e: IOException) {
                    Log.d("my_svg", "- ${e.message}")
                    e.printStackTrace()
                    null
                }
                if (bitmap != null) {
                    // Se o processamento do SVG foi bem-sucedido
                    // ...
                } else {
                    // Se houve um problema ao processar o SVG
                   withContext(Dispatchers.Main){
                       Toast.makeText(applicationContext, "houve um problema ao processar o SVG", Toast.LENGTH_SHORT).show()
                   }
                }
            } else if (fileType == "PNG") {
                // Processar o PNG
                bitmap = try {
                    withContext(Dispatchers.IO) {
                        val url = URL(urlString)
                        val connection = url.openConnection() as HttpURLConnection
                        connection.connect()

                        val inputStream = connection.inputStream

                        // Decodifica a InputStream diretamente em um Bitmap
                        val bitmap = BitmapFactory.decodeStream(inputStream)

                        // Faça aqui qualquer manipulação ou processamento adicional necessário para o PNG

                        bitmap
                    }
                } catch (e: IOException) {
                    Log.d("my_png", "- ${e.message}")
                    e.printStackTrace()
                    null
                }

                if (bitmap != null) {
                    // Se o processamento do PNG foi bem-sucedido
                    // ...
                } else {
                    // Se houve um problema ao processar o PNG
                    Toast.makeText(applicationContext, "houve um problema ao processar o PNG", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Mostrar um Toast para outros tipos de arquivo
                Toast.makeText(applicationContext, "Tipo de arquivo não suportado", Toast.LENGTH_SHORT).show()
            }

            bitmap?.let { criarImageview(it) }
        }
    }

    private fun criarBitmapDeSVG(svgXml : String ) : Bitmap {
        val svg = SVG.getFromString(svgXml)
        val picture = svg.renderToPicture()
        val bitmap = Bitmap.createBitmap(picture.width, picture.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawPicture(picture)

       return bitmap
    }

   suspend fun criarImageview(bitmap: Bitmap){
        withContext(Dispatchers.Main) {
            if (bitmap != null) {
                // Criar um novo ImageView
                val novoImageView = ImageView(this@MainActivity) // Substitua pelo contexto do seu adapter
                novoImageView.id = View.generateViewId() // Gera um ID único para o novo ImageView
                novoImageView.setImageBitmap(bitmap)

                // Definir as configurações de layout para o novo ImageView
                val layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.MATCH_PARENT
                )
                novoImageView.layoutParams = layoutParams

                // Adicionar o novo ImageView ao ConstraintLayout
                val constraintLayout = findViewById<ConstraintLayout>(R.id.constraintlayoutMain) // Substitua pelo ID do seu ConstraintLayout



                // Adicionar o novo ImageView ao ConstraintLayout
                constraintLayout.addView(novoImageView)

                // Configurar as restrições para o novo ImageView
                val constraintSet = ConstraintSet()
                constraintSet.clone(constraintLayout)

                // Configurar as restrições de posicionamento do novo ImageView
                constraintSet.connect(
                    novoImageView.id,
                    ConstraintSet.TOP,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.TOP
                )
                constraintSet.connect(
                    novoImageView.id,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START
                )
                constraintSet.connect(
                    novoImageView.id,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END
                )

                // Aplicar as restrições ao ConstraintLayout
                constraintSet.applyTo(constraintLayout)

                // Trazer o novo ImageView para a frente
                novoImageView.bringToFront()
            }
        }
    }

    fun inputStreamToXML(inputStream: InputStream) : String{
        // Lê o InputStream
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()

        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            //  Log.d("my_svg","line- ${line}")
            stringBuilder.append(line)
        }

        return stringBuilder.toString()
    }
    fun obterValoresFillColorDaStringSVG(svgXml: String): List<String> {
        val pattern = """(?:fill|stop-color)\s*=\s*["'](#(?:[0-9a-fA-F]{3}|[0-9a-fA-F]{6}|[0-9a-fA-F]{8}))["']""".toRegex()
        val matchResults = pattern.findAll(svgXml)
        val valoresFill = matchResults.map { it.groupValues[1] }.toList()

        // Convertendo para um conjunto para manter apenas valores únicos
      //  val valoresUnicos = valoresFill.toSet().toList()
        listaDeCores.addAll(valoresFill)
        return valoresFill
    }

    fun substituirCoresNaStringSVG(svgXml: String, coresOriginais: List<String>, coresSubstitutas: List<String>): String {
        Log.d("my_svg","coresOriginais- ${coresOriginais}")
        Log.d("my_svg","coresSubstitutas- ${coresSubstitutas}")
        var svgModificado = svgXml

        if (coresOriginais.size != coresSubstitutas.size) {
            throw IllegalArgumentException("As listas de cores originais e substitutas devem ter o mesmo tamanho.")
        }

        for (i in coresOriginais.indices) {
            svgModificado = svgModificado.replace(coresOriginais[i], coresSubstitutas[i])
        }

        return svgModificado
    }

    private fun configRecyclerview(){
        val manager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        binding.recyclerviewMenuPersonalizacao.adapter = adapter
        binding.recyclerviewMenuPersonalizacao.layoutManager = manager
        binding.recyclerviewMenuPersonalizacao.setHasFixedSize(true)

        adapter.attackListener(object : OnClickListener{
            override fun onClick() {
                bottomSheetBehavior?.setState(BottomSheetBehavior.STATE_COLLAPSED)
               // bottomSheetBehavior?.setState(BottomSheetBehavior.STATE_HIDDEN)
               // Toast.makeText(applicationContext, "Click", Toast.LENGTH_SHORT).show()
                Log.d("Cores",listaDeCores.toString())
                val bundle = Bundle().apply {
                    putStringArrayList("cores", ArrayList(listaDeCores))
                    putParcelableArrayList("itens",ArrayList(itens))
                }
               // Toast.makeText(this@MainActivity, "Total de cores: ${ArrayList(listaDeCores)} ", Toast.LENGTH_SHORT).show()
                fragmentCores.arguments = bundle
                mudarFragments(R.id.fragmentContainerView,fragmentCores)
            }

            override fun onClick(cor: String) {
                TODO("Not yet implemented")
            }

            override fun onClick(view: View, cor: String, camada: CamadaPersonalizavel) {
                TODO("Not yet implemented")
            }

            override fun onClick(
                corOriginal: String,
                corNova: String,
                camada: CamadaPersonalizavel
            ) {
                TODO("Not yet implemented")
            }
        })

    }
    private fun mudarFragments(view: Int, fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        // Verificar se o fragmento já está na memória
        val existingFragment = fragmentManager.findFragmentById(view)

        if (existingFragment == null) {
            // Se não estiver na memória, substituir o fragmento
            transaction.replace(view, fragment)
        } else {
            // Se já estiver na memória, reutilizar o fragmento existente
            transaction.replace(view, existingFragment)
        }

        transaction.commit()
    }


    override fun onDadosEnviados(dados: String) {
        Toast.makeText(this@MainActivity, dados, Toast.LENGTH_SHORT).show()
    }

    override fun onDadosEnviados(itens: List<CamadaPersonalizavel>) {
        var dados = itens[0].dados
        var coresOriginais = itens[0].coresOriginais
        var coresSubstitutas= itens[0].coresSubstitutas
        dados =  substituirCoresNaStringSVG(dados, coresOriginais, coresSubstitutas)
        criarBitmapDeSVG(dados)
    }

    override fun onDadosEnviados(itens: CamadaPersonalizavel) {
        TODO("Not yet implemented")
    }


}