import java.lang.Thread.sleep

fun someLongOperation(): Long {
    var s: Long = 0
    for (i in 2..10_000_000) {
        var j = 2
        var prime = true
        while (j * j <= i) {
            if (i % j == 0) {
                prime = false
                break
            }
            j++
        }
        if (prime)
            s += i
    }
    return s
}

fun main() {
    val pool = MyThreadPool(3)
    repeat(5) {
        pool.execute {
            println(it * someLongOperation())
        }
    }
    sleep(1000L)
    pool.shutdown()
}