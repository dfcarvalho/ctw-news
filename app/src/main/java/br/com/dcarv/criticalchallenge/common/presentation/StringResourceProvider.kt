package br.com.dcarv.criticalchallenge.common.presentation

import android.content.Context
import javax.inject.Inject

class StringResourceProvider @Inject constructor(private val context: Context) {

        fun get(resId: Int): String = context.getString(resId)
}
