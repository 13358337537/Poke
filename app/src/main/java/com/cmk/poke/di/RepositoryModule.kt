/*
 * Designed and developed by 2020 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cmk.poke.di

import com.cmk.poke.network.PokeClient
import com.cmk.poke.persistence.PokeDao
import com.cmk.poke.persistence.PokemonInfoDao
import com.cmk.poke.repository.DetailRepository
import com.cmk.poke.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

  @Provides
  @ActivityRetainedScoped
  fun provideMainRepository(
    pokeClient: PokeClient,
    pokemonDao: PokeDao
  ): MainRepository {
    return MainRepository(pokeClient, pokemonDao)
  }

  @Provides
  @ActivityRetainedScoped
  fun provideDetailRepository(
    pokeClient: PokeClient,
    pokemonInfoDao: PokemonInfoDao
  ): DetailRepository {
    return DetailRepository(pokeClient, pokemonInfoDao)
  }
}
