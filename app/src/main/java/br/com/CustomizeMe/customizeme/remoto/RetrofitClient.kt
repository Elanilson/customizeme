package br.com.CustomizeMe.customizeme.remoto


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitClient private constructor(){

    companion object{
        private lateinit var INSTANCE : Retrofit

        private  fun getRetrofitInstace() : Retrofit{
            if(!Companion::INSTANCE.isInitialized){
                synchronized(RetrofitClient::class.java){
                    INSTANCE = Retrofit.Builder()
                        .baseUrl("https://apkdoandroidonline.com/Simulador/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                }

            }
            return INSTANCE
        }
        fun <T> classService(classService : Class<T>) : T {
            return getRetrofitInstace().create(classService)
        }
    }


}