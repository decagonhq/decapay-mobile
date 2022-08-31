package com.decagonhq.decapay.common.utils.datavalidator

import android.os.Parcel
import android.os.Parcelable
import com.google.android.material.datepicker.CalendarConstraints
import java.util.*

class RangeValidator(
    private val startDate: Long,
    private val endDate: Long
) : CalendarConstraints.DateValidator {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        //
    }

    override fun isValid(date: Long): Boolean {
        return !(startDate > date || endDate < date)
    }

    companion object CREATOR : Parcelable.Creator<RangeValidator> {
        override fun createFromParcel(parcel: Parcel): RangeValidator {
            return RangeValidator(parcel)
        }

        override fun newArray(size: Int): Array<RangeValidator?> {
            return arrayOfNulls(size)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) {
            return true
        }
        if (!(other is RangeValidator)) {
            return false
        }
        return true
    }

    override fun hashCode(): Int {
        val hashedFields = arrayOf<Any>()
        return Arrays.hashCode(hashedFields)
    }
}
