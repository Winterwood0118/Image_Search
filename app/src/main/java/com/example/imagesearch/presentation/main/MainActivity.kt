package com.example.imagesearch.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.imagesearch.R
import com.example.imagesearch.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

private const val PREF_KEY = "pref_key"
private const val WORD_KEY = "word_key"
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val imageSearchViewModel by viewModels<ImageSearchViewModel> ()
    private val sharedPref by lazy { getSharedPreferences(PREF_KEY, MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initFragment()
        initButton()
        loadData()
    }

    override fun onStop() {
        super.onStop()
        saveData()
    }

    private fun getFragmentByTag(tag: String): Fragment {
        return supportFragmentManager.findFragmentByTag(tag) ?: ImageSearchFragment()
    }

    // 두 프래그먼트 모두 띄워놓은 상태로 하단 버튼을 통해 show, hide만 바꿔줌, tap layout, navigation 등 사용 시 필요 없는 과정
    private fun initFragment() {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.frameFragment, MyBoxFragment(), "my_box")
            hide(getFragmentByTag("my_box"))
            add(R.id.frameFragment, ImageSearchFragment(), "search")
            show(getFragmentByTag("search"))
            commit()
        }
    }

    private fun initButton() {
        with(binding) {
            btnSearch.apply {
                setOnClickListener {
                    supportFragmentManager.beginTransaction().apply {
                        hide(getFragmentByTag("my_box"))
                        show(getFragmentByTag("search"))
                        commit()
                    }
                    btnMyBox.isClickable = true
                    isClickable = false
                }
                isClickable = false
            }

            btnMyBox.apply {
                setOnClickListener {
                    supportFragmentManager.beginTransaction().apply {
                        show(getFragmentByTag("my_box"))
                        hide(getFragmentByTag("search"))
                        commit()
                    }
                    isClickable = false
                    btnSearch.isClickable = true
                }
            }
        }
    }

    private fun loadData() { // shared preference에서 마지막 검색기록 불러와서 viewModel에 저장
        val currentSearchWord = sharedPref.getString(WORD_KEY, "") ?: ""
        imageSearchViewModel.setLastSearchWord(currentSearchWord)
        imageSearchViewModel.getKLikeThumbnailList()
    }

    private fun saveData() { // viewModel에서 마지막 검색기록 가지고 와서 shared preferenc에 저장
        val edit = sharedPref.edit()
        val currentSearchWord = imageSearchViewModel.lastSearchWord.value ?: ""
        edit.putString(WORD_KEY, currentSearchWord)
        edit.apply()
    }
}
