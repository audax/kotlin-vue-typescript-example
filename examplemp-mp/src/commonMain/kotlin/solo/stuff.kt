package examplemp

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@ExperimentalJsExport
@JsExport
fun doStuff(str: String, times: Int) = (0..times).joinToString(" ") { str }
