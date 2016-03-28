package myfirstjson.com.myfirstjson;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;


public class MyFirstJSON extends AppCompatActivity implements View.OnClickListener{

    Button button;
    ListView listView;
    String url = "http://yazilimciakli.com/_mehmet/json/data.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_first_json);

        button = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.listView);
        button.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:


                RequestQueue requestQueue = Volley.newRequestQueue(MyFirstJSON.this);//projemizde yeni bir  volley oluşturduk

                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {//urlmizi get methoduyla strink ifadeye ve bir fonksiyona giriş yaptık
                    @Override
                    public void onResponse(String response) {//dönen cevap response değişkeninin içerisinde

                        Gson gson = new Gson();//yeni bir gson oluşturduk
                        final List<Kelime> kelimeler = Arrays.asList(gson.fromJson(response, Kelime[].class));//dönen değeri Kelime classımıza göre gsona yolladık
                        final ArrayAdapter<Kelime> kelimelerAdapter = new ArrayAdapter<Kelime>(MyFirstJSON.this, android.R.layout.simple_list_item_1, android.R.id.text1, kelimeler);
                        //dönen verileri listviewe yazmak için ArrayAdapter a ihtiyacımız var.
                        listView.setAdapter(kelimelerAdapter);//array adapter ı listview e gönderdik
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            //burada listviewdeki herhangi bir kelimeye tıkladığınızda mesaj kutusu çıkıyor
                            //burayı yazma nedenim gson ile parçalanan verileri nasıl kullanabileceğimizi anlatmak
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                AlertDialog.Builder dialogOlusturucu = new AlertDialog.Builder(MyFirstJSON.this);//mesaj kutusu oluşturmak
                                dialogOlusturucu.setMessage("Türkçesi:" + kelimelerAdapter.getItem(position).getTr()//bu komut bize seçilen kelimenin Tr kısmını gösterecek
                                        + "\n" + "İngilizcesi:" + kelimelerAdapter.getItem(position).getEn()//bu komut bize seçilen kelimenin En kısmını gösterecek
                                )
                                        .setCancelable(false)
                                        .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                dialogOlusturucu.create().show();

                            }
                        });
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MyFirstJSON.this, "Bağlantı sırasında hata oluştu!", Toast.LENGTH_SHORT).show();//bağlantı yapılamazsa uyarı verecek
                    }
                });

                requestQueue.add(request);//oluşturduğumuz volley bilgileri gönderdik.

                break;
        }
    }
}
