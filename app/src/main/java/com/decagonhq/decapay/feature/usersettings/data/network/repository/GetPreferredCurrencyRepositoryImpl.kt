package com.decagonhq.decapay.feature.usersettings.data.network.repository

import com.decagonhq.decapay.feature.usersettings.data.network.api.GetPreferredCurrencyApi
import com.decagonhq.decapay.feature.usersettings.data.network.model.GetPreferredCurrencyResponseDemo
import com.decagonhq.decapay.feature.usersettings.domain.repository.GetPreferredCurrencyRepository
import javax.inject.Inject

class GetPreferredCurrencyRepositoryImpl @Inject constructor(
    private val getPreferredCurrencyApi: GetPreferredCurrencyApi
) : GetPreferredCurrencyRepository{
    override suspend fun getPreferredCurrency(): GetPreferredCurrencyResponseDemo {
        return getPreferredCurrencyApi.getPreferredCurrency()
    }
}