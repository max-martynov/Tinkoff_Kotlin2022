import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue

class MyThreadPool(poolSize: Int = 1) : Executor {

    private var threads: List<MyThread>
    private val tasks = LinkedBlockingQueue<Runnable>()

    init {
        require(poolSize > 0)
        threads = List(poolSize) { MyThread(tasks).apply { this.start() } }
    }

    override fun execute(command: Runnable) {
        tasks.add(command)
    }

    fun shutdown() {
        threads.forEach {
            it.interrupt()
        }
    }
}