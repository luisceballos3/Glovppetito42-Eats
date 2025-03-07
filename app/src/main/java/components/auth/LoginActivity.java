package components.auth;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.glovppetito42eats.R;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import api.ApiService;
import api.request.RequestUser;
import api.response.UserResponse;
import ui.bar.navigation.NavigationBarUI;


public class LoginActivity extends AppCompatActivity {

    EditText userEmail, userPassword;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEmail = (EditText) findViewById(R.id.email);
        userPassword = (EditText) findViewById(R.id.password);

        //verifyUserSession();
    }

    private void verifyUserSession() {

            startActivity(new Intent(getApplicationContext(), components.auth.AuthMessageActivity.class));
    }

    public void performUserLogin(View v) {
        try {
            String user = userEmail.getText().toString();
            String pass = userPassword.getText().toString();
            RequestUser request = new RequestUser();
            request.setCorreo(user);
            request.setPassword(pass);

            retrofit2.Call<UserResponse> response = ApiService.getApiService().login(request);
            response.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(retrofit2.Call<UserResponse> call, Response<UserResponse> response) {
                    if (response.isSuccessful()) {
                        UserResponse estudiante = response.body();
                        if (estudiante != null) {

                            Toast.makeText(getApplicationContext(), "Login Exitoso", Toast.LENGTH_LONG).show();
                            estudiante.setTipo(3);

                            Intent i = new Intent(getApplicationContext(), NavigationBarUI.class);
                            i.putExtra("UserId", estudiante.getUsuario_id());
                            i.putExtra("Nombre", estudiante.getNombre());
                            i.putExtra("Apellido", estudiante.getApellido());
                            i.putExtra("Cedula", estudiante.getCedula());
                            i.putExtra("Correo", estudiante.getCorreo());

                            startActivity(i);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Error Al Iniciar Sesión", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Error Al Iniciar Sesión", Toast.LENGTH_LONG).show();
                    int x = 1;
                }
            });
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error Al Iniciar Sesión", Toast.LENGTH_LONG).show();
            int x = 1;
        }
    }

    public void registerUser(View v) {
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        i.putExtra("num", 1);
        startActivity(i);
    }

    public void recoverUserPassword(View view) {
        startActivity(new Intent(getApplicationContext(), PasswordRecoveryActivity.class));
    }

}