package com.decagonhq.decapay.feature.createbudget.data.staticdata

object CalendarMonth {
    val calendarMonth: ArrayList<String>
        get() {
            val calendarMonthData = ArrayList<String>()
            calendarMonthData.add("January")
            calendarMonthData.add("February")
            calendarMonthData.add("March")
            calendarMonthData.add("April")
            calendarMonthData.add("May")
            calendarMonthData.add("June")
            calendarMonthData.add("July")
            calendarMonthData.add("August")
            calendarMonthData.add("September")
            calendarMonthData.add("October")
            calendarMonthData.add("November")
            calendarMonthData.add("December")

            return calendarMonthData
        }

    fun convertMonthStringValueToInt(chosenMonth: String): Int? {
      val month: Int? =  when(chosenMonth){
            "January" -> 1
          "February" -> 2
          "March" -> 3
          "April" -> 4
          "May" -> 5
          "June" -> 6
          "July" -> 7
          "August" -> 8
          "September" -> 9
          "October" -> 10
          "November" -> 11
          "December" -> 12
          else -> null
      }
        return month
    }
}
