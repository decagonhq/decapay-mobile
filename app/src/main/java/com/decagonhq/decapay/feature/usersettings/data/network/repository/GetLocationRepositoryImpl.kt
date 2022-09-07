package com.decagonhq.decapay.feature.usersettings.data.network.repository

import com.decagonhq.decapay.feature.usersettings.data.network.api.GetLocationListApi
import com.decagonhq.decapay.feature.usersettings.data.network.model.GetLocationResponseDemo
import com.decagonhq.decapay.feature.usersettings.domain.repository.GetLocationRepository
import javax.inject.Inject

class GetLocationRepositoryImpl @Inject constructor(
    private val getLocationListApi: GetLocationListApi
) : GetLocationRepository{
    override suspend fun getLocationList(): GetLocationResponseDemo {
        return getLocationListApi.getLocationList()
    }
}