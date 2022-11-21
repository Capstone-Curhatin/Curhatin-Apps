package com.capstone.curhatin.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.capstone.core.R
import com.capstone.core.data.common.Resource
import com.capstone.core.databinding.DialogCategoryBinding
import com.capstone.core.utils.setDialogError
import com.capstone.core.utils.setLoading
import com.capstone.core.utils.stopLoading
import com.capstone.curhatin.viewmodel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogCategory(
    private val callback: DialogCategoryCallback
) : DialogFragment() {

    private var _binding: DialogCategoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CategoryViewModel by viewModels()

    private lateinit var dialogCategoryCallback: DialogCategoryCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCategoryBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_rectangle_white)
        val width = (resources.displayMetrics.widthPixels * 0.95).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.70).toInt()
        dialog?.window?.setLayout(width, height)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCategory().observe(viewLifecycleOwner){ res ->
            when(res){
                is Resource.Loading -> {setLoading()}
                is Resource.Error -> {
                    stopLoading()
                    setDialogError(res.message.toString())
                }
                is Resource.Success -> {
                    stopLoading()

                    val name = ArrayList<String>()
                    val id = ArrayList<Int>()

                    res.data?.data?.forEach {
                        id.add(it.id)
                        name.add(it.name)
                    }

                    // set and send id of category
                    val nameAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, name)
                    val idAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, id)
                    binding.listView.apply {
                        adapter = nameAdapter
                        setOnItemClickListener { adapterView, view, i, l ->
                            dialogCategoryCallback.onClickCategory(this@DialogCategory, idAdapter.getItem(i)!!, nameAdapter.getItem(i)!!)
                        }
                    }
                }
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dialogCategoryCallback = callback
        }catch (e: Exception){
            throw ClassCastException(context.toString() + "must be implement DialogCategoryCallback")
        }
    }

    interface DialogCategoryCallback {
        fun onClickCategory(dialog: DialogFragment, id: Int, categoryName: String)
    }

}