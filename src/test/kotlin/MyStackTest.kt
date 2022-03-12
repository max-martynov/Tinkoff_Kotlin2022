import collections.MyStack
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
import kotlin.test.assertNull


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MyStackTest {

    private val emptyStack = MyStack<Any>()
    private val stringValues = listOf("bcd", "a", "efg")
    private val integerValues = List(100) { (0..10000).random() }

    @Nested
    inner class PopTest {
        @Test
        fun `pop on empty stack returns null`() {
            assertNull(emptyStack.pop())
        }

        @Test
        fun `pop all elements from String stack`() {
            val stack = MyStack(stringValues)
            stringValues.reversed().forEach { assertEquals(it, stack.pop()) }
        }

        @Test
        fun `pop all elements from Int stack`() {
            val stack = MyStack(integerValues)
            integerValues.reversed().forEach { assertEquals(it, stack.pop()) }
        }
    }

    @Nested
    inner class PeekTest {
        @Test
        fun `peek on empty stack returns null`() {
            assertNull(emptyStack.peek())
        }

        @Test
        fun `peek on String stack`() {
            val stack = MyStack<String>()
            stringValues.forEach {
                stack.push(it)
                assertEquals(it, stack.peek())
            }
        }

        @Test
        fun `peek on Int stack`() {
            val stack = MyStack<Int>()
            integerValues.forEach {
                stack.push(it)
                assertEquals(it, stack.peek())
            }
        }
    }

    @Nested
    inner class MultipleOperationsTest {
        @Test
        fun `all operations on String stack`() {
            val stack = MyStack<String>()
            stack.push("a")
            assertEquals("a", stack.peek())
            assertEquals("a", stack.pop())
            stack.push("b")
            stack.push("c")
            assertEquals("c", stack.pop())
            assertEquals("b", stack.peek())
        }

        @Test
        fun `all operations on Int stack`() {
            val stack = MyStack<Int>()
            stack.push(1)
            stack.push(2)
            assertEquals(2, stack.pop())
            stack.push(3)
            assertEquals(3, stack.peek())
            assertEquals(3, stack.pop())
        }
    }
}