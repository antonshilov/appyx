package com.bumble.appyx.transitionmodel.spotlight.operation

import androidx.compose.animation.core.AnimationSpec
import com.bumble.appyx.interactions.Parcelize
import com.bumble.appyx.interactions.core.NavElements
import com.bumble.appyx.interactions.core.NavTransition
import com.bumble.appyx.interactions.core.Operation
import com.bumble.appyx.interactions.core.inputsource.AnimatedInputSource
import com.bumble.appyx.interactions.core.inputsource.InputSource
import com.bumble.appyx.transitionmodel.spotlight.Spotlight.State
import com.bumble.appyx.transitionmodel.spotlight.Spotlight.State.ACTIVE
import com.bumble.appyx.transitionmodel.spotlight.Spotlight.State.INACTIVE_AFTER
import com.bumble.appyx.transitionmodel.spotlight.Spotlight.State.INACTIVE_BEFORE


@Parcelize
class Last<NavTarget : Any> : Operation<NavTarget, State> {

    override fun isApplicable(elements: NavElements<NavTarget, State>) =
        elements.any { it.state == INACTIVE_AFTER }

    override fun invoke(elements: NavElements<NavTarget, State>): NavTransition<NavTarget, State> {
        return NavTransition(
            fromState = elements,
            targetState = elements.mapIndexed { index, element ->
                element.transitionTo(
                    newTargetState = when (index) {
                        elements.lastIndex -> ACTIVE
                        else -> INACTIVE_BEFORE
                    },
                    operation = this
                )
            }
        )
    }
}

fun <NavTarget : Any> InputSource<NavTarget, State>.last() {
    operation(Last())
}

fun <NavTarget : Any> AnimatedInputSource<NavTarget, State>.last(animationSpec: AnimationSpec<Float>) {
    operation(Last(), animationSpec)
}

