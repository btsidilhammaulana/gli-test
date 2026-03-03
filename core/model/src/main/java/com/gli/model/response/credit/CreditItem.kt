package com.gli.model.response.credit

sealed class CreditItem {
  data class DataItem(val data: CreditModel) : CreditItem()
  data class MoreItem(val remainingCount: Int) : CreditItem()
}