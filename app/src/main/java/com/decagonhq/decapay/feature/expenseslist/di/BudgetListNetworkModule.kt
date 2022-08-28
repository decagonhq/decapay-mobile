package com.decagonhq.decapay.feature.expenseslist.di

import com.decagonhq.decapay.feature.expenseslist.data.network.api.ExpenseListApi
import com.decagonhq.decapay.feature.expenseslist.data.repository.ExpenseListRepositoryImpl
import com.decagonhq.decapay.feature.expenseslist.domain.repository.ExpenseListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BudgetListNetworkModule {

    @Provides
    @Singleton
    fun provideExpenseListApi(retrofit: Retrofit):ExpenseListApi{
        return retrofit.create(ExpenseListApi::class.java)
    }

    @Provides
    @Singleton
    fun providesExpenseListRepository(expenseListApi: ExpenseListApi):ExpenseListRepository{
        return  ExpenseListRepositoryImpl(expenseListApi)
    }

}