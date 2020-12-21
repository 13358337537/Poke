package com.cmk.poke.repository

import androidx.annotation.WorkerThread
import com.cmk.poke.mapper.ErrorResponseMapper
import com.cmk.poke.model.PokemonInfo
import com.cmk.poke.network.PokeClient
import com.cmk.poke.persistence.PokemonInfoDao
import com.skydoves.sandwich.map
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import com.skydoves.whatif.whatIfNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val pokeClient: PokeClient,
    private val pokemonInfoDao: PokemonInfoDao
): Repository {

    @WorkerThread
    suspend fun fetchPokemonInfo(
        name: String,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit
    ) = flow<PokemonInfo?> {
        val pokemonInfo = pokemonInfoDao.getPokemonInfo(name)
        if (pokemonInfo != null) {
            /**
             * fetches a [PokemonInfo] from the network and getting [ApiResponse] asynchronously.
             * @see [suspendOnSuccess](https://github.com/skydoves/sandwich#suspendonsuccess-suspendonerror-suspendonexception)
             */
            val response = pokeClient.fetchPokemonInfo(name = name)
            response.suspendOnSuccess {
                data.whatIfNotNull { response ->
                    pokemonInfoDao.insertPokemonInfo(response)
                    emit(response)
                    onSuccess()
                }
            }
                // handles the case when the API request gets an error response.
                // e.g., internal server error.
                .onError {
                    /** maps the [ApiResponse.Failure.Error] to the [PokemonErrorResponse] using the mapper. */
                    map(ErrorResponseMapper){
                        onError("[Code: $code]: $message")
                    }
                }
                // handles the case when the API request gets an exception response.
                // e.g., network connection error.
                .onException { onError(message) }
        } else {
            emit(pokemonInfo)
            onSuccess()
        }
    }.flowOn(Dispatchers.IO)
}