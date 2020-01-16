package dev.wintergreen.engine.util

import java.util.concurrent.TimeUnit

open class FrequencyTimer(frequency: Long, unit: TimeUnit) {

    private var start = currentTime
    private var frequency: Long = 0

    val isReady: Boolean
        get() = this.fullCount() > 0

    private val timeSinceStart: Long
        get() = currentTime - this.start

    init {
        this.setFrequency(frequency, unit)
    }

    fun setFrequency(frequency: Long, unit: TimeUnit) {
        this.frequency = UNIT.convert(frequency, unit)
    }

    fun getFrequency(unit: TimeUnit): Long {
        return unit.convert(frequency, UNIT)
    }

    fun reset() {
        this.start = currentTime
    }

    fun decrement() {
        this.start += this.frequency
    }

    fun decrementIfReady(): Boolean {
        if (this.isReady) {
            this.decrement()
            return true
        }
        return false
    }

    open fun count(): Double {
        return this.timeSinceStart.toDouble() / this.frequency.toDouble()
    }

    open fun fullCount(): Long {
        return this.timeSinceStart / this.frequency
    }

    fun countAndReset(): Double {
        val count = this.count()
        this.reset()
        return count
    }

    companion object {

        private val UNIT = TimeUnit.NANOSECONDS
        private val UNITS_PER_SECOND = UNIT.convert(1, TimeUnit.SECONDS)

        private val currentTime: Long
            get() = System.nanoTime()

        fun timesPer(times: Long, perUnit: TimeUnit): FrequencyTimer {
            val one = UNIT.convert(1, perUnit)
            return FrequencyTimer(
                one / times,
                UNIT
            )
        }

        fun always(): FrequencyTimer {
            return object : FrequencyTimer(0, TimeUnit.NANOSECONDS) {
                override fun count(): Double {
                    return java.lang.Double.MAX_VALUE
                }

                override fun fullCount(): Long {
                    return java.lang.Long.MAX_VALUE
                }
            }
        }
    }

}
