package com.gli.test.screen.detail.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.gli.model.response.credit.CreditModel
import com.gli.test.databinding.BottomSheetCreditBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CastBottomSheetDialogFragment : BottomSheetDialogFragment() {

  private lateinit var binding: BottomSheetCreditBinding
  private val adapter: MoreCreditAdapter by lazy {
    MoreCreditAdapter()
  }

  override fun onStart() {
    super.onStart()
    setupSystemBar()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = BottomSheetCreditBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val castList = arguments?.getParcelableArrayList<CreditModel>(ARG_CAST) ?: emptyList()

    binding.rvCredit.layoutManager = GridLayoutManager(context, 4)
    binding.rvCredit.adapter = adapter
    adapter.setItems(castList)
  }

  private fun setupSystemBar() {
    dialog?.window?.let { window ->
      WindowCompat.setDecorFitsSystemWindows(window, false)
    }
  }

  companion object {
    private const val ARG_CAST = "ARG_CAST"

    fun newInstance(list: ArrayList<CreditModel>): CastBottomSheetDialogFragment {
      val fragment = CastBottomSheetDialogFragment()
      val bundle = Bundle()
      bundle.putParcelableArrayList(ARG_CAST, list)
      fragment.arguments = bundle
      return fragment
    }
  }
}