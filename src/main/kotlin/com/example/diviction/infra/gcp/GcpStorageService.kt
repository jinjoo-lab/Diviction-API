package com.example.diviction.infra.gcp

import com.example.diviction.infra.gcp.GCP_URLs.USER_PROFILE_URL
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Component
class GcpStorageService(
        private val storage: Storage,
        @Value("\${spring.cloud.gcp.storage.bucket}")
        private val bucketName: String

) {
    fun uploadFileToGCS(imageFile: MultipartFile): String {
        val randUUID = UUID.randomUUID().toString()
        val fileEx = getFileEx(imageFile.originalFilename!!)
        val blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, "user-profile/$randUUID")
                        .setContentDisposition("/user-profile")
                        .setContentType("image/$fileEx")
                        .build(),
                imageFile.inputStream
        )
        return "$USER_PROFILE_URL$randUUID"
    }

    fun getFileEx(fileName: String): String {
        return fileName.substring(fileName.lastIndexOf(".") + 1).lowercase()
    }
}