package com.cmk.poke.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cmk.poke.model.Pokemon
import com.cmk.poke.model.PokemonInfo

@Database(entities = [Pokemon::class, PokemonInfo::class],version = 1,exportSchema = true)
@TypeConverters(value = [TypeResponseConverter::class])
abstract class AppDatabase : RoomDatabase(){

    abstract fun pokemonDao(): PokeDao
    abstract fun pokemonInfoDao(): PokemonInfoDao

}