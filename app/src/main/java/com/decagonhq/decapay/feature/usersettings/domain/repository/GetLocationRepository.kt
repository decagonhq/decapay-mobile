package com.decagonhq.decapay.feature.usersettings.domain.repository

import com.decagonhq.decapay.feature.usersettings.data.network.model.GetLocationResponseDemo

interface GetLocationRepository {
    suspend fun getLocationList(): GetLocationResponseDemo
}