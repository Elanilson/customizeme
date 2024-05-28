package br.com.CustomizeMe.customizeme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.CustomizeMe.customizeme.adapter.PersonalizacaoAdapter
import br.com.CustomizeMe.customizeme.databinding.ActivityEditorBinding
import br.com.CustomizeMe.customizeme.fragments.CoresFragment
import br.com.CustomizeMe.customizeme.interfaces.OnClickListener
import br.com.CustomizeMe.customizeme.interfaces.OnDadosEnviadosListener
import br.com.CustomizeMe.customizeme.model.CamadaPersonalizavel
import br.com.CustomizeMe.customizeme.model.dto.Roupa
import br.com.CustomizeMe.customizeme.model.dto.TipoGola
import br.com.CustomizeMe.customizeme.model.dto.toCamisa
import br.com.CustomizeMe.customizeme.model.roupa.FrenteVerso
import br.com.CustomizeMe.customizeme.model.roupa.Manequim
import br.com.CustomizeMe.customizeme.model.roupa.Mcamisa
import br.com.CustomizeMe.customizeme.model.roupa.MontagemManeqim
import br.com.CustomizeMe.customizeme.model.roupa.MtipoGola


import br.com.CustomizeMe.customizeme.remoto.API
import br.com.CustomizeMe.customizeme.remoto.RetrofitClient
import br.com.CustomizeMe.customizeme.utils.UtilsSVG
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.util.ArrayList

class EditorActivity : AppCompatActivity()  , OnDadosEnviadosListener{
    private val binding by lazy { ActivityEditorBinding.inflate(layoutInflater) }
    private val adapter = PersonalizacaoAdapter()
     var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    private var frameLayoutSheet: FrameLayout? = null
    private var onDadosEnviadosListener: OnDadosEnviadosListener? = null
    private val fragmentCores = CoresFragment()
    private var listaDeCores : MutableList<String> = mutableListOf()
    private var mapImagem : Map<String,List<String>> = mapOf()
    private var itens : MutableList<CamadaPersonalizavel> = mutableListOf()
    private lateinit var utilsSVG : UtilsSVG
    private val service =  RetrofitClient.classService(API::class.java)
    private var montagemManeqimFrente : MutableList<MontagemManeqim> = mutableListOf()
    private var montagemManeqimVerso : MutableList<MontagemManeqim> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        frameLayoutSheet = binding.frameLayoutSheet
        bottomSheetBehavior = BottomSheetBehavior.from(frameLayoutSheet!!)
        bottomSheetBehavior?.setState(BottomSheetBehavior.STATE_HIDDEN)
        utilsSVG = UtilsSVG(this)
        configRecyclerview()
        buscarRoupa()
        // val svgUrl = "https://apkdoandroidonline.com/totem/estampa.svg"
        //camisa - molde
     /*   val svgUrl3 = "https://apkdoandroidonline.com/camisas_personalizaveis/camisa_pesca_manga_longa_masculino/molde/camisa/camisa_pesca_mangas_longa.svg"
        //camisa - textura
        val svgUrl = "https://apkdoandroidonline.com/camisas_personalizaveis/camisa_pesca_manga_longa_masculino/textura/camisa/camisa_pesca_masculino.png"
        // gola - molde
        val svgUrl4 = "https://apkdoandroidonline.com/camisas_personalizaveis/camisa_pesca_manga_longa_masculino/molde/gola/gola_redonda_pesca.svg"
        val svgUrl5 = "https://apkdoandroidonline.com/camisas_personalizaveis/camisa_pesca_manga_longa_masculino/molde/gola/gola_redonda_fundo_pesca.svg"
        //gola - textura
        val svgUrl2 = "https://apkdoandroidonline.com/camisas_personalizaveis/camisa_pesca_manga_longa_masculino/textura/gola/gola_redonda_textura_pesca.png"
        //detalhes - molde
        val svgUrl6 = "https://apkdoandroidonline.com/camisas_personalizaveis/camisa_pesca_manga_longa_masculino/molde/detalhe/camisa_pesca_detalhe.svg"

      //  val imagens = listOf<String>(svgUrl,svgUrl2,svgUrl3,svgUrl4,svgUrl5,svgUrl6)
        val imagens = listOf<String>(svgUrl3,svgUrl4,svgUrl5,svgUrl6)

       CoroutineScope(Dispatchers.Main).launch {
           carregarImagemExtrairDados(imagens)
       }*/

