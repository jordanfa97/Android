package com.example.mastermind;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class FragmentPreferencias extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.mis_preferencias);
    }
}
