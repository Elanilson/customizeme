package br.com.CustomizeMe.customizeme.remoto

import br.com.CustomizeMe.customizeme.model.dto.Roupa
import retrofit2.Response
import retrofit2.http.GET

interface API {
    @GET("getCamisa.php")
    suspend fun getRoupa() : Response<Roupa>

}