package com.example.imagesearch.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.imagesearch.R
import com.example.imagesearch.databinding.ActivityMainBinding
import com.example.imagesearch.presentation.entity.DocumentEntity

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initFragment()
        initButton()

    }

    private fun initFragment(){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameFragment, ImageSearchFragment())
            addToBackStack("")
            commit()
        }
    }

    private fun initButton(){
        with(binding){
            btnSearch.apply {
                setOnClickListener {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameFragment, ImageSearchFragment())
                        addToBackStack(null)
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
                        replace(R.id.frameFragment, MyBoxFragment())
                        commit()
                    }
                    isClickable = false
                    btnSearch.isClickable = true
                }
            }
        }
    }
    private fun loadData(savedInstanceState: Bundle?){
        if (savedInstanceState != null){
            // PICKED_IMAGE = this@MainActivity.getSharedPreferences(PICKED_LIST, MODE_PRIVATE)
        }
    }

    companion object{
        var PICKED_IMAGE = mutableListOf<DocumentEntity>()
        const val PICKED_LIST = "picked_list"
    }
}

/*

구조설계

data
1) database의 데이터를 사용 가능한 클래스로 만들기 위한 Entity ㅇ
2) Entity 형태로 데이터를 받아오는 database ㅇ
3) database의 정보를 저장하는 Repository ㅇ
4) picked data를 저장하는 preference?

presentation
1) 두 프래그먼트를 얹을 main ㅇ
2) 두 프래그먼트 ㅇ
3) Mode?
4) Entity -> Parcelable Data ㅇ
5) MVVM 이용 ㅇ?

1. 메인 액티비티 레이아웃 작성(1 FrameLayout, 2 Button) ㅇ
2. ImageSearchFragment 레이아웃 작성 -> 서치 뷰(or Edit Text + Button), 리사이클러 뷰(그리드 레이아웃)
3. MyBoxFragment 레이아웃 작성 -> 리사이클러 뷰(그리드 레이아웃) <- 어댑터 돌려써도 될지도?
4. 리사이클러 뷰 아이템 작성 -> 이미지뷰(Glide 써보기), 출처(네이버 블로그 등), 시간(yyyy-MM-dd HH:mm:ss), 선택표시 -> 어댑터 쓸 때 프래그먼트 뭐쓰는지 받아오기? 이넘클래스 이용? 고민 좀더
5. Preference 이용해서 마지막 검색 저장해서 불러오기 없으면 "" 받아오게
6. 레트로핏 이용해서 json 받아와서 Entity에 저장 -> Custom Class에 저장 ㅇ

Try
피그마 이용해서 레이아웃 괜찮은거 가져오기

*/