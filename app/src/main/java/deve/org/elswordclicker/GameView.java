//package where the application is stored
package deve.org.elswordclicker;
//imports for the functions and methods and variables to work
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Objects;
import java.util.Random;
//class
public class GameView extends AppCompatActivity {
    //for saving variables
    SharedPreferences sharedPref;
    //all the images used
    ImageView enemy,magma,buggiebomb,milch,ivB;
    //the healthbar
    ProgressBar health;
    //all the textviews used
    TextView stage, eldollar,dmmg,lifee,acs;
    //for having delays
    Handler h = new Handler();
    //the layout
    ScrollView rll;
    //method to get random integer
    Random rn = new Random();
    //Runnable function
    Runnable runnable;
    String version = "beta 3.0-0";

    int delay = 10000;
    int autoclick = 0;

    int max_life = 100;
    int current_life = 100;
    int current_stage = 1;

    long ieldollar = 0;

    long longswoard_price = 100;
    int autoclick_price = 15000;

    long dollarperstage;


    long dmg = 1;
    int intBoss = 1;

    //on create function
    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_main);
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        //telling the app where anything is
        enemy = findViewById(R.id.enemy);
        magma = findViewById(R.id.magma);
        buggiebomb = findViewById(R.id.buggiebomb);
        milch = findViewById(R.id.milch);
        health = findViewById(R.id.health);
        stage = findViewById(R.id.stage);
        eldollar = findViewById(R.id.eldollar);
        dmmg = findViewById(R.id.dmmg);
        lifee = findViewById(R.id.lifee);
        rll = findViewById(R.id.rll);
        acs = findViewById(R.id.acs);
        ivB = findViewById(R.id.ivB);

        getSupportActionBar().addOnMenuVisibilityListener(new ActionBar.OnMenuVisibilityListener() {
            @Override
            public void onMenuVisibilityChanged(boolean isVisible) {

            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        Glide.with(this).load("https://cdna.artstation.com/p/assets/images/images/004/552/832/large/ji-hyun-kim-0-1.jpg?1484534208").into(ivB);
        Glide.with(this).load("https://vignette.wikia.nocookie.net/murderseries/images/1/1b/Diabolicesper.png/revision/latest?cb=20160313223239").into(enemy);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                DrawerLayout drawerLayout1 = findViewById(R.id.drawer_layout);
                switch (id){
                    case R.id.idItem1:
                        break;
                    case R.id.idItem2:
                        break;
                    case R.id.idItem3:
                        break;
                    case R.id.idItem4:
                        break;
                    case R.id.idItem5:
                        break;
                    case R.id.idItem6:
                        onBeforeBuyLongsword(item.getTitle().toString());
                        break;
                    case R.id.idItem7:
                        onBeforeBuyLongsword(item.getTitle().toString());
                        break;
                    case R.id.idItem8:
                        drawerLayout1.closeDrawer(GravityCompat.START);
                        onAppInfo();
                        break;
                }

                drawerLayout1.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        //methods to let this stuff being clickable
        enemy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEnemy(0);
            }
        });
        magma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                magma.setVisibility(View.INVISIBLE);
                onClickEnemy(dmg*10);
                h.postDelayed(new Runnable() {
                    public void run() {
                        magma.setVisibility(View.VISIBLE);
                    }
                }, (1000)*45);
            }
        });
        buggiebomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buggiebomb.setVisibility(View.INVISIBLE);
                onClickEnemy(dmg*40);
                h.postDelayed(new Runnable() {
                    public void run() {
                        buggiebomb.setVisibility(View.VISIBLE);
                    }
                }, (1000)*90);
            }
        });
        milch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                milch.setVisibility(View.INVISIBLE);
                onClickEnemy(dmg*80);
                h.postDelayed(new Runnable() {
                    public void run() {
                        milch.setVisibility(View.VISIBLE);
                    }
                }, (1000)*180);
            }
        });

    }

    //Setting all variables to their saved value on every application start
    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        Glide.with(this).load("https://cdna.artstation.com/p/assets/images/images/004/552/832/large/ji-hyun-kim-0-1.jpg?1484534208").into(ivB);
        Glide.with(this).load("https://vignette.wikia.nocookie.net/murderseries/images/1/1b/Diabolicesper.png/revision/latest?cb=20160313223239").into(enemy);
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        max_life = sharedPref.getInt("mlife",100);
        intBoss = sharedPref.getInt("boss",1);
        health.setMax(max_life);
        current_life = sharedPref.getInt("clife",100);
        health.setProgress(current_life);
        current_stage = sharedPref.getInt("stage",1);
        stage.setText("Stage "+ current_stage);
        ieldollar = sharedPref.getLong("eldollar",0);
        eldollar.setText("Eldollar: "+ieldollar);
        dmg = sharedPref.getLong("damage",1);
        longswoard_price = sharedPref.getLong("longswordp",100);
        dmmg.setText("Damage: "+dmg);
        lifee.setText(current_life+"/"+max_life);
        autoclick_price = sharedPref.getInt("autoclickp",15000);
        delay = sharedPref.getInt("delay",5000);
        acs.setText("dmg/ms: "+dmg/10+"/"+delay);
        super.onStart();
    }
    public void onBeforeBuyLongsword(String item){

        final long tenLongswords = 10*longswoard_price + 10*100;

        final double l1 = ieldollar/longswoard_price;
        final double l10 = ieldollar/tenLongswords;

        if(item.equals("Longsword")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Longsword");
            builder.setMessage("Pricelist:" +
                    "\n" +
                    "\n" +
                    "Buy 1 for "+ longswoard_price+" ED  (" +(l1) +")" +
                    "\n" +
                    "Buy 10 for "+tenLongswords+" ED  (" + (l10) + ")" +
                    "\n" +
                    "Buy max. for your ED");
            builder.setPositiveButton("Buy 1", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    onBuyLongsword();
                }
            });
            builder.setNegativeButton("Buy 10", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(ieldollar>tenLongswords){
                        for(int e = 0; e<10; e++){
                            onBuyLongsword();
                        }
                    }else{
                        Toast.makeText(GameView.this, "Not enough money.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNeutralButton("Buy max.", new DialogInterface.OnClickListener() {
                int count;
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                        while(ieldollar>=longswoard_price){
                            onBuyLongsword();
                            count++;
                        }
                        Toast.makeText(GameView.this, "You bought "+count+" time(s).", Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();
        }
        else if(item.equals("Autoclicker")){
            if(delay!=100){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Longsword");
                builder.setMessage("Do you really want to buy 1 Autoclicker for "+autoclick_price+" ED?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBuyAutoClick();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }else{
                Toast.makeText(this, "You already reached the maximum.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //buying Long Sword function
    @SuppressLint("SetTextI18n")
    public void onBuyLongsword(){
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
         if(ieldollar>=longswoard_price){
             ieldollar -= longswoard_price;
             dmg++;
             dmmg.setText("Damage: "+dmg);
             SharedPreferences.Editor editor = sharedPref.edit();
             editor.putLong("eldollar", ieldollar);
             eldollar.setText("Eldollar: "+ieldollar);
             longswoard_price += 100;
             acs.setText("dmg/ms: "+dmg+"/"+delay);
             editor.putLong("longswordp",longswoard_price);
             editor.putLong("damage",dmg);
             editor.apply();
         }else{
             Toast.makeText(this, "Not enough money.", Toast.LENGTH_SHORT).show();
         }
    }
    @SuppressLint("SetTextI18n")
    public void onBuyAutoClick(){
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        if(delay != 100){
            if(ieldollar>=autoclick_price){
                ieldollar -= autoclick_price;
                autoclick++;
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("ac",autoclick);
                editor.putLong("eldollar", ieldollar);
                eldollar.setText("Eldollar: "+ieldollar);
                delay = delay - 100;
                editor.putInt("delay",delay);
                autoclick_price += 15000;
                editor.putInt("autoclickp", autoclick_price);
                acs.setText("dmg/ms: "+dmg/10+"/"+delay);
                editor.apply();

            }else{
                Toast.makeText(this, "Not enough money.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "You reached the maximum delay.", Toast.LENGTH_SHORT).show();
        }

    }

    //shacking Enemy function
    public void onShakeImage() {
        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        enemy.startAnimation(shake);
    }

    //Clicking function
    @SuppressLint("SetTextI18n")
    public void onClickEnemy(long magmadmg) {
        onShakeImage();
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        dollarperstage = 2 * (current_stage + dmg + magmadmg);
        if(rn.nextInt(1000-1)+1 == 1){
            current_life -= 100*(dmg + magmadmg);
            ieldollar += 100*(dollarperstage + (dmg+magmadmg));
            Toast.makeText(this, "Crit: "+100*(dmg + magmadmg), Toast.LENGTH_LONG).show();
        }else{
            current_life -= (dmg + magmadmg);
            ieldollar += dollarperstage + (dmg+magmadmg);
        }
        editor.putLong("eldollar", ieldollar);
        eldollar.setText("Eldollar: " + ieldollar);
        editor.putInt("clife", current_life);
        health.setProgress(current_life);
        while (current_life < 0) {
            current_stage++;
            intBoss++;
            editor.putInt("stage", current_stage);
            stage.setText("Stage " + current_stage);
            max_life += 100;
            editor.putInt("mlife", max_life);
            health.setMax(max_life);
            current_life = current_life + max_life;
            health.setProgress(current_life);
            lifee.setText(current_life + "/" + max_life);
            editor.putInt("clife", current_life);
        }
        lifee.setText(current_life + "/" + max_life);
        editor.apply();
    }
    @SuppressLint("SetTextI18n")
    public void onAutoClickEnemy(){
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        dollarperstage = (2 * (current_stage + dmg));
        if(rn.nextInt(1000-1)+1 == 1){
            current_life -= 100*(dmg/10);
            ieldollar += 100*(dollarperstage + (dmg/10));
            Toast.makeText(this, "Crit: "+100*(dmg/10), Toast.LENGTH_LONG).show();
        }else{
            current_life -= (dmg/10);
            ieldollar += dollarperstage + (dmg/10);
        }
        editor.putLong("eldollar", ieldollar);
        eldollar.setText("Eldollar: " + ieldollar);
        editor.putInt("c", current_life);
        health.setProgress(current_life);
        while (current_life < 0) {
            onShakeImage();
            current_stage++;
            intBoss++;
            editor.putInt("stage", current_stage);
            stage.setText("Stage " + current_stage);
            max_life += 100;
            editor.putInt("mlife", max_life);
            health.setMax(max_life);
            current_life = current_life + max_life;
            health.setProgress(current_life);
            lifee.setText(current_life + "/" + max_life);
            editor.putInt("clife", current_life);
        }
        lifee.setText(current_life + "/" + max_life);
        editor.apply();
    }

    //stop function
    @Override
    protected void onStop() {
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("clife",current_life);
        editor.apply();
        super.onStop();
    }

    //"Information about the App"-Button
    public void onAppInfo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(GameView.this);
                    builder.setTitle("App info");
                    builder.setMessage("This app/game was made by Emre.\nYou can tell me your feedback and if You have found a bug just tell it me in the group with a screenshot and a nice describtion of the bug.\n\n"+version);
                    builder.setPositiveButton("Join my Telegram group!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://t.me/joinchat/Fbev6FHu8EhWMIA1E_2-KA"));
                        startActivity(intent);
                    }
                });
                    builder.setNegativeButton("Source Code here", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/EmiDevGerman/ElswordClickerApp"));
                    startActivity(intent);
                    }
                });
                AlertDialog diag = builder.create();
                diag.show();
    }



    @Override
    protected void onResume() {
        h.postDelayed(new Runnable() {
            public void run() {
                onAutoClickEnemy();
                runnable=this;
                sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
                delay = sharedPref.getInt("delay",5000);
                h.postDelayed(runnable, delay);
            }
        }, delay);
        super.onResume();
    }
    @Override
    protected void onPause() {
        h.removeCallbacks(runnable);
        super.onPause();
    }

}
