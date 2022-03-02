package com.bumble.appyx.v2.sandbox.client.mvicoreexample.feature

import com.bumble.appyx.v2.sandbox.client.mvicoreexample.MviCoreChildNode1.Output
import com.bumble.appyx.v2.sandbox.client.mvicoreexample.feature.MviCoreExampleFeature.Wish

internal object OutputChild1ToWish : (Output) -> Wish {
    override fun invoke(output: Output): Wish =
        when (output) {
            is Output.Result -> Wish.ChildInput(output.data)
        }
}
