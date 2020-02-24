package com.sample.android.smallcount

sealed class VictoryUiModel {

  data class TitleUpdated(val title: String) : VictoryUiModel()

  data class CountUpdated(val count: Int) : VictoryUiModel()
}
