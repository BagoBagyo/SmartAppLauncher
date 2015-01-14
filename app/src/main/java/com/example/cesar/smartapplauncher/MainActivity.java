package com.example.cesar.smartapplauncher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button display, hide;
        display = (Button) findViewById(R.id.display_button);
        hide = (Button) findViewById(R.id.hide_button);

        display.setOnClickListener(this);
        hide.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.display_button:
                Toast.makeText(getApplicationContext(), "Display Button is clicked", Toast.LENGTH_SHORT).show();
                startService(new Intent(MainActivity.this, SmartAppLauncher.class));
                findViewById(android.R.id.content).startDrag(null, new View.DragShadowBuilder(findViewById(R.id.display_button)), null, 0);
                break;
            case R.id.hide_button:
                Toast.makeText(getApplicationContext(), "Hide Button is clicked", Toast.LENGTH_SHORT).show();
                stopService(new Intent(MainActivity.this, SmartAppLauncher.class));
                break;
        }

    }

}
