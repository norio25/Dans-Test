package com.norio.danstest.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.norio.danstest.R
import com.norio.danstest.databinding.ActivityDetailBinding
import com.norio.danstest.home.ListJobResponseItem


class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val DATA_ID = "data_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setData()
        getData()
    }

    private fun setData() {
        viewModel.dataDetail.observe(this) {
            initView(it)
        }
    }

    private fun initView(data: ListJobResponseItem?) {

        binding.toolbarDetail.title = "Job Detail"
        binding.toolbarDetail.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbarDetail.setNavigationOnClickListener { finish() }

        binding.includeCompanyDetail.tvOfficeDetail.text = data?.company
        binding.includeCompanyDetail.tvLocationDetail.text = data?.location

        Glide.with(this).load(data?.company_logo).into(binding.includeCompanyDetail.ivJobDetail)

        binding.includeJobDetail.tvTitleDetail.text = data?.title
        binding.includeJobDetail.tvFulltimeDetail.text = data?.type

        binding.includeJobDetail.rvDescriptionDetail.text =
            HtmlCompat.fromHtml(data?.description.toString(), HtmlCompat.FROM_HTML_MODE_COMPACT)

        binding.includeCompanyDetail.tvWebsiteDetail.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data?.company_url))
            startActivity(browserIntent)
        }
    }

    private fun getData() {
        val jobId = intent.getStringExtra(DATA_ID).toString()
        viewModel.getDetail(jobId)
    }
}