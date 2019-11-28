package com.zs.various.activity

import android.graphics.RectF
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import com.zs.various.R
import com.zs.various.base.BaseActivity
import com.zs.various.util.LogUtil
import com.zs.various.util.voice.SimilarityCompare
import kotlinx.android.synthetic.main.activity_voice.*

class VoiceActivity : BaseActivity() {

    private var mCompare: SimilarityCompare? = null
    private val originStr = ("When I'm ten I was suddenly confronted with the anguish of moving from the only home I had ever known.My whole life,brief as it was,had been spent in that big old house,gracefully touched with the laughter and tears of four generations."
            +
            "When the final day came,I ran to the haven of the small back porch and sat alone,shuddering,as tears welled up from my heart.Suddenly I felt a hand rest on my shoulder.I looked up to see my grandfather.It isn’t easy,is it,Billy? he said softly,sitting down on the steps beside me.Grandpa, I replied through my tears,how can I ever say goodbye to you and all my friends? "
            +
            "For a moment he just stared off into the apple trees.Goodbye is such a sad word, he said.It seems too final,too cold,for friends to use.We seem to have so many ways of saying goodbye and they all have one thing in common:sadness. I continued to look into his face.He gently took my hand in his.Come with me,my friend, he whispered.We walked,hand in hand,to his favorite place in the front yard,where a huge red rosebush sat conspicuously alone."
            +
            "What do you see here,Billy? he asked.I looked at the flowers,not knowing what to say,and then answered,I see something soft and beautiful,Grandpa.Kneeling,he pulled me close.It isn’t just the roses that are beautiful,Billy.It’s that special place in your heart that makes them so.His eyes met mine again.Billy,I planted these roses a long,long time ago—before your mother was even a dream.I put them into the soil the day my first son was born.It was my way of saying thankyou to God.That boy’s name was Billy,just like yours.I used to watch him pick roses for his mother. I saw my grandfather’s tears.I had never seen him cry before.His voice became hoarse.One day a terrible war came,and my son,like so many sons,went away to fight a great evil.He and I walked to the train station together...Ten months later a telegram came.My son had died in some tiny village in Italy.All I could think of was that the last thing I said to him in his life was goodbye. Grandpa slowly stood up.Don’t ever say goodbye,Billy.Don’t ever give in to the sadness and the loneliness of that word.I want you to remember instead the joy and the happiness of those times when you first said hello to a friend.Take that special hello and lock it away within you—in that place in your heart where summer is an always-time.When you and your friends must part,I want you to reach deep within you and bring back that first hello. "
            +
            "A year and a half later,my grandfather became gravely ill.When he returned after several weeks in the hospital,he wanted his bed next to the window,where he could see his beloved rosebush.Then the family was summoned,and I returned to the old house.It was decided that the oldest grandchildren would be allowed to say their goodbyes.When it came to my turn,I noted how tired he looked.His eyes were closed and his breathing was slow and hard.I took his hand as gently as he had once taken mine.Hello,Grandpa, I whispered.His eyes slowly opened,Hello,my friend, he said,with a brief smile.His eyes closed again and I moved on.I was standing by his rosebush when an uncle came to tell me that my grandfather had died.Remembering Grandpa’s words,I reached deep within me for those special feelings that had made up our friendship.Suddenly,and truly,I knew what he had meant about never saying goodbye—about refusing to give in to sadness.")
    private val resultStr = ("When I was ten I was suddenly confronted with the anguish of moving from the only home I had ever known.My whole life,brief as it was,had been spent in that big old house,gracefully touched with the laughter and tears of four generations."
            +
            "When the final day came,I ran to the haven of the small back porch and sat alone,shuddering,as tears welled up from my heart.Suddenly I felt a hand rest on my shoulder.I looked up to see my grandfather.It isn’t easy,is it,Billy? he said softly,sitting down on the steps beside me.Grandpa, I replied through my tears,how can I ever say goodbye to you and all my friends? "
            +
            "For a moment he just stared off into the apple trees.Goodbye is such a sad word, he said.It seems too final,too cold,for friends to use.We seem to have so many ways of saying goodbye and they all have one thing in common:sadness. I continued to look into his face.He gently took my hand in his.Come with me,my friend, he whispered.We walked,hand in hand,to his favorite place in the front yard,where a huge red rosebush sat conspicuously alone."
            +
            "I see something soft and beautiful,Grandpa.Kneeling,he pulled me close.It isn’t just the roses that are beautiful,Billy.It’s that special place in your heart that makes them so.His eyes met mine again.Billy,I planted these roses a long,long time ago—before your mother was even a dream.I put them into the soil the day my first son was born.It was my way of saying thankyou to God.That boy’s name was Billy,just like yours.I used to watch him pick roses for his mother. I saw my grandfather’s tears.I had never seen him cry before.His voice became hoarse.One day a terrible war came,and my son,like so many sons,went away to fight a great evil.He and I walked to the train station together...Ten months later a telegram came.My son had died in some tiny village in Italy.All I could think of was that the last thing I said to him in his life was goodbye. Grandpa slowly stood up.Don’t ever say goodbye,Billy.Don’t ever give in to the sadness and the loneliness of that word.I want you to remember instead the joy and the happiness of those times when you first said hello to a friend.Take that special hello and lock it away within you—in that place in your heart where summer is an always-time.When you and your friends must part,I want you to reach deep within you and bring back that first hello. "
            +
            "A year and a half later,my grandfather became gravely ill.When he returned after several weeks in the hospital,he wanted his bed next to the window,where he could see his beloved rosebush.Then the family was summoned,and I returned to the old house.It was decided that the oldest grandchildren would be allowed to say their goodbyes.When it came to my turn,I noted how tired he looked.His eyes were closed and his breathing was slow and hard.I took his hand as gently as he had once taken mine.Hello,Grandpa, I whispered.His eyes slowly opened,Hello,my friend, he said,with a brief smile.His eyes closed again and I moved on.I was standing by his rosebush when an uncle came to tell me that my grandfather had died.Remembering Grandpa’s words,I reached deep within me for those special feelings that had made up our friendship.Suddenly,and truly,I knew what he had meant about never saying goodbye—about refusing to give in to sadness.")


