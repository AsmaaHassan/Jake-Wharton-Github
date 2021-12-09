package com.example.jakewhartongithub.models

import com.google.gson.annotations.SerializedName
/**
 * Created by Asmaa Hassan
 */
data class ResponseRepoPaginated(

	@field:SerializedName("total_count")
	val totalCount: Int? = null,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean? = null,

	@field:SerializedName("items")
	val items: List<Repo>
)