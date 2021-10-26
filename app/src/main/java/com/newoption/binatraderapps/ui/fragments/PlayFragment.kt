package com.newoption.binatraderapps.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.newoption.binatraderapps.MainActivity
import com.newoption.binatraderapps.R
import com.newoption.binatraderapps.databinding.PlayFragmentBinding


class PlayFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: PlayFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.play_fragment, container, false
        )
        binding.fragment = this
        binding.greeting.setContent { JetpackCompose() }
        return binding.root
    }


    override fun onClick(p0: View?) {
        play()
    }

    fun play() {
        findNavController().navigate(R.id.action_Play_to_Main)
        /*val intent = Intent(activity, GameActivity::class.java)
        startActivity(intent)
        requireActivity().finish()*/
    }

    fun policy() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://docs.google.com/document/d/1duxczIeqeEnIqMwDYxYS-5frDEfs3N0y7pyt4EcjOUs/")
        )
        startActivity(intent)
    }

    fun call() {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "+71234567890"))
        startActivity(intent)
    }

    fun slide() {
        findNavController().navigate(R.id.action_playFragment_to_slideFragment)
    }

    @Composable
    fun JetpackCompose() {
        Card(backgroundColor = Color.Transparent) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.logo1),
                    contentDescription = getString(R.string.image),
                    alignment = Alignment.Center
                )
                Column(

                    horizontalAlignment = Alignment.CenterHorizontally,

                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    /*Text(
                        text = getString(R.string.app_name),
                        style = MaterialTheme.typography.h5
                    )*/
                    Image(
                        painter = painterResource(R.drawable.tool),
                        contentDescription = getString(R.string.image),
                        alignment = Alignment.Center,
                        modifier = Modifier.padding(all = 8.dp).clickable { play() }
                    )
                    Image(
                        painter = painterResource(R.drawable.info),
                        contentDescription = getString(R.string.image),
                        alignment = Alignment.Center,
                        modifier = Modifier.padding(all = 8.dp).clickable { slide() }

                    )
                    Image(
                        painter = painterResource(R.drawable.policy),
                        contentDescription = getString(R.string.image),
                        alignment = Alignment.Center,
                        modifier = Modifier.defaultMinSize(minHeight = 64.dp).padding(all = 8.dp).clickable { policy() },
                        contentScale = ContentScale.FillHeight

                    )
                    Image(
                        painter = painterResource(R.drawable.ic_left),
                        contentDescription = getString(R.string.image),
                        alignment = Alignment.Center,
                        modifier = Modifier.defaultMinSize(minHeight = 64.dp).padding(all = 8.dp).clickable {
                            val res= activity as MainActivity
                            res.onFragmentBackPressed()
                        },
                        contentScale = ContentScale.FillHeight
                    )
                }


            }

        }
    }
}


