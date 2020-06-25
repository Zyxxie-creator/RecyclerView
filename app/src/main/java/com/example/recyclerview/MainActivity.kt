package com.example.recyclerview

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





        val imageList = listOf<ItemOfList>(
            ItemOfList(
                R.drawable.img1, "Котик",
                "Новости из мира ядерной физики:\n" +
                        "Желудок у котенка не больше наперстка, следовательно,\n" +
                        "те два литра молока, которые он способен выпить за час,\n" +
                        "находятся в его желудке под далением 50000 атмосфер,\n" +
                        "что в десять раз больше давления в эпицентре ядерного взрыва."
            ),
            ItemOfList(
                R.drawable.img2,
                "Гепард",
                "Самое быстрое животное – это гепард, а второе место, твердо удерживает блоха скачущая по бегущему гепарду."
            ),
            ItemOfList(
                R.drawable.img3,
                "Хомяк",
                "— Расскажи о хомяке. — Хомяк — это такой суслик, который похож на куницу.\n"
            ),
            ItemOfList(
                R.drawable.img4,
                "Ламы",
                "Кто такие альпака и лама? Чем они отличаются? После почти года жизни в Перу мне стало очень интересно в этом разобраться. Так вот, альпака и лама — животные семейства верблюдовых, родиной которых являются высокогорные районы Анд Южной Америки. До приезда в Латинскую Америку я лично про альпаку вообще ничего не слышала, да и о ламе имела смутное представление. Но оказывается, это разные животные и предназначение у них тоже абсолютно разное\n"

            )
        )
        val recyclerView = findViewById<RecyclerView>(R.id._imageRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = ItemAdapter(this, imageList){
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("OBJECT_INTENT", it)
            startActivity(intent)
        }
    }
    private fun run(url: String){
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response.body()?.string()
                val jsonImage = (JSONObject(json).get("file")).toString()
                this@MainActivity.runOnUiThread{
                    if(response.isSuccessful){
                        Picasso.get()
                            .load(jsonImage)
                            .fit()
                            .into(_imageAPI)
                    }
                }
            }
        })
    }
}

