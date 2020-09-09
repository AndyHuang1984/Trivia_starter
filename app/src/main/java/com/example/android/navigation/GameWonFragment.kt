/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {

    lateinit var binding: FragmentGameWonBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 初始化 Event
        initEvent()
    }

    /** 初始化 Event */
    private fun initEvent() {
        binding.nextMatchButton.setOnClickListener {
            it.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }
    }

    /** 取得 Intent */
    private fun getShareIntent() : Intent {
        var args = GameWonFragmentArgs.fromBundle(arguments!!)
        return ShareCompat.IntentBuilder.from(activity!!)
                .setText(getString(R.string.share_success_text, args.numQuestions, args.numQuestions))
                .setType("text/plain")
                .intent
    }

    /** 啟動呼叫外部應用 Intent */
    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.winner_menu, menu)
        // 檢查 activity resolves
        if (null == getShareIntent().resolveActivity(activity!!.packageManager)) {
            // 隱藏選單
            menu?.findItem(R.id.share)?.setVisible(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId) {
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }
}
