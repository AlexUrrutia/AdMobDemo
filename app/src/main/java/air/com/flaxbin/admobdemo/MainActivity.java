package air.com.flaxbin.admobdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class MainActivity extends AppCompatActivity {
   //INTERSTICIAL (PANTALLA COMPLETA)
    private InterstitialAd mInterstitialAd;

    int ClickNumber = 0;
    Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //ADMOB
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        //
        Button button = (Button) findViewById(R.id.mMyButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ClickNumber++;
                Toast.makeText(getApplicationContext(), "CLICK "+ClickNumber, Toast.LENGTH_SHORT).show();

                if (mInterstitialAd.isLoaded()) {
                    if(ClickNumber >= 4) {
                        mInterstitialAd.show();
                        ClickNumber = 0;
                    }
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                    Toast.makeText(getApplicationContext(), "ANUNCIO NO HA SIDO CARGADO...", Toast.LENGTH_SHORT).show();

                }
            }
        });




        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.d("TAG", "ANUNCIO  CARGADO Y LISTO!");

                Toast.makeText(getApplicationContext(), "ANUNCIO CARGADO Y LISTO...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.d("TAG", "ANUNCIO HA FALLADO EN CARGAR.");
                Toast.makeText(getApplicationContext(), "ANUNCIO CARGADO Y LISTO...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Log.d("TAG", "MOSTRANDO ANUNCIO");

            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Log.d("TAG", "CLICK EN ANUNCIO");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.d("TAG", "USUARIO ABANDONO APP");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                Log.d("TAG", "ANUNCIO CERRADO");
                Toast.makeText(getApplicationContext(), "Anuncio cerrado, cargando uno nuevo...", Toast.LENGTH_SHORT).show();
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }
}
