package com.decagonhq.decapay.feature.usersettings.domain.repository

import com.decagonhq.decapay.feature.usersettings.data.network.model.GetPreferredCurrencyResponseDemo

interface GetPreferredCurrencyRepository {

    suspend fun getPreferredCurrency(): GetPreferredCurrencyResponseDemo

}