package com.zubi.client.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zubi.client.vehicle.VehicleManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel : ViewModel() {

    val gearText: StateFlow<String> = VehicleManager.gearFlow()
        .map { result ->
            result.fold(
                onSuccess = { it.name },
                onFailure = { "오류: ${it.message}" },
            )
        }
        .catch { emit("오류: ${it.message}") }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "확인 중…")
}
