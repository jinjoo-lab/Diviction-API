package com.example.diviction

import com.google.cloud.storage.Cors
import com.google.cloud.storage.HttpMethod
import com.google.cloud.storage.StorageOptions
import com.google.common.collect.ImmutableList
import org.springframework.stereotype.Component

@Component
class ConfigureBucketCors {
    fun configureBucketCors(
        projectId: String,
        bucketName: String,
        origin: String,
        responseHeader: String,
        maxAgeSeconds: Int
    ) {
        val storage = StorageOptions.newBuilder().setProjectId(projectId).build().service
        val bucket = storage.get(bucketName)

        val method = HttpMethod.GET

        val cors = Cors.newBuilder()
            .setOrigins(ImmutableList.of(Cors.Origin.of(origin)))
            .setMethods(ImmutableList.of(method))
            .setResponseHeaders(ImmutableList.of(responseHeader))
            .setMaxAgeSeconds(maxAgeSeconds)
            .build()

        bucket.toBuilder().setCors(ImmutableList.of(cors)).build().update()
    }
}