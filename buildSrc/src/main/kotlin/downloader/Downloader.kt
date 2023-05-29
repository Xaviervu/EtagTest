import downloader.FileDownloadService
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.io.File

class Downloader {
    companion object {

        /**
         * Download an url if the file is not up to date
         * @param url - version to write to the file
         * @param fileName - path of the file to write to
         */
        @JvmStatic
        fun rfDownloader(url: String, cacheDir: String, fileName: String) {
            println("XavVeg download to $fileName")


            val cacheSize = 10 * 1024 * 1024L // 10 MB

            val cache = Cache(File(cacheDir), cacheSize)

            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .cache(cache)
                .build()

            val builder: Retrofit.Builder = Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)


            val retrofit: Retrofit = builder.build()

            val result = retrofit.create(FileDownloadService::class.java)
            val response  = result.downloadFileWithDynamicUrlSync(url).execute()
            println( "XavVeg successful response = ${response.isSuccessful}")
            val outputFile =  File(cacheDir + fileName)
            val fileIsCreated = outputFile.createNewFile()
            println("XavVeg $fileIsCreated")
            outputFile.bufferedWriter().use {
//                for (line in lines) {
                it.write(url)
                it.newLine()
//                }
            }
            println("XavVeg $url Downloaded successfully to $fileName.")

//            val androidPart = "#elif defined(ANDROID)"
//            val androidVersionStrings = mutableMapOf(
//                    0 to "#define RF_VERSION_MAJOR",
//                    1 to "#define RF_VERSION_MINOR",
//                    2 to "#define RF_VERSION_REVISION",
//                    3 to "#define RF_VERSION_BUILD"
//            )
//
//            val numberVersion = url.split(".")
//            var isAndroidPart = false
//
//            val lines = File(path).bufferedReader().use {
//                it.readLines().toMutableList()
//            }
//            run breaker@{
//                lines.forEachIndexed { linesIndex, line ->
//                    if (isAndroidPart) {
//                        if (line.trim().startsWith("//") || line.trim().isEmpty()) {
//                            return@forEachIndexed
//                        } else {
//                            androidVersionStrings.asIterable().firstOrNull {
//                                line.contains(it.value)
//                            }?.let {
//                                lines[linesIndex] =
//                                        setVersion(lines[linesIndex], numberVersion[it.key])
//                                androidVersionStrings.remove(it.key)
//                            }?:let {
//                                throw Exception("$path[${linesIndex+1}] Expected '${androidVersionStrings.values.first()}' but discovered '$line'")
//                            }
//
//                            if (androidVersionStrings.isEmpty()) {
//                                return@breaker
//                            }
//                        }
//                    } else if (line.contains(androidPart)){
//                        isAndroidPart = true
//                    }
//                }
//            }
//
//            if (androidVersionStrings.isNotEmpty()) {
//                val notFoundStrings = StringBuilder()
//                androidVersionStrings.forEach {
//                    notFoundStrings.append("'${it.value}',\n")
//                }
//                throw Exception("Strings $notFoundStrings not found in file $path")
//            }
//

        }

       
//        /**
//         * Updating the version in the string
//         * @param string - string for updating version
//         * @param version - version to write to the string
//         */
//        private fun setVersion(string: String, version: String): String {
//            val commentDelimiter = "//"
//            val comment = StringBuilder("")
//            val versionString: String
//            val newStr = StringBuilder()
//            if (string.contains(commentDelimiter)) {
//                comment.append(" $commentDelimiter").append(string.substringAfter("//"))
//                versionString = string.substringBefore("//")
//            } else {
//                versionString = string
//            }
//
//            newStr.append(versionString.trim().replaceAfterLast(' ', version))
//            newStr.append(comment)
//            return newStr.toString()
//        }
    }
}