       /* utilsSVG.loadSvgAndAddImageView(svgUrl4)
        utilsSVG.loadSvgAndAddImageView(svgUrl5)
        utilsSVG.loadSvgAndAddImageView(svgUrl6)
        //  loadSvgAndAddImageView(svgUrl3)
        utilsSVG.loadSvgAndAddImageView(svgUrl)
        utilsSVG.loadSvgAndAddImageView(svgUrl2)*/


    }


     fun buscarRoupa(){
         CoroutineScope(Dispatchers.IO).launch {
             var retorno : Response<Roupa>? = null
             try{
                 retorno = service.getRoupa()
                 if(retorno != null){
                     if(retorno.isSuccessful){
                         Log.d("Resposta"," sucesso ${retorno.body().toString()}")
                         val vestuarioSuperior : MutableList<FrenteVerso<Mcamisa>> = mutableListOf()


                         val camisasRecebidas = retorno.body()?.camisa

                         if (camisasRecebidas != null) {
                             vestuarioSuperior.add(FrenteVerso(camisasRecebidas[0].toCamisa(), camisasRecebidas[1].toCamisa()))
                             vestuarioSuperior.add(FrenteVerso(camisasRecebidas[2].toCamisa(), camisasRecebidas[3].toCamisa()))
                         }

                         val manequim = Manequim(vestuarioSuperior,null, null, null)
                         processarManequim(manequim)


                     }else{
                         withContext(Dispatchers.Main){
                            Toast.makeText(applicationContext,retorno.message(),Toast.LENGTH_SHORT).show()
                         }
                     }
                 }else{
                     withContext(Dispatchers.Main){
                        Toast.makeText(applicationContext,"nada retornado",Toast.LENGTH_SHORT).show()
                     }
                     Log.d("Resposta"," null , nada retornado")
                 }

             }catch (e: Exception){
                 withContext(Dispatchers.Main){
                     e.message?.let { Toast.makeText(applicationContext,it,Toast.LENGTH_SHORT).show() }
                 }
                 e.message?.let { Log.d("Resposta", it) }
                 e.printStackTrace()
             }
         }
    }

    private fun <T> processarManequim(manequim: Manequim<T>){
        val vestuarioSuperior = manequim.vestuarioSuperior
        var tipoGola : MutableList<MtipoGola> = mutableListOf()



        CoroutineScope(Dispatchers.IO).launch {
       if( vestuarioSuperior != null){
           vestuarioSuperior.forEachIndexed  { index, camada ->

               if(index == 0){//Frente
                   val camisaMolde = camada.frente as Mcamisa
                   val camisaTextura = camada.verso as Mcamisa
                   Log.d("Andamento","Camisa-molde")
                   //molde-camisa
                   montagemManeqimFrente.add(MontagemManeqim(camisaMolde.id,camisaMolde.titulo,camisaMolde.imagemUrl,null,null))

                   //tipo de golas
                   pecorrerTiposGolas(camisaMolde.tipo_gola,"Molde",index)
                   Log.d("Andamento","tipoGola-molde")
                   //molde-punho
                   montagemManeqimFrente.add(MontagemManeqim(camisaMolde.punho?.id,camisaMolde.punho?.titulo,camisaMolde.punho?.imagemUrl,null,null))
                   Log.d("Andamento","punho-molde")
                   //textura-camisa
                   montagemManeqimFrente.add(MontagemManeqim(camisaTextura.id,camisaTextura.titulo,camisaTextura.imagemUrl,null,null))
                   Log.d("Andamento","camisa-textura")
                   //tipo de golas
                   pecorrerTiposGolas(camisaTextura.tipo_gola,"Textura",index)


               }else{//Verso
                   val camisaMolde = camada.frente as Mcamisa
                   val camisaTextura = camada.verso as Mcamisa

                   //molde-camisa
                   montagemManeqimVerso.add(MontagemManeqim(camisaMolde.id,camisaMolde.titulo,camisaMolde.imagemUrl,null,null))

                   //tipo de golas
                   pecorrerTiposGolas(camisaMolde.tipo_gola,"Molde",index)

                   //molde-punho
                   montagemManeqimVerso.add(MontagemManeqim(camisaMolde.punho?.id,camisaMolde.punho?.titulo,camisaMolde.punho?.imagemUrl,null,null))

                   //textura-camisa
                   montagemManeqimVerso.add(MontagemManeqim(camisaTextura.id,camisaTextura.titulo,camisaTextura.imagemUrl,null,null))

                   //tipo de golas
                   pecorrerTiposGolas(camisaTextura.tipo_gola,"Textura",index)
               }




           }

       }




                Log.d("Andamento","Entrei no loop: ${montagemManeqimFrente.size}")
                Log.d("Andamento","Entrei no loop: ${montagemManeqimFrente[5].toString()}")
            montagemManeqimFrente.forEachIndexed  { index, manequim ->
                Log.d("Andamento","Entrei no loop: ${index}")
            manequim.imagemUrl?.let { url->
                    Log.d("Itens","Imagem: ${url}")

                val fileType = when {
                    url.endsWith(".svg", ignoreCase = true) -> "SVG"
                    url.endsWith(".png", ignoreCase = true) -> "PNG"
                    else -> "Outro Tipo"
                }

                if (fileType == "SVG") {
                    var dados = utilsSVG.criarSVGToBitmap(url)
                    Log.d("Itens","Dados ${dados.length}")


                    val cores = utilsSVG.obterCoresComSubstituicao(dados)
                    Log.d("Resposta","ooooo: ${utilsSVG.coresOriginais.size}")
                    //    dados =  utilsSVG.substituirCoresNaStringSVG(dados, utilsSVG.coresOriginais, cores)
                    manequim.cores = cores
                    manequim.coresSubstitutas = cores
                    Log.d("Itens","Cores ${manequim.titulo}")
                    Log.d("Itens","Cores ${cores.size}")
                    Log.d("Itens","Cores ${cores}")

                    val id = utilsSVG.getIdImageview()
                    Log.d("Itens","ID ${id}")

                    manequim.titulo?.let {
                        dados =  utilsSVG.substituirCoresNaStringSVG(dados, utilsSVG.coresOriginais, cores)
                        itens.add(CamadaPersonalizavel(id,it,dados, cores, cores))
                    }

                } else if (fileType == "PNG") {
                    utilsSVG.criarPNGToBitmap(url)
                }else {

                }



                   // montagemManeqimFrente[index] = manequim
                }
            }



             /*   for (manequim in montagemManeqimFrente){
                     Log.d("Resposta","montagem: ${manequim.imagemUrl}")
                }*/




        }


    }

    fun pecorrerTiposGolas(tipoGola : List<MtipoGola>,tipoCamada : String, index : Int){
        for (tipo in tipoGola){

            val golas = tipo.gola

            for (gola in golas){
                val fundos = gola.fundoGola
               // Log.d("Resposta","Gola-Molde-url: ${gola.imagemUrl}")

                if(index == 0){// frente
                    if(tipoCamada.equals("Molde")){
                        //molde-gola
                        montagemManeqimFrente.add(MontagemManeqim(gola.id,gola.titulo,gola.imagemUrl,null,null))
                    }else{
                        //textura-gola
                        montagemManeqimFrente.add(MontagemManeqim(gola.id,gola.titulo,gola.imagemUrl,null,null))
                        Log.d("Andamento","tipoGola-textura")
                    }
                }else{//verso
                    if(tipoCamada.equals("Molde")){
                        //molde-gola
                        montagemManeqimVerso.add(MontagemManeqim(gola.id,gola.titulo,gola.imagemUrl,null,null))
                    }else{
                        //textura-gola
                        montagemManeqimVerso.add(MontagemManeqim(gola.id,gola.titulo,gola.imagemUrl,null,null))
                    }
                }

                for (fundo in fundos){
                    val fundoGola = fundo.imagemUrl

                    if(index == 0){// frente
                        //molde-fundoGola
                        montagemManeqimFrente.add(MontagemManeqim(fundo.id,fundo.titulo,fundo.imagemUrl,null,null))
                    }else{//verso
                        //molde-fundoGola
                        montagemManeqimVerso.add(MontagemManeqim(fundo.id,fundo.titulo,fundo.imagemUrl,null,null))
                    }
                  //  Log.d("Resposta","GolaFundo-Molde-url: ${fundo.imagemUrl}")
                }
            }
        }
    }

    private suspend fun carregarImagemExtrairDados(imagens : List<String>){

        CoroutineScope(Dispatchers.IO).launch {
            for (imagem in imagens){

                val dados = utilsSVG.loadSvgAndAddImageView(imagem)
                Log.d("Itens","pegar dados ${dados.length}")


               val cores = utilsSVG.obterValoresFillColorDaStringSVG(dados)
                Log.d("Itens","pegar cores ${cores.size}")

                val id = utilsSVG.getIdImageview()
                Log.d("Itens","pegar id  ${id}")
                itens.add(CamadaPersonalizavel(id,"item",dados, cores, cores))
            }

            Log.d("Itens",itens.toString())
        }
    }





    private fun configRecyclerview(){
        val manager = LinearLayoutManager(this, RecyclerView.HORIZONTAL,false)
        binding.recyclerviewMenuPersonalizacao.adapter = adapter
        binding.recyclerviewMenuPersonalizacao.layoutManager = manager
        binding.recyclerviewMenuPersonalizacao.setHasFixedSize(true)

        adapter.attackListener(object : OnClickListener {
            override fun onClick() {
                bottomSheetBehavior?.setState(BottomSheetBehavior.STATE_COLLAPSED)
                // bottomSheetBehavior?.setState(BottomSheetBehavior.STATE_HIDDEN)
                // Toast.makeText(applicationContext, "Click", Toast.LENGTH_SHORT).show()

                val bundle = Bundle().apply {
                  //  putStringArrayList("cores", ArrayList(listaDeCores))
                  //  putParcelableArrayList Log.d("Cores",listaDeCores.toString())("itens", ArrayList(itens))
                    Log.d("Itens","itens enviados ${itens[1]}")
                   // putParcelable("item", itens[1])
                    putParcelableArrayList("itens", ArrayList(itens))
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
        Toast.makeText(this@EditorActivity, dados, Toast.LENGTH_SHORT).show()
    }

    override fun onDadosEnviados(itens: List<CamadaPersonalizavel>) {
        Toast.makeText(this@EditorActivity, "Valor recebido ${itens.size}", Toast.LENGTH_SHORT).show()
        var dados = itens[0].dados
        var coresOriginais = itens[0].coresOriginais
        var coresSubstitutas= itens[0].coresSubstitutas
        dados =  utilsSVG.substituirCoresNaStringSVG(dados, coresOriginais, coresSubstitutas)
        utilsSVG.criarBitmapDeSVG(dados)
    }

    override fun onDadosEnviados(itens: CamadaPersonalizavel) {
        var dados = itens.dados
        var coresOriginais = itens.coresOriginais
        var coresSubstitutas= itens.coresSubstitutas
        Log.d("Itens","original ${coresOriginais}")
        Log.d("Itens","coresSubstitutas ${coresSubstitutas}")
        dados =  utilsSVG.substituirCoresNaStringSVG(dados, coresOriginais, coresSubstitutas)
        utilsSVG.criarBitmapDeSVG(dados)?.let {
           CoroutineScope(Dispatchers.Main).launch {
               Log.d("Itens"," id obtido  ${itens.id}")
               utilsSVG.atualizarImageView(itens.id,it)
           }
        }
    }


}