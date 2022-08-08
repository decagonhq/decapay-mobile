package com.decagonhq.decapay.feature.createbudget.data.staticdata

object BudgetPeriods {
    val budgetPeriod: ArrayList<String>
        get() {
            val budgetPeriodData = ArrayList<String>()
            budgetPeriodData.add(0, "Please select a period")
            budgetPeriodData.add("Annual")
            budgetPeriodData.add("Monthly")
            budgetPeriodData.add("Weekly")
            budgetPeriodData.add("Daily")
            budgetPeriodData.add("Custom")

            return budgetPeriodData
        }
}
