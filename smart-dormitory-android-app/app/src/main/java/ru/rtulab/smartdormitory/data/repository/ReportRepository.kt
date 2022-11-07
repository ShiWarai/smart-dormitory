package ru.rtulab.smartdormitory.data.repository

import ru.rtulab.smartdormitory.common.ResponseHandler
import ru.rtulab.smartdormitory.data.remote.api.report.ReportApi
import ru.rtulab.smartdormitory.data.remote.api.report.models.RequestCreateReport
import javax.inject.Inject

class ReportRepository @Inject constructor(
    private var reportApi: ReportApi,
    private val handler: ResponseHandler
) {
    suspend fun createReport(requestCreateReport: RequestCreateReport) = handler {
        reportApi.createReport(requestCreateReport)
    }

    suspend fun getAll() = handler {
        reportApi.getAll()
    }
}