    var handler = Handler()

    var rectF: RectF? = null

    var mTime = 0

    override fun setLayoutId(): Int {
        return R.layout.activity_voice
    }

    fun compare(){
        mCompare = SimilarityCompare(this)
        val result = mCompare!!.compareCosineSimilarity(originStr, resultStr)
        LogUtil.logShow("result = $result")
    }

    override fun initView() {

        record_view?.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        record_view?.startAnim()
                        countDown()
                    }
                    MotionEvent.ACTION_CANCEL,
                    MotionEvent.ACTION_UP -> {
                        handler.removeCallbacksAndMessages(null)
                        record_view?.setRecordStatus(false)

                    }
                    MotionEvent.ACTION_MOVE -> {
                        getRect()?.let {
                            if (it.contains(event.rawX, event.rawY)){
                                record_view?.setRecordStatus(true)
                            }else{
                                record_view?.setRecordStatus(false)
                            }
                        }
                    }
                }
                return true
            }
        })

    }

    private fun getRect(): RectF? {
        if (rectF == null) {
            val location = IntArray(2)
            // 获取控件在屏幕中的位置，返回的数组分别为控件左顶点的 x、y 的值
            record_view.getLocationOnScreen(location)
            rectF = RectF(location[0].toFloat(), location[1].toFloat(), (location[0] + record_view.width).toFloat(), (location[1] + record_view.height).toFloat())
        }
        return rectF
    }

    fun countDown(){
        record_time?.text = getString(R.string.count_down_time).format(mTime)
        record_view?.setProgress(mTime.toFloat())
        mTime ++
        handler.postDelayed({
            countDown()
        } , 1000)
    }

    override fun initData() {


    }


}
