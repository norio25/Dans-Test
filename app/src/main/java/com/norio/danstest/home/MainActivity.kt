package com.norio.danstest.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.norio.danstest.R
import com.norio.danstest.databinding.ActivityMainBinding
import com.norio.danstest.detail.DetailActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var location: String = ""
    private var description: String = ""
    private var fullTime: Boolean = false
    private var flagFilter: Boolean = false
    private lateinit var binding: ActivityMainBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setData()
        initSearch()
        initListener()
        viewModel.getListJob(location, description, fullTime)

        binding.includeFilter.switchFullTime.setOnCheckedChangeListener{ _, isChecked ->
            fullTime = isChecked
        }
    }

    private fun setData() {
        viewModel.dataThrowable.observe(this) {
            Log.d("listjob", it.toString())
        }
        viewModel.dataListJob.observe(this) {
            if (it.isNotEmpty()) {
                val data: ArrayList<ListJobResponseItem> = ArrayList()

                for (i in 0 until it.size) {
                    data.add(it[i])
                }

                val mAdapter = ListAdapter(data)
                binding.rvHome.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = mAdapter
                }

                mAdapter.setOnItemClickListener(object : ListAdapter.OnItemClickListener {
                    override fun onItemClicked(id: String) {
                        val intent = Intent(this@MainActivity, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.DATA_ID, id)
                        startActivity(intent)
                    }

                })
            }
        }
    }

    private fun initSearch() {
        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // this is your adapter that will be filtered
                if (newText.isEmpty()) {
                    location = ""
                }
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                //Here u can get the value "query" which is entered in the search box.
                location = query
                binding.searchJob.clearFocus()
                viewModel.getListJob(location, description, fullTime)
                return true
            }
        }

        val queryCloseListener = SearchView.OnCloseListener {
            location = ""
            true
        }

        binding.searchJob.setOnCloseListener(queryCloseListener)
        binding.searchJob.setOnQueryTextListener(queryTextListener)
    }

    private fun initListener() {
        binding.ivFilter.setOnClickListener(this)
        binding.includeFilter.btnApplyFilter.setOnClickListener(this)
    }

    private fun showHideFilter(flag: Boolean) {
        if (flag) {
            binding.includeFilter.filterLayout.visibility = View.VISIBLE
            binding.ivFilter.setImageResource(R.drawable.ic_arrow_up)
        }
        else {
            binding.includeFilter.filterLayout.visibility = View.GONE
            binding.ivFilter.setImageResource(R.drawable.ic_arrow_down)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.iv_filter -> {
                flagFilter = !flagFilter
                showHideFilter(flagFilter)
            }
            R.id.btn_apply_filter -> applyFilter()
        }
    }

    private fun applyFilter() {
        location = binding.includeFilter.etLocation.text.toString()
        viewModel.getListJob(location, description, fullTime)
    }

}