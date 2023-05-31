package downloader

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url
interface FileDownloadService {
    @GET
    fun downloadFileWithDynamicUrlSync(@Url fileUrl: String): Call<String>
}