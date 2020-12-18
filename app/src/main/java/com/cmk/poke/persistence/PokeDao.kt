package com.cmk.poke.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cmk.poke.model.Pokemon

/**
 *
 * 查询本地数据
 */
@Dao
interface PokeDao {
    /*
       1. OnConflictStrategy.REPLACE：冲突策略是取代旧数据同时继续事务。

       2. OnConflictStrategy.ROLLBACK：冲突策略是回滚事务。

       3. OnConflictStrategy.ABORT：冲突策略是终止事务。

       4. OnConflictStrategy.FAIL：冲突策略是事务失败。

       5. OnConflictStrategy.IGNORE：冲突策略是忽略冲突。
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonList(pokemonList : List<Pokemon>)

    @Query("SELECT * FROM Pokemon WHERE page = :page_")
    suspend fun getPokemonList(page_: Int): List<Pokemon>

    @Query("SELECT * FROM Pokemon WHERE page <= :page_")
    suspend fun getAllPokemonList(page_: Int): List<Pokemon>
}