package components.ranking;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.glovppetito42eats.R;

import java.util.List;

import adapters.TablaListViewAdapter;
import api.ApiService;
import api.response.CVID_Tabla;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankingActivity extends AppCompatActivity {

    ListView lstTabla;
    int tipo;

    ImageView imgCargando;
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallaranking);

        imgCargando = findViewById(R.id.imgCargando);
        imgCargando.setBackgroundResource(R.drawable.cargando);
        imgCargando.setVisibility(View.VISIBLE);

        animationDrawable = (AnimationDrawable) imgCargando.getBackground();
        animationDrawable.start();

        InicializarControles();
        LoadListView(0);
    }

    private void LoadListView(int n) {
        Call<List<CVID_Tabla>> response = ApiService.getApiService().getRanking();
        response.enqueue(new Callback<List<CVID_Tabla>>() {
            @Override
            public void onResponse(Call<List<CVID_Tabla>> call, Response<List<CVID_Tabla>> response) {
                if (response.isSuccessful()) {
                    List<CVID_Tabla> table = response.body();
                    TablaListViewAdapter adapter = new TablaListViewAdapter(getApplicationContext(), table);
                    imgCargando.setVisibility(View.GONE);
                    lstTabla.setVisibility(View.VISIBLE);
                    lstTabla.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<CVID_Tabla>> call, Throwable t) {

            }
        });
    }

    private void InicializarControles() {
        lstTabla = findViewById(R.id.lstTabla);
    }

    public void General(View v) {
        tipo = 1;
        LoadListView(tipo);
    }

    public void Local(View v) {
        tipo = 0;
        LoadListView(tipo);
    }
}