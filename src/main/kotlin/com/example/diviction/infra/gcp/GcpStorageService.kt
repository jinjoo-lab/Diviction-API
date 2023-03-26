package com.example.diviction.infra.gcp

import com.example.diviction.ConfigureBucketCors
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
        private val bucketName: String,
        private val configureBucketCors: ConfigureBucketCors

) {
    init {
        configureBucketCors.configureBucketCors(
                projectId = "sturdy-now-380610",
                bucketName = bucketName,
                origin = "*",
                responseHeader = "Content-Type",
                maxAgeSeconds = 3600
        )
    }
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