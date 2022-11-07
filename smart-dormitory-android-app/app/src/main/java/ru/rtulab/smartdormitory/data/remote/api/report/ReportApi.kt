package ru.rtulab.smartdormitory.data.remote.api.report

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.rtulab.smartdormitory.data.remote.api.report.models.ReportDto
import ru.rtulab.smartdormitory.data.remote.api.report.models.RequestCreateReport

interface ReportApi {

    @POST("/reports/")
    suspend fun createReport(
        @Body report: RequestCreateReport
    ): Response<Unit>

    @GET("/reports/")
    suspend fun getAll(): List<ReportDto>
}