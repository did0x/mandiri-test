package co.saputra.mandiritest.arch.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import co.saputra.mandiritest.R
import co.saputra.mandiritest.base.BaseListAdapter
import co.saputra.mandiritest.base.BaseViewHolder
import co.saputra.mandiritest.databinding.ItemMovieTopBinding
import com.bumptech.glide.Glide
import com.putrash.common.Constant
import com.putrash.common.convertDate
import com.putrash.data.BuildConfig
import com.putrash.data.model.Movie

class TopRatedMovieAdapter(
    layoutInflater: LayoutInflater,
    private val context: Context,
    private val onClickListener: (Movie) -> Unit
) : BaseListAdapter<Movie, ItemMovieTopBinding, TopRatedMovieAdapter.ViewHolder>(
    layoutInflater,
    ItemMovieTopBinding::inflate,
    MovieDiffcallback
) {

    override fun itemViewHolder(viewBinding: ItemMovieTopBinding, viewType: Int): ViewHolder {
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemMovieTopBinding) : BaseViewHolder<Movie>(binding.root) {
        override fun onBind(item: Movie) {
            binding.apply {
                root.setOnClickListener {
                    onClickListener(item)
                }
                tvTitle.text = item.title
                tvSubtitle.text = context.getString(
                    R.string.label_subtitle,
                    item.releaseDate?.convertDate(Constant.DATE_YYYY_MM_DD_FORMAT, Constant.DATE_YYYY_FORMAT),
                    item.genres?.map { it.name }?.joinToString(", ")
                )
                Glide.with(context)
                    .load(BuildConfig.IMAGE_URL + item.backdropPath)
                    .into(ivPoster)
            }
        }
    }

    object MovieDiffcallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.title == newItem.title
        }
    }
}