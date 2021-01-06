package com.github.ericytsang.foregroundnotification.app.stickyheader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.ericytsang.foregroundnotification.app.stickyheader.databinding.ActivityMainBinding

class MainActivity:AppCompatActivity()
{
    override fun onCreate(savedInstanceState:Bundle?)
    {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ListItemAdapter<Int>()
        binding.recyclerView.layoutManager = StickyHeadersLinearLayoutManager<ListItemAdapter<Int>>(this)
        binding.recyclerView.adapter = adapter.apply()
        {
            submitList(
                listOf(
                    GenericItemModel(0,VhModel.ListItem(ViewHolders.ListItem.Model("ListItem\n1"))),
                    GenericItemModel(1,VhModel.ListItem(ViewHolders.ListItem.Model("ListItem\n\n\n\n2"))),
                    GenericItemModel(2,VhModel.ListItem(ViewHolders.ListItem.Model("ListItem\n3"))),
                    GenericItemModel(3,VhModel.ListItem(ViewHolders.ListItem.Model("ListItem\n\n4"))),
                    GenericItemModel(4,VhModel.ListItem(ViewHolders.ListItem.Model("ListItem\n5"))),
                    GenericItemModel(5,VhModel.ListItem(ViewHolders.ListItem.Model("ListItem\n\n\n6"))),
                    GenericItemModel(6,VhModel.ListItem(ViewHolders.ListItem.Model("ListItem\n7"))),
                    GenericItemModel(7,VhModel.StickyHeader(ViewHolders.StickyHeader.Model("Hea\nder2"))),
                    GenericItemModel(8,VhModel.ListItem(ViewHolders.ListItem.Model("ListI\ntem8"))),
                    GenericItemModel(9,VhModel.ListItem(ViewHolders.ListItem.Model("ListI\n\ntem9"))),
                    GenericItemModel(10,VhModel.ListItem(ViewHolders.ListItem.Model("ListI\n\n\ntem10"))),
                    GenericItemModel(11,VhModel.ListItem(ViewHolders.ListItem.Model("ListI\n\ntem11"))),
                    GenericItemModel(12,VhModel.ListItem(ViewHolders.ListItem.Model("ListItem12"))),
                    GenericItemModel(13,VhModel.ListItem(ViewHolders.ListItem.Model("ListI\ntem13"))),
                    GenericItemModel(14,VhModel.ListItem(ViewHolders.ListItem.Model("ListItem14"))),
                    GenericItemModel(15,VhModel.ListItem(ViewHolders.ListItem.Model("ListI\n\n\n\n\n\ntem15"))),
                    GenericItemModel(16,VhModel.StickyHeader(ViewHolders.StickyHeader.Model("H\n\n\n\n\n\n\neader3"))),
                    GenericItemModel(17,VhModel.ListItem(ViewHolders.ListItem.Model("L\nistI\n\nt\ne\n\nm16"))),
                    GenericItemModel(18,VhModel.ListItem(ViewHolders.ListItem.Model("ListIte\nm17"))),
                    GenericItemModel(19,VhModel.ListItem(ViewHolders.ListItem.Model("List\nIt\n\n\nem18"))),
                    GenericItemModel(20,VhModel.ListItem(ViewHolders.ListItem.Model("List\nIt\nem19"))),
                    GenericItemModel(21,VhModel.ListItem(ViewHolders.ListItem.Model("ListI\ntem20"))),
                    GenericItemModel(22,VhModel.ListItem(ViewHolders.ListItem.Model("List\n\nIt\nem21"))),
                    GenericItemModel(23,VhModel.ListItem(ViewHolders.ListItem.Model("ListItem22"))),
                    GenericItemModel(24,VhModel.StickyHeader(ViewHolders.StickyHeader.Model("He\nader4"))),
                    GenericItemModel(25,VhModel.ListItem(ViewHolders.ListItem.Model("ListItem23"))),
                    GenericItemModel(26,VhModel.ListItem(ViewHolders.ListItem.Model("ListItem24"))),
                    GenericItemModel(27,VhModel.ListItem(ViewHolders.ListItem.Model("ListItem25"))),
                    GenericItemModel(28,VhModel.ListItem(ViewHolders.ListItem.Model("ListI\ntem26"))),
                    GenericItemModel(29,VhModel.ListItem(ViewHolders.ListItem.Model("ListIt\n\n\nem27"))),
                    GenericItemModel(30,VhModel.ListItem(ViewHolders.ListItem.Model("ListI\n\ntem28"))),
                    GenericItemModel(31,VhModel.ListItem(ViewHolders.ListItem.Model("ListItem29"))),
                    GenericItemModel(32,VhModel.ListItem(ViewHolders.ListItem.Model("ListI\ntem30"))),
                    GenericItemModel(33,VhModel.ListItem(ViewHolders.ListItem.Model("ListI\n\n\ntem31"))),
                    GenericItemModel(34,VhModel.ListItem(ViewHolders.ListItem.Model("ListI\n\n\ntem32"))),
                    GenericItemModel(35,VhModel.ListItem(ViewHolders.ListItem.Model("ListItem33"))),
                    GenericItemModel(36,VhModel.ListItem(ViewHolders.ListItem.Model("ListIt\n\nem34"))),
                    GenericItemModel(37,VhModel.ListItem(ViewHolders.ListItem.Model("ListIt\nem35"))),
                    GenericItemModel(38,VhModel.ListItem(ViewHolders.ListItem.Model("List\n\n\nItem36"))),
                    GenericItemModel(39,VhModel.ListItem(ViewHolders.ListItem.Model("ListIt\nem37"))),
                    GenericItemModel(40,VhModel.ListItem(ViewHolders.ListItem.Model("ListItem38"))),
                    GenericItemModel(41,VhModel.StickyHeader(ViewHolders.StickyHeader.Model("H\neader5"))),
                    GenericItemModel(42,VhModel.ListItem(ViewHolders.ListItem.Model("ListItem39"))),
                    GenericItemModel(43,VhModel.ListItem(ViewHolders.ListItem.Model("ListIte\nm40"))),
                    GenericItemModel(44,VhModel.ListItem(ViewHolders.ListItem.Model("Li\nstItem41"))),
                    GenericItemModel(45,VhModel.ListItem(ViewHolders.ListItem.Model("ListItem42"))),
                    GenericItemModel(46,VhModel.ListItem(ViewHolders.ListItem.Model("Lis\ntItem43"))),
                    GenericItemModel(47,VhModel.ListItem(ViewHolders.ListItem.Model("ListItem44"))),
                    GenericItemModel(48,VhModel.ListItem(ViewHolders.ListItem.Model("ListItem45"))),
                    GenericItemModel(49,VhModel.ListItem(ViewHolders.ListItem.Model("ListIt\nem\n46"))),
                    GenericItemModel(50,VhModel.ListItem(ViewHolders.ListItem.Model("ListItem47"))),
                    GenericItemModel(51,VhModel.ListItem(ViewHolders.ListItem.Model("ListItem48"))),
                )
            )
        }
    }
}