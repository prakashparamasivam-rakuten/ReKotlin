@file:Suppress("UNCHECKED_CAST", "unused", "NOTHING_TO_INLINE")

package org.rekotlin

/**
 * Convenience factory function for [compositeStore]
 *
 * @see [compositeStore]
 */
inline fun <S1, State> composeStores(
    store1: Store<S1>,
    vararg middleware: Middleware<State>,
    crossinline compose: (S1) -> State = { it as State }
): Store<State> = compositeStore(store1, middleware = middleware.toList()) {
    compose(it.s1())
}

/**
 * Convenience factory function for [compositeStore]
 *
 * @see [compositeStore]
 */
inline fun <S1, S2, State> composeStores(
    store1: Store<S1>,
    store2: Store<S2>,
    vararg middleware: Middleware<State>,
    crossinline compose: (S1, S2) -> State
): Store<State> = compositeStore(store1, store2, middleware = middleware.toList()) {
    compose(it.s1(), it.s2())
}

/**
 * Convenience factory function for [compositeStore]
 *
 * @see [compositeStore]
 */
inline fun <S1, S2, S3, State> composeStores(
    store1: Store<S1>,
    store2: Store<S2>,
    store3: Store<S3>,
    vararg middleware: Middleware<State>,
    crossinline compose: (S1, S2, S3) -> State
): Store<State> = compositeStore(store1, store2, store3, middleware = middleware.toList()) {
    compose(it.s1(), it.s2(), it.s3())
}

/**
 * Convenience factory function for [compositeStore]
 *
 * @see [compositeStore]
 */
inline fun <S1, S2, S3, S4, State> composeStores(
    store1: Store<S1>,
    store2: Store<S2>,
    store3: Store<S3>,
    store4: Store<S4>,
    vararg middleware: Middleware<State>,
    crossinline compose: (S1, S2, S3, S4) -> State
): Store<State> = compositeStore(store1, store2, store3, store4, middleware = middleware.toList()) {
    compose(it.s1(), it.s2(), it.s3(), it.s4())
}

/**
 * Convenience factory function for [compositeStore]
 *
 * @see [compositeStore]
 */
inline fun <S1, S2, S3, S4, S5, State> composeStores(
    store1: Store<S1>,
    store2: Store<S2>,
    store3: Store<S3>,
    store4: Store<S4>,
    store5: Store<S5>,
    vararg middleware: Middleware<State>,
    crossinline compose: (S1, S2, S3, S4, S5) -> State
): Store<State> = compositeStore(store1, store2, store3, store4, store5, middleware = middleware.toList()) {
    compose(it.s1(), it.s2(), it.s3(), it.s4(), it.s5())
}

/**
 * Convenience factory function for [compositeStore]
 *
 * @see [compositeStore]
 */
inline fun <S1, S2, S3, S4, S5, S6, State> composeStores(
    store1: Store<S1>,
    store2: Store<S2>,
    store3: Store<S3>,
    store4: Store<S4>,
    store5: Store<S5>,
    store6: Store<S6>,
    vararg middleware: Middleware<State>,
    crossinline compose: (S1, S2, S3, S4, S5, S6) -> State
): Store<State> = compositeStore(store1, store2, store3, store4, store5, store6, middleware = middleware.toList()) {
    compose(it.s1(), it.s2(), it.s3(), it.s4(), it.s5(), it.s6())
}

/**
 * Convenience factory function for [compositeStore]
 *
 * @see [compositeStore]
 */
inline fun <S1, S2, S3, S4, S5, S6, S7, State> composeStores(
    store1: Store<S1>,
    store2: Store<S2>,
    store3: Store<S3>,
    store4: Store<S4>,
    store5: Store<S5>,
    store6: Store<S6>,
    store7: Store<S7>,
    vararg middleware: Middleware<State>,
    crossinline compose: (S1, S2, S3, S4, S5, S6, S7) -> State
): Store<State> = compositeStore(store1, store2, store3, store4, store5, store6, store7, middleware = middleware.toList()) {
    compose(it.s1(), it.s2(), it.s3(), it.s4(), it.s5(), it.s6(), it.s7())
}

/**
 * Convenience factory function for [compositeStore]
 *
 * @see [compositeStore]
 */
inline fun <S1, S2, S3, S4, S5, S6, S7, S8, State> composeStores(
    store1: Store<S1>,
    store2: Store<S2>,
    store3: Store<S3>,
    store4: Store<S4>,
    store5: Store<S5>,
    store6: Store<S6>,
    store7: Store<S7>,
    store8: Store<S8>,
    vararg middleware: Middleware<State>,
    crossinline compose: (S1, S2, S3, S4, S5, S6, S7, S8) -> State
): Store<State> = compositeStore(store1, store2, store3, store4, store5, store6, store7, store8, middleware = middleware.toList()) {
    compose(it.s1(), it.s2(), it.s3(), it.s4(), it.s5(), it.s6(), it.s7(), it.s8())
}

/**
 * Convenience factory function for [compositeStore]
 *
 * @see [compositeStore]
 */
inline fun <S1, S2, S3, S4, S5, S6, S7, S8, S9, State> composeStores(
    store1: Store<S1>,
    store2: Store<S2>,
    store3: Store<S3>,
    store4: Store<S4>,
    store5: Store<S5>,
    store6: Store<S6>,
    store7: Store<S7>,
    store8: Store<S8>,
    store9: Store<S9>,
    vararg middleware: Middleware<State>,
    crossinline compose: (S1, S2, S3, S4, S5, S6, S7, S8, S9) -> State
): Store<State> = compositeStore(store1, store2, store3, store4, store5, store6, store7, store8, store9, middleware = middleware.toList()) {
    compose(it.s1(), it.s2(), it.s3(), it.s4(), it.s5(), it.s6(), it.s7(), it.s8(), it.s9())
}

@PublishedApi internal inline fun <S> Array<out Store<*>>.s1() = this[0].state as S
@PublishedApi internal inline fun <S> Array<out Store<*>>.s2() = this[1].state as S
@PublishedApi internal inline fun <S> Array<out Store<*>>.s3() = this[2].state as S
@PublishedApi internal inline fun <S> Array<out Store<*>>.s4() = this[3].state as S
@PublishedApi internal inline fun <S> Array<out Store<*>>.s5() = this[4].state as S
@PublishedApi internal inline fun <S> Array<out Store<*>>.s6() = this[5].state as S
@PublishedApi internal inline fun <S> Array<out Store<*>>.s7() = this[6].state as S
@PublishedApi internal inline fun <S> Array<out Store<*>>.s8() = this[7].state as S
@PublishedApi internal inline fun <S> Array<out Store<*>>.s9() = this[8].state as S
