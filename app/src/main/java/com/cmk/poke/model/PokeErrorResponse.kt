package com.cmk.poke.model

/**
 * A customized pokemon error response.
 *
 * @param code A network response code.
 * @param message A network error message.
 */
data class PokeErrorResponse(
        val code: Int,
        val message: String?
)
