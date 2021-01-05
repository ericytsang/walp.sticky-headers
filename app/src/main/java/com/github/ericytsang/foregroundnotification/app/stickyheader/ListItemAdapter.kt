package com.github.ericytsang.foregroundnotification.app.stickyheader;

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.ericytsang.foregroundnotification.app.stickyheader.databinding.ListItemTextBinding

class ListItemAdapter<Key>:ListAdapter<GenericItemModel<Key,VhModel>,ViewHolders<out VhModel>>(GenericItemModel.Differ())
{
    enum class ViewType
    {
        StickyHeader,
        ListItem,
        ;
        fun createViewHolder(parent:ViewGroup):ViewHolders<out VhModel> = when (this)
        {
            StickyHeader -> ViewHolders.StickyHeader(ListItemTextBinding.inflate(parent.context.layoutInflater,parent,false))
            ListItem -> ViewHolders.ListItem(ListItemTextBinding.inflate(parent.context.layoutInflater,parent,false))
        }
        fun bind(viewHolder:ViewHolders<out VhModel>, viewHolderModel:GenericItemModel<*,VhModel>)
        {
            when (this)
            {
                StickyHeader -> (viewHolder as ViewHolders.StickyHeader).bind(viewHolderModel.customData as VhModel.StickyHeader)
                ListItem -> (viewHolder as ViewHolders.ListItem).bind(viewHolderModel.customData as VhModel.ListItem)
            }
        }
    }

    fun getItemViewTypeEnum(position:Int):ViewType = when (getItem(position).customData)
    {
        is VhModel.StickyHeader -> ViewType.StickyHeader
        is VhModel.ListItem -> ViewType.ListItem
    }

    override fun getItemViewType(position:Int):Int = getItemViewTypeEnum(position).ordinal

    override fun onCreateViewHolder(parent:ViewGroup,viewType:Int):ViewHolders<out VhModel>
    {
        return ViewType.values()[viewType].createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolders<out VhModel>, position: Int) {
        getItemViewTypeEnum(position).bind(holder,getItem(position))
    }
}

/**
 * an item returned by [ListAdapter.getItem] in the [ListAdapter].
 */
data class GenericItemModel<Key,CustomData>(

    /**
     * an identifier for this [GenericItemModel] that should remain stable despite changes to this
     * [GenericItemModel]'s internal state.
     *
     * [key]'s [Any.equals] and [Any.hashCode] will be used to test whether this [key] matches
     * another, so [Any.equals] and [Any.hashCode] should be implemented properly.
     */
    val key:Key,

    /**
     * whether the state of this [GenericItemModel].
     */
    val customData:CustomData,
)
{
    /**
     * pass into constructor for [ListAdapter].
     */
    class Differ<Key,CustomData>:DiffUtil.ItemCallback<GenericItemModel<Key,CustomData>>()
    {
        override fun areItemsTheSame(
            oldItem:GenericItemModel<Key,CustomData>,newItem:GenericItemModel<Key,CustomData>
        ):Boolean = oldItem.key == newItem.key

        override fun areContentsTheSame(
            oldItem:GenericItemModel<Key,CustomData>,newItem:GenericItemModel<Key,CustomData>
        ):Boolean = oldItem == newItem
    }
}

sealed class VhModel
{
    data class StickyHeader(val model:ViewHolders.StickyHeader.Model):VhModel()
    data class ListItem(val model:ViewHolders.ListItem.Model):VhModel()
}

sealed class ViewHolders<Model>(view:View):RecyclerView.ViewHolder(view)
{
    abstract fun bind(model:Model)

    data class StickyHeader(
        val binding:ListItemTextBinding
    ):ViewHolders<VhModel.StickyHeader>(binding.root)
    {
        override fun bind(model:VhModel.StickyHeader)
        {
            binding.text.text = model.model.string
        }

        data class Model(val string:String)
    }

    data class ListItem(
        val binding:ListItemTextBinding
    ):ViewHolders<VhModel.ListItem>(binding.root)
    {
        override fun bind(model:VhModel.ListItem)
        {
            binding.text.text = model.model.string
        }

        data class Model(val string:String)
    }
}