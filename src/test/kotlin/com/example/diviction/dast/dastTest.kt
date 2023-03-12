package com.example.diviction.dast

import com.example.diviction.module.DAST.constant.DASTDRUG
import com.example.diviction.module.DAST.dto.SaveDastDto
import com.example.diviction.module.DAST.entity.Dast
import com.example.diviction.module.DAST.service.DastService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
class dastTest @Autowired constructor(val dastService: DastService) {

    @Test
    fun dastTest()
    {

    }
}
