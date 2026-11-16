package com.zubi.client.vehicle

import android.content.Context
import ai.pleos.playground.vehicle.Vehicle
import ai.pleos.playground.vehicle.constant.control.VehicleGear
import ai.pleos.playground.vehicle.listener.GearSelectionListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Pleos Vehicle SDK 진입점.
 * Application에서 한 번 초기화하고 앱 전체에서 공유합니다.
 */
object VehicleManager {

    private var vehicle: Vehicle? = null

    fun init(context: Context) {
        if (vehicle != null) return
        vehicle = Vehicle(context.applicationContext).also { it.initialize() }
    }

    fun release() {
        vehicle?.release()
        vehicle = null
    }

    private fun requireVehicle(): Vehicle =
        checkNotNull(vehicle) { "VehicleManager.init()이 먼저 호출되어야 합니다." }

    /**
     * 현재 기어 상태를 구독합니다. (권한: pleos.car.permission.CAR_DRIVE)
     * SDK 연동 확인용 예제입니다. Pleos Gear API는 deprecated 상태이므로
     * 실제 기능에서는 android.car의 GEAR_SELECTION 프로퍼티 사용을 권장합니다.
     */
    @Suppress("DEPRECATION")
    fun gearFlow(): Flow<Result<VehicleGear>> = callbackFlow {
        val gear = requireVehicle().getGear()

        gear.getCurrentGearSelectionState(
            { trySend(Result.success(it ?: VehicleGear.GEAR_UNKNOWN)) },
            { trySend(Result.failure(it)) },
        )

        val listener = object : GearSelectionListener {
            override fun onSelected(gear: VehicleGear?) {
                trySend(Result.success(gear ?: VehicleGear.GEAR_UNKNOWN))
            }

            override fun onFailed(e: Exception) {
                trySend(Result.failure(e))
            }
        }
        gear.registerGearSelection(listener)
        awaitClose { gear.unregisterGearSelection(listener) }
    }
}
