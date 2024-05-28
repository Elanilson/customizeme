package br.com.CustomizeMe.customizeme.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import br.com.CustomizeMe.customizeme.R
import com.caverock.androidsvg.SVG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.StringReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UtilsSVG {
    private lateinit var activity: Activity
    private var listaDeCores: MutableList<String> = mutableListOf()
    private var dadosSVG : String = ""
    private var idImageView : Int = 1
    var coresOriginais : MutableList<String> = mutableListOf()

    val coresPadrao = mapOf(
        "none" to "#00000000",
        "black" to "#000000",
        "white" to "#FFFFFF",
        "red" to "#FF0000",
        "blue" to "#0000FF",
        "green" to "#008000",
        "yellow" to "#FFFF00",
        "purple" to "#800080",
        "orange" to "#FFA500",
        "cyan" to "#00FFFF",
        "pink" to "#FFC0CB",
        "brown" to "#A52A2A",
        "gray" to "#808080",
        "magenta" to "#FF00FF",
        "lime" to "#00FF00",
        "teal" to "#008080",
        "olive" to "#808000",
        "maroon" to "#800000",
        "navy" to "#000080",
        "silver" to "#C0C0C0",
        "gold" to "#FFD700",
        "silver" to "#C0C0C0",
        "gold" to "#FFD700",
        "indigo" to "#4B0082",
        "turquoise" to "#40E0D0",
        "salmon" to "#FA8072",
        "darkgreen" to "#006400",
        "violet" to "#EE82EE",
        "olivedrab" to "#6B8E23",
        "coral" to "#FF7F50",
        "skyblue" to "#87CEEB",
        "darkorchid" to "#9932CC",
        "firebrick" to "#B22222",
        "chocolate" to "#D2691E",
        "peru" to "#CD853F",
        "darkcyan" to "#008B8B",
        "orchid" to "#DA70D6",
        "mediumspringgreen" to "#00FA9A",
        "darkslategray" to "#2F4F4F",
        "darkkhaki" to "#BDB76B",
        "deepskyblue" to "#00BFFF",
        "seagreen" to "#2E8B57",
        "plum" to "#DDA0DD",
        "tomato" to "#FF6347",
        "darkolivegreen" to "#556B2F",
        "slateblue" to "#6A5ACD",
        "mediumaquamarine" to "#66CDAA",
        "dodgerblue" to "#1E90FF",
        "darkred" to "#8B0000"
        // Adicione outras cores conforme necessário
    )

    constructor(activity: Activity) {
        this.activity = activity
    }

    fun substituirCorPadrao(cor: String): String {
        val coresPadrao = mapOf(
            "none" to "#00000000",
            "black" to "#000000",
            "white" to "#FFFFFF",
            "red" to "#FF0000",
            "blue" to "#0000FF",
            "green" to "#008000",
            "yellow" to "#FFFF00",
            "purple" to "#800080",
            "orange" to "#FFA500",
            "cyan" to "#00FFFF",
            "pink" to "#FFC0CB",
            "brown" to "#A52A2A",
            "gray" to "#808080",
            "magenta" to "#FF00FF",
            "lime" to "#00FF00",
            "teal" to "#008080",
            "olive" to "#808000",
            "maroon" to "#800000",
            "navy" to "#000080",
            "silver" to "#C0C0C0",
            "gold" to "#FFD700",
            "silver" to "#C0C0C0",
            "gold" to "#FFD700",
            "indigo" to "#4B0082",
            "turquoise" to "#40E0D0",
            "salmon" to "#FA8072",
            "darkgreen" to "#006400",
            "violet" to "#EE82EE",
            "olivedrab" to "#6B8E23",
            "coral" to "#FF7F50",
            "skyblue" to "#87CEEB",
            "darkorchid" to "#9932CC",
            "firebrick" to "#B22222",
            "chocolate" to "#D2691E",
            "peru" to "#CD853F",
            "darkcyan" to "#008B8B",
            "orchid" to "#DA70D6",
            "mediumspringgreen" to "#00FA9A",
            "darkslategray" to "#2F4F4F",
            "darkkhaki" to "#BDB76B",
            "deepskyblue" to "#00BFFF",
            "seagreen" to "#2E8B57",
            "plum" to "#DDA0DD",
            "tomato" to "#FF6347",
            "darkolivegreen" to "#556B2F",
            "slateblue" to "#6A5ACD",
            "mediumaquamarine" to "#66CDAA",
            "dodgerblue" to "#1E90FF",
            "darkred" to "#8B0000"
            // Adicione outras cores conforme necessário
        )

        return coresPadrao.getOrElse(cor) { cor }
    }


   suspend fun loadSvgAndAddImageView(urlString: String) : String = suspendCoroutine { continuation ->

        CoroutineScope(Dispatchers.IO).launch {
            val fileType = when {
                urlString.endsWith(".svg", ignoreCase = true) -> "SVG"
                urlString.endsWith(".png", ignoreCase = true) -> "PNG"
                else -> "Outro Tipo"
            }
            Log.d("Itens","Imagem: ${fileType}")
            var bitmap: Bitmap? = null;

            if (fileType == "SVG") {
                // Processar o SVG

                bitmap = try {


                        val url = URL(urlString)
                        val connection = url.openConnection() as HttpURLConnection
                        connection.connect()

                        val inputStream = connection.inputStream
                        dadosSVG =  inputStreamToXML(inputStream)

                    val bitmap  =  criarBitmapDeSVG(dadosSVG)

                        withContext(Dispatchers.Main) {
                            continuation.resume(dadosSVG)
                        }


                       bitmap


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
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            activity,
                            "houve um problema ao processar o SVG",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else if (fileType == "PNG") {


            } else {
                // Mostrar um Toast para outros tipos de arquivo
                withContext(Dispatchers.Main) {
                    Toast.makeText(activity, "Tipo de arquivo não suportado", Toast.LENGTH_SHORT).show()
                }

            }

            bitmap?.let { criarImageview(it) }
        }

       return@suspendCoroutine
    }

    suspend fun criarSVGToBitmap(urlString : String) : String = suspendCoroutine { continuation ->

        CoroutineScope(Dispatchers.IO).launch {
            var bitmap: Bitmap? = null;
            // Processar o SVG
            bitmap = try {


                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()

                val inputStream = connection.inputStream
                dadosSVG =  inputStreamToXML(inputStream)

                val bitmap  =  criarBitmapDeSVG(dadosSVG)

                withContext(Dispatchers.Main) {
                    continuation.resume(dadosSVG)
                }


                bitmap


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
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        activity,
                        "houve um problema ao processar o SVG",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            bitmap?.let { criarImageview(it) }
        }


        return@suspendCoroutine
    }
     suspend fun criarPNGToBitmap(urlString : String){
        var bitmap: Bitmap? = null;
        // Processar o PNG
        bitmap = try {

            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            val inputStream = connection.inputStream

            // Decodifica a InputStream diretamente em um Bitmap
            val bitmap = BitmapFactory.decodeStream(inputStream)

            // Faça aqui qualquer manipulação ou processamento adicional necessário para o PNG
            Log.d("Itens","Bitmap criado")
            bitmap

        } catch (e: IOException) {

            Log.d("Erru", "- ${e.message}")
            e.printStackTrace()
            null
        }

        if (bitmap != null) {
            bitmap?.let { criarImageview(it) }
        } else {
            // Se houve um problema ao processar o PNG
            Log.d("Erru", "houve um problema ao processar o PNG")
        }
    }

    fun criarBitmapDeSVG(svgXml: String): Bitmap {
        val svg = SVG.getFromString(svgXml)
        val picture = svg.renderToPicture()
        val bitmap = Bitmap.createBitmap(picture.width, picture.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawPicture(picture)

        return bitmap
    }

    suspend fun criarImageview(bitmap: Bitmap) {
        withContext(Dispatchers.Main) {
            if (bitmap != null) {
                // Criar um novo ImageView
                val novoImageView = ImageView(activity) // Substitua pelo contexto do seu adapter

                idImageView =  View.generateViewId() // Gera um ID único para o novo ImageView


                novoImageView.id = idImageView
                novoImageView.setImageBitmap(bitmap)
                Log.d("Itens"," id gerado ${idImageView}")
                // Definir as configurações de layout para o novo ImageView
                val layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.MATCH_PARENT
                )
                novoImageView.layoutParams = layoutParams

                // Adicionar o novo ImageView ao ConstraintLayout
                val constraintLayout =
                    activity.findViewById<ConstraintLayout>(R.id.constraintlayoutMain) // Substitua pelo ID do seu ConstraintLayout


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

    fun inputStreamToXML(inputStream: InputStream): String {
        // Lê o InputStream
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()

        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            //  Log.d("my_svg","line- ${line}")
            stringBuilder.append(line)
        }
       // Log.d("Itens","Dados-x: ${stringBuilder.length}")
        return stringBuilder.toString()
    }
    fun obterCoresComSubstituicao(svgXml: String): List<String> {
        val coresOriginais = mutableListOf<String>()

        try {
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(StringReader(svgXml))

            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        val tagName = parser.name
                        if (tagName.equals("clipPath", ignoreCase = true)) {
                            // Ignorar elementos de controle, como clipPath
                            skipElement(parser)
                        } else if (tagName.equals("rect", ignoreCase = true)) {
                            // Se quiser ignorar também o conteúdo de certos elementos, como rect, pode ajustar aqui
                            skipElement(parser)
                        } else {
                            val cor = parser.getAttributeValue(null, "fill")
                            if (cor != null && cor != "none") {
                                val corSubstituida = substituirCorPadrao(cor)
                                coresOriginais.add(corSubstituida)
                            }
                        }
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return coresOriginais
    }

    fun obterCores(svgXml: String): List<String> {
         coresOriginais = mutableListOf<String>()

        try {
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(StringReader(svgXml))

            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        val tagName = parser.name
                        if (tagName.equals("clipPath", ignoreCase = true)) {
                            // Ignorar elementos de controle, como clipPath
                            skipElement(parser)
                        } else if (tagName.equals("rect", ignoreCase = true)) {
                            // Se quiser ignorar também o conteúdo de certos elementos, como rect, pode ajustar aqui
                             skipElement(parser)
                        } else {
                            val cor = parser.getAttributeValue(null, "fill")
                            if (cor != null && cor != "none") {
                                coresOriginais.add(cor)

                            }
                        }
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }



        return coresOriginais
    }

    private fun skipElement(parser: XmlPullParser) {
        var depth = 1
        while (depth > 0) {
            when (parser.next()) {
                XmlPullParser.START_TAG -> depth++
                XmlPullParser.END_TAG -> depth--
            }
        }
    }






    fun obterCoresOriginal(svgXml: String): List<String> {
        coresOriginais = mutableListOf()

        val pattern =
            """(?:fill|stop-color)\s*=\s*["']((?:[a-zA-Z]+)|(?:#[0-9a-fA-F]{3,8}))["']""".toRegex()
        val matchResults = pattern.findAll(svgXml)
         coresOriginais = matchResults.map { it.groupValues[1] }.toMutableList()
        coresOriginais.removeAll { it == "none" }
        val valoresFill = matchResults.map {
            val cor = it.groupValues[1].toLowerCase()
            coresPadrao.getOrDefault(cor, cor)
        }.filter { it != coresPadrao["none"] }.toList()

        // Convertendo para um conjunto para manter apenas valores únicos
        // val valoresUnicos = valoresFill.toSet().toList()

        return valoresFill
    }

    fun pertenceAGrupoDeClipping(posicaoInicio: Int, svgXml: String): Boolean {
        val trechoSvg = svgXml.substring(0, posicaoInicio)
        return trechoSvg.contains("<clipPath")
    }


    fun obterValoresFillColorDaStringSVG(svgXml: String): List<String> {
        val pattern =
            """(?:fill|stop-color)\s*=\s*["'](#(?:[0-9a-fA-F]{3}|[0-9a-fA-F]{6}|[0-9a-fA-F]{8}))["']""".toRegex()
        val matchResults = pattern.findAll(svgXml)
        val valoresFill = matchResults.map { it.groupValues[1] }.toList()

        // Convertendo para um conjunto para manter apenas valores únicos
        //  val valoresUnicos = valoresFill.toSet().toList()

        return valoresFill
    }

    fun substituirCoresNaStringSVG(
        svgXml: String,
        coresOriginais: List<String>,
        coresSubstitutas: List<String>
    ): String {
        Log.d("my_svg", "coresOriginais- ${coresOriginais}")
        Log.d("my_svg", "coresSubstitutas- ${coresSubstitutas}")
        var svgModificado = svgXml

        if (coresOriginais.size != coresSubstitutas.size) {
            throw IllegalArgumentException("As listas de cores originais e substitutas devem ter o mesmo tamanho.")
        }

        for (i in coresOriginais.indices) {
            svgModificado = svgModificado.replace(coresOriginais[i], coresSubstitutas[i])
        }

        return svgModificado
    }

    fun getIdImageview() : Int{
        return idImageView
    }
    fun atualizarImageView(idImageView: Int, novoBitmap: Bitmap) {
       // withContext(Dispatchers.Main) {
            val imageView = activity.findViewById<ImageView>(idImageView)
            imageView.setImageBitmap(novoBitmap)
      //  }
    }

}