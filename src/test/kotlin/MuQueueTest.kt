import collections.MyQueue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import kotlin.NoSuchElementException
import kotlin.test.assertEquals
import kotlin.test.assertNull


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MuQueueTest {

    private val emptyQueue = MyQueue<Any>()
    private val stringValues = listOf("a", "def", "baf")
    private val integerValues = List(100) { (0..10000).random() }

    @Nested
    inner class ElementTest {
        @Test
        fun `empty queue throws exception`() {
            val exception = assertThrows<NoSuchElementException> { emptyQueue.element() }
            assertEquals(exception.message, "Empty queue")
        }

        @Test
        fun `nonempty Int queue returns front element`() {
            val queue = MyQueue<Int>()
            queue.offer(1)
            assertEquals(1, queue.element())
            queue.offer(2)
            assertEquals(1, queue.element())
        }

        @Test
        fun `nonempty String queue returns front element`() {
            val queue = MyQueue<String>()
            queue.offer("bac")
            assertEquals("bac", queue.element())
            queue.offer("aaa")
            assertEquals("bac", queue.element())
        }
    }

    @Nested
    inner class RemoveTest {
        @Test
        fun `empty queue throws exception`() {
            val exception = assertThrows<NoSuchElementException> { emptyQueue.remove() }
            assertEquals(exception.message, "Empty queue")
        }

        @Test
        fun `String queue removes front element`() {
            val queue = MyQueue(stringValues)
            stringValues.forEach {
                assertEquals(it, queue.remove())
            }
        }

        @Test
        fun `Int queue removes front element`() {
            val queue = MyQueue(integerValues)
            integerValues.forEach {
                assertEquals(it, queue.remove())
            }
        }
    }

    @Nested
    inner class PeekTest {
        @Test
        fun `empty queue returns null`() {
            assertNull(emptyQueue.peek())
        }

        @Test
        fun `String queue returns front element and doesn't delete it`() {
            val queue = MyQueue<String>()
            queue.offer("a")
            assertEquals("a", queue.peek())
            queue.offer("aa")
            assertEquals("a", queue.peek())
        }

        @Test
        fun `Int queue returns front element and doesn't delete it`() {
            val queue = MyQueue<Int>()
            queue.offer(10)
            assertEquals(10, queue.peek())
            queue.offer(1)
            assertEquals(10, queue.peek())
        }
    }

    @Nested
    inner class PollTest {
        @Test
        fun `empty queue returns null`() {
            assertNull(emptyQueue.poll())
        }

        @Test
        fun `String queue removes front element`() {
            val queue = MyQueue(stringValues)
            stringValues.forEach {
                assertEquals(it, queue.poll())
            }
        }

        @Test
        fun `Int queue removes front element`() {
            val queue = MyQueue(integerValues)
            integerValues.forEach {
                assertEquals(it, queue.poll())
            }
        }
    }

    @Nested
    inner class MultipleOperationsTest {
        @Test
        fun `all operations on String queue`() {
            val queue = MyQueue<String>()
            queue.offer("a")
            assertEquals("a", queue.element())
            assertEquals("a", queue.peek())
            queue.offer("b")
            assertEquals("a", queue.remove())
            assertEquals("b", queue.poll())
            assertNull(queue.peek())
        }

        @Test
        fun `all operations on Integer queue`() {
            val queue = MyQueue(listOf(1, 2))
            assertEquals(1, queue.element())
            assertEquals(1, queue.remove())
            queue.offer(3)
            assertEquals(2, queue.peek())
            assertEquals(2, queue.poll())
            assertEquals(3, queue.remove())
            assertNull(queue.poll())
        }
    }

}