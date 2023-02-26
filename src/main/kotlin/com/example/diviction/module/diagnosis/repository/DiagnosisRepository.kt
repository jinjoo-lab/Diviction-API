package com.example.diviction.module.diagnosis.repository

import com.example.diviction.module.diagnosis.entity.DiagnosisResult
import org.springframework.data.jpa.repository.JpaRepository

interface DiagnosisRepository : JpaRepository<DiagnosisResult,Long> {
}
