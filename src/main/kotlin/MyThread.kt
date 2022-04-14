import java.util.concurrent.LinkedBlockingQueue

class MyThread(private val tasks: LinkedBlockingQueue<Runnable>) : Thread() {

    override fun run() {
        try {
            while (!isInterrupted) {
                val task = tasks.take()
                task.run()
            }
        } catch (e: InterruptedException) {
            interrupt()
        }
    }
}