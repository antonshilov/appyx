package com.bumble.appyx.core.navigation2

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.bumble.appyx.core.navigation.NavKey
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@Immutable
class NavElement<NavTarget, State> private constructor(
    val key: @RawValue NavKey<NavTarget>,
    val fromState: @RawValue State,
    val state: @RawValue State,
    val operation: @RawValue Operation<NavTarget, State>,
    val transitionHistory: List<Pair<State, State>>
) : Parcelable {
    constructor(
        key: @RawValue NavKey<NavTarget>,
        fromState: @RawValue State,
        targetState: @RawValue State,
        operation: @RawValue Operation<NavTarget, State>,
    ) : this(
        key,
        fromState,
        targetState,
        operation,
        if (fromState == targetState) emptyList() else listOf(fromState to targetState)
    )

    fun transitionTo(
        newTargetState: @RawValue State,
        operation: @RawValue Operation<NavTarget, State>
    ): NavElement<NavTarget, State> =
        NavElement(
            key = key,
            fromState = fromState,
            state = newTargetState,
            operation = operation,
            transitionHistory =
            if (fromState != newTargetState) {
                transitionHistory + listOf(fromState to newTargetState)
            } else transitionHistory
        )

    fun onTransitionFinished(): NavElement<NavTarget, State> =
        NavElement(
            key = key,
            fromState = state,
            state = state,
            operation = operation,
            transitionHistory = emptyList()
        )


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NavElement<*, *>

        if (key != other.key) return false
        if (fromState != other.fromState) return false
        if (state != other.state) return false
        if (operation != other.operation) return false
        if (transitionHistory != other.transitionHistory) return false

        return true
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + (fromState?.hashCode() ?: 0)
        result = 31 * result + (state?.hashCode() ?: 0)
        result = 31 * result + operation.hashCode()
        result = 31 * result + transitionHistory.hashCode()
        return result
    }

    override fun toString(): String {
        return "NavElement(key=$key, fromState=$fromState, targetState=$state, operation=$operation, transitionHistory=$transitionHistory)"
    }
}
