package com.knu.cloud.screens.home.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knu.cloud.model.home.dashboard.DashboardDataSet
import com.knu.cloud.model.home.dashboard.DashboardData
import com.knu.cloud.repository.home.dashboard.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

val computeDataSet = mutableListOf(
    DashboardData("인스턴스", 4,6),
    DashboardData("VCPUs",2, 8),
    DashboardData("RAM", 3, 7)
)

val volumeDataSet = mutableListOf(
    DashboardData("볼륨", 5, 5),
    DashboardData("볼륨 스냅샷", 1, 9),
    DashboardData("볼륨 스토리지", 2, 8)
)

val networkDataSet = mutableListOf(
    DashboardData("Floating IP", 1, 50),
    DashboardData("보안 그룹", 1, 9),
    DashboardData("네트워크", 3, 7),
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<DashboardState>(DashboardState.Loading) // uiState의 처음은 Loading으로 시작 -> LoadingScreen이 보여짐.
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            /*
            dashboardRepository.getDashboardData()
                .onSuccess {

                }.onFailure {

                }
            */
            delay(2000)
            _uiState.value = DashboardState.Success(
                DashboardDataSet(
                    computeDataSet = computeDataSet,
                    volumeDataSet = volumeDataSet,
                    networkDataSet = networkDataSet
                )
            ) // uiState를 Success로 바꿔줌 -> 정보 Screen이 보여짐.
        }
    }

}
