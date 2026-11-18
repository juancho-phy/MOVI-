package com.example.movi.Data.Model.Repository


import com.example.movi.Data.Model.DashboardAliadoUi

interface DashboardAliadoRepository {

    suspend fun obtenerDashboard(): DashboardAliadoUi

}