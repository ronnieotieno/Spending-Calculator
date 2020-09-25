

package dev.ronnie.spendingcalculator.data.testUtils

import androidx.lifecycle.LiveData
import dev.ronnie.spendingcalculator.utils.Event
import org.hamcrest.Matcher
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@Throws(InterruptedException::class)
fun <T> getValue(liveData: LiveData<T>): T {
    val data = arrayOfNulls<Any>(1)
    val latch = CountDownLatch(1)
    liveData.observeForever { o ->
        data[0] = o
        latch.countDown()
    }
    latch.await(2, TimeUnit.SECONDS)

    @Suppress("UNCHECKED_CAST")
    return data[0] as T
}

@Throws(InterruptedException::class)
fun <T> getValueForEvents(liveData: LiveData<Event<T>>): T {
    val data = arrayOfNulls<Any>(1)
    val latch = CountDownLatch(1)
    liveData.observeForever { o ->
        data[0] = o.peekContent()
        latch.countDown()
    }
    latch.await(2, TimeUnit.SECONDS)

    @Suppress("UNCHECKED_CAST")
    return data[0] as T
}
