package com.example.diviction.infra.gcp

object GCP_URLs{
    val STORAGE_BASE_URL = "https://storage.googleapis.com/diviction/user-profile/"
    val COUNSELOR_BASIC_IMG_URL = STORAGE_BASE_URL + "counselor.png" // 상담사 기본 프로필 사진
    val PATIENT_BASIC_IMG_URL = STORAGE_BASE_URL + "patient.png"  // 환자 기본 프로필 사진
    val USER_PROFILE_URL = STORAGE_BASE_URL + "user-profile/"
}