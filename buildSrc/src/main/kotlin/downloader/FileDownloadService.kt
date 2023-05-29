package downloader

import okhttp3.Call
import okhttp3.ResponseBody


import retrofit2.http.GET
import retrofit2.http.Url





interface FileDownloadService {
    @GET
    fun downloadFileWithDynamicUrlSync(@Url fileUrl: String): Call
}