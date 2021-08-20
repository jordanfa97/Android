package com.example.mastermind;

import android.app.Activity;
import android.os.Bundle;

public class PantallaPreferencias extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new FragmentPreferencias())
                .commit();
    }
}
