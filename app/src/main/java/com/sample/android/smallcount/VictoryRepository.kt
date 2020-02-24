package com.sample.android.smallcount

interface VictoryRepository {
  fun getVictoryTitleAndCount(): Pair<String, Int>
  fun setVictoryTitle(title: String)
  fun getVictoryTitle(): String
  fun setVictoryCount(count: Int)
  fun getVictoryCount(): Int
  fun clear()
}