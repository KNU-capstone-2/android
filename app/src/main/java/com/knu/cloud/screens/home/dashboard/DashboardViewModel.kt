package com.knu.cloud.screens.home.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knu.cloud.model.home.dashboard.DashboardClass
import com.knu.cloud.model.home.dashboard.DashboardDataSet
import com.knu.cloud.model.home.dashboard.DashboardData
import com.knu.cloud.model.home.dashboard.DashboardUsageData
import com.knu.cloud.repository.home.dashboard.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<DashboardState>(DashboardState.Loading) // uiState의 처음은 Loading으로 시작 -> LoadingScreen이 보여짐.
    val uiState = _uiState.asStateFlow()

    private var computeDataSet: MutableList<DashboardData>? = null
    private var volumeDataSet: MutableList<DashboardData>? = null
    private var networkDataSet: MutableList<DashboardData>? = null
    private lateinit var usageData: DashboardUsageData

    init {
        viewModelScope.launch {
            dashboardRepository.getDashboardData()
                .onSuccess {
                    Timber.tag("TEST").e("$it")
                    val total = it?.dashboardDataClass ?:testDashboardClass
                    computeDataSet = mutableListOf(
                        DashboardData("인스턴스", total.instanceCount,10-total.instanceCount),
                        DashboardData("VCPUs",total.vcpuCount, 10-total.vcpuCount),
                        DashboardData("RAM", total.ramCount, 2900-total.ramCount)
                    )

                    volumeDataSet = mutableListOf(
                        DashboardData("볼륨", total.volumeCount, 10-total.volumeCount),
                        DashboardData("볼륨 스냅샷", total.snapshotCount, 10-total.snapshotCount),
                        DashboardData("볼륨 스토리지", total.volumeStorageGB, 30-total.volumeStorageGB)
                    )

                    networkDataSet = mutableListOf(
                        DashboardData("Floating IP", total.floatingIpCount, 50-total.floatingIpCount),
                        DashboardData("보안 그룹", total.securityGroupCount, 10-total.securityGroupCount),
                        DashboardData("네트워크", total.networkCount, 10-total.networkCount),
                    )
                }.onFailure {
                    /*TODO*/
                }
            /*
            dashboardRepository.getDashboardUsageData()
                .onSuccess {
                    usageData = DashboardUsageData(
                        activeInstance = it.activeInstance,
                        useRAM = it.useRAM,
                        VCPUTime = it.VCPUTime,
                        GBTime = it.GBTime,
                        RAMTime = it.RAMTime,
                    )
                }.onFailure {
                    /*TODO*/
                }
            */
            usageData = DashboardUsageData(
                activeInstance = "1",
                useRAM = "20",
                VCPUTime = "3",
                GBTime = "4",
                RAMTime = "5",
            )
            _uiState.value = DashboardState.Success(
                dataSet = DashboardDataSet(
                    computeDataSet = computeDataSet!!,
                    volumeDataSet = volumeDataSet!!,
                    networkDataSet = networkDataSet!!
                ),
                usageDataSet = usageData
            ) // uiState를 Success로 바꿔줌 -> 정보 Screen이 보여짐.
            delay(1000)
        }
    }
}


val testDashboardClass = DashboardClass(
    instanceCount = 1,
    vcpuCount = 1,
    ramCount = 192,
    volumeCount = 2,
    snapshotCount = 0,
    volumeStorageGB = 2,
    floatingIpCount = 1,
    securityGroupCount = 1,
    networkCount = 1,
)