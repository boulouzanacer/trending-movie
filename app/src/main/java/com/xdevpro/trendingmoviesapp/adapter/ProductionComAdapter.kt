package com.xdevpro.trendingmoviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xdevpro.trendingmoviesapp.databinding.AdapterMovieBinding
import com.xdevpro.trendingmoviesapp.databinding.ProductionCompanyLayoutBinding
import com.xdevpro.trendingmoviesapp.model.Production_companyModel

class ProductionComAdapter: RecyclerView.Adapter<ProductionViewHolder>() {

    var productionCom = mutableListOf<Production_companyModel>()
    var BASE_URL = "https://image.tmdb.org/t/p/" // Base url for image
    var ImageType = "w500"  // Image type ( W500 / Original )


    fun setProductionList(productionCom: List<Production_companyModel>) {
        this.productionCom = productionCom.toMutableList()
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return productionCom.size
    }

    override fun onBindViewHolder(holder: ProductionViewHolder, position: Int) {
        val productionCom = productionCom[position]
        holder.binding.productionComName.text = productionCom.name
        //holder.binding.p.text = productionCom.name
        Glide.with(holder.itemView.context).load(BASE_URL+ ImageType+productionCom.logo_path).into(holder.binding.productionComPoster) // Using Glide Library for fetching Network Image

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductionViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val binding = ProductionCompanyLayoutBinding.inflate(inflater, parent, false)
        return ProductionViewHolder(binding)
    }
}

class ProductionViewHolder(val binding: ProductionCompanyLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

}
