package dev.wintergreen.engine.core

import dev.wintergreen.engine.util.FrequencyTimer

abstract class EngineLoop(
    val id: String,
    /**
     * How much time in seconds between each execution.
     */
    val frequency: FrequencyTimer
) {

    /**
     * Time between last loop execution and this loop execution.
     */
    var deltaTime: Float = 0f
        internal set

    /**
     * Total lifetime of loop in seconds.
     */
    var time: Float = 0f

    /**
     * Rate.
     */
    var rate: Float = 0f
        internal set

    internal var count: Float = 0f

    /**
     * Last time this loop was called.
     */
    internal var lastRunTimestamp: Long = 0L

//    var frequency = frequency
//        set(value) {
//            field = value
//            timer.setFrequency(frequencyInMillis, TimeUnit.MILLISECONDS)
//        }
//
//    internal val frequencyInMillis
//        get() = (frequency * 1000).toLong()
//
//    internal val timer = FrequencyTimer(
//        (frequency * 1000).toLong(),
//        TimeUnit.MILLISECONDS
//    )

    open fun onStart() {}

    abstract fun onLoop()

    open fun onStop() {}
}