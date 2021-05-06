/**
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.rekotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class SubscriberTests {

    @Test
    fun `should allow to select substate`() {
        val store = ParentStore(::intReducer, IntState())
        val subscriber = FakeSubscriber<Int?>()
        store.subscribe(subscriber) { select { number } }

        store.dispatch(IntAction(3))

        assertEquals(3, subscriber.lastState)
    }

    @Test
    fun `should allow to select substate, even if it's null`() {
        val store = ParentStore(::intReducer, IntState())
        val subscriber = FakeSubscriber<Int?>()
        store.subscribe(subscriber) { select { number } }

        store.dispatch(IntAction(null))

        assertEquals(null, subscriber.lastState)
    }

    data class ComplexAppState(
        val number: Int? = null,
        val other: OtherState? = null
    )

    data class OtherState(val name: String?, val age: Int?)

    private fun complexAppStateReducer(action: Action, state: ComplexAppState?): ComplexAppState {
        val oldState = state ?: ComplexAppState()

        return when (action) {
            is IntAction -> oldState.copy(number = action.value)
            is SetOtherStateAction -> oldState.copy(other = action.otherState)
            else -> oldState
        }
    }

    data class SetOtherStateAction(val otherState: OtherState) : Action

    /**
     * it supports complex state selector closures
     */
    @Test
    fun `should allow to select sub state from complex app state`() {
        val store = store(::complexAppStateReducer, ComplexAppState())
        val subscriber = FakeSubscriber<Pair<Int?, String?>>()

        store.subscribe(subscriber) {
            select {
                Pair(number, other?.name)
            }
        }

        store.dispatch(IntAction(5))
        store.dispatch(SetOtherStateAction(OtherState("TestName", 99)))

        assertNotNull(subscriber.lastState)
        assertEquals(5, subscriber.lastState?.first)
        assertEquals("TestName", subscriber.lastState?.second)
    }

    @Test
    fun `should not notify subscriber for unchanged substate when using skipRepeats "manually"`() {
        val store = ParentStore(::intReducer, IntState(3))
        val subscriber = FakeSubscriber<Int?>()

        store.subscribe(subscriber) {
            select { number }.skip { old, new -> old == new }
        }

        store.dispatch(IntAction(3))
        store.dispatch(IntAction(3))

        assertEquals(1, subscriber.callCount)
    }

    @Test
    fun `should not notify subscriber for unchanged sub-state by default`() {
        val store = ParentStore(::intReducer, IntState(3), skipRepeats = true)
        val subscriber = FakeSubscriber<Int?>()

        store.subscribe(subscriber) { select { number } }

        store.dispatch(IntAction(3))
        store.dispatch(IntAction(3))

        assertEquals(1, subscriber.callCount)
    }

    @Test
    fun `should skips repeated state updates for equatable state by default`() {
        val store = ParentStore(::stringReducer, StringState())
        val subscriber = FakeSubscriber<StringState>()

        store.subscribe(subscriber)

        store.dispatch(StringAction("Initial"))
        store.dispatch(StringAction("Initial"))

        assertEquals(1, subscriber.callCount)
    }

    @Test
    fun `should skips repeated state updates for equatable selected sub-state by default`() {
        val store = ParentStore(::intReducer, IntState(3))
        val subscriber = FakeSubscriber<Int?>()

        store.subscribe(subscriber) { select { number } }

        store.dispatch(IntAction(3))
        store.dispatch(IntAction(3))

        assertEquals(1, subscriber.callCount)
    }

    @Test
    fun testPassesOnDuplicateStateUpdatesInCustomizedStore() {
        val store = ParentStore(::stringReducer, StringState(), skipRepeats = false)
        val subscriber = FakeSubscriber<StringState>()

        store.subscribe(subscriber)

        assertEquals("Initial", subscriber.lastState?.name)

        store.dispatch(StringAction("Initial"))

        assertEquals("Initial", subscriber.lastState?.name)
        assertEquals(2, subscriber.callCount)
    }

    @Test
    fun testSkipWhen() {
        val state = TestCustomAppState(5)
        val store = ParentStore(::customAppStateReducer, state)
        val subscriber = FakeSubscriber<SubState>()

        store.subscribe(subscriber) {
            select { subState }
                .skip { oldState, newState -> oldState.value == newState.value }
        }

        assertEquals(5, subscriber.lastState?.value)

        store.dispatch(CustomSubStateAction(5))

        assertEquals(5, subscriber.lastState?.value)
        assertEquals(1, subscriber.callCount)
    }

    @Test
    fun testOnlyWhen() {
        val state = TestCustomAppState(5)
        val store = ParentStore(::customAppStateReducer, state)
        val subscriber = FakeSubscriber<SubState>()

        store.subscribe(subscriber) {
            select { subState }
                .only { oldState, newState -> oldState.value != newState.value }
        }

        assertEquals(5, subscriber.lastState?.value)

        store.dispatch(CustomSubStateAction(5))

        assertEquals(5, subscriber.lastState?.value)
        assertEquals(1, subscriber.callCount)
    }
}
