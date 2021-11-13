package com.genshindaily.genshindaily

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.fragment_option.*
import java.util.*

class InfoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_info, null)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val builder = AlertDialog.Builder(requireActivity())

        aloy_materials.setOnClickListener{
            val dialogView = layoutInflater.inflate(R.layout.materials_aloy, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        raiden_materials.setOnClickListener{
            val dialogView = layoutInflater.inflate(R.layout.materials_raiden, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        yoimiya_materials.setOnClickListener{
            val dialogView = layoutInflater.inflate(R.layout.materials_yoimiya, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }


    }//onViewCreated

}