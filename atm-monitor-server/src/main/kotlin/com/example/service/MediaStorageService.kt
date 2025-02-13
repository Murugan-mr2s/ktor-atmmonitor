package com.example.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.Buffer
import kotlinx.io.IOException
import kotlinx.io.copyTo
import org.slf4j.LoggerFactory
import java.io.File
import java.nio.file.*

enum class StorageResult{
    SUCCESS,FAILED
}

class MediaStorageService {

    val LOG = LoggerFactory.getLogger(MediaStorageService::class.java)
    val base = "medias"
    val basePath= Paths.get(base).normalize()

    init {
        newDirectory(basePath)
    }

    private fun newDirectory(dpath : Path) : StorageResult {
        var flag = StorageResult.FAILED
        try {
             if( ! Files.exists(dpath)) {
                Files.createDirectory(dpath)
            }
            flag=StorageResult.SUCCESS

        } catch (_: IOException) {
            LOG.error("unable to create base directory")
        }
        return flag
    }


     suspend fun saveMedia(atmId:String, filename : String, buffer: Buffer) : String? {

           val atmMediaPath= basePath.resolve(atmId).normalize()
           val isSuccess = newDirectory(atmMediaPath)
           if( isSuccess == StorageResult.SUCCESS )  {
               val ext = filename.substringAfter(".")
               val newfilename = atmId.plus("_").plus(System.currentTimeMillis()).plus("."+ext)
               val mediaFile=atmMediaPath.resolve(newfilename).normalize().toFile()

               withContext(Dispatchers.IO) {
                   mediaFile.outputStream().use { output->
                       buffer.copyTo(output)
                       buffer.clear()
                   }
               }
               return newfilename
           }
        return null
    }

    fun downloadMedia(atmId:String, file:String) : File? {
        val atmMediaPath= basePath.resolve(atmId).resolve(file).normalize()
        return  if (atmMediaPath.toFile().exists()) atmMediaPath.toFile() else null
    }
}