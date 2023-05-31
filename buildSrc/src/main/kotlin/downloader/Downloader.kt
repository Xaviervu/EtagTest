import downloader.FileDownloadService
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.charset.Charset

class Downloader {
    companion object {

        /**
         * Download an url if the file is not up to date
         * @param url - version to write to the file
         * @param fileName - path of the file to write to
         */
        @JvmStatic
        fun rfDownloader(url: String, cacheDir: String, filePath: String) {
            println("XavVeg download to $filePath")


            val cacheSize = 10 * 1024 * 1024L // 10 MB

            val cache = Cache(File(cacheDir), cacheSize)
            val logging = HttpLoggingInterceptor { message -> println(message) }
            logging.level = HttpLoggingInterceptor.Level.HEADERS
            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .cache(cache)
                .build()

            val baseUrl = URL(url).protocol + "://" + URL(url).host + "/"
            val file = url.substring(baseUrl.length, url.length)
            val builder: Retrofit.Builder = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(okHttpClient)


            val retrofit: Retrofit = builder.build()

            val result = retrofit.create(FileDownloadService::class.java)
            val response = result.downloadFileWithDynamicUrlSync(file).execute()
            val rawResponse: Response? = response.raw().networkResponse()
            println("XavVeg successful response = ${response.isSuccessful}; rawCode = ${rawResponse?.code()} ")
            if (!response.isSuccessful){
                println("XavVeg error happens: ${response.message()}")
            }
            val outputFile = File(filePath)
            if(outputFile.exists()) outputFile.delete()
            outputFile.createNewFile()
            val outputStream = FileOutputStream(outputFile)
            response.body()?.byteInputStream(Charset.forName("UTF-8"))?.copyTo(
                outputStream,
                bufferSize = DEFAULT_BUFFER_SIZE
            )
            outputStream.close()
            println("XavVeg $url Downloaded successfully to $outputFile.")


        }

    }
}
