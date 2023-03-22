package com.example.diviction.infra.gcp

object GCP_URLs{
    val COUNSELOR_BASIC_IMG_URL = "https://storage.googleapis.com/diviction/user-profile/counselor.png" // 상담사 기본 프로필 사진
    val PATIENT_BASIC_IMG_URL = "https://storage.googleapis.com/diviction/user-profile/patient.png"  // 환자 기본 프로필 사진
    val STORAGE_BASE_URL = "https://storage.cloud.google.com/diviction/"
    val USER_PROFILE_URL = STORAGE_BASE_URL + "user-profile/"
}