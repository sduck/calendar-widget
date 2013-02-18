package com.plusonelabs.calendar.prefs;

import android.os.Bundle;
import android.preference.*;
import android.widget.Toast;

import com.plusonelabs.calendar.EventAppWidgetProvider;
import com.plusonelabs.calendar.R;

public class AppearancePreferencesFragment extends PreferenceFragment {

    private static final String BACKGROUND_TRANSPARENCY_DIALOG = "backgroundTransparencyDialog";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences_appearance);

        ListPreference multiDayPref = (ListPreference) findPreference(ICalendarPreferences.PREF_EVENTS_MULTIDAY);
        multiDayPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (preference instanceof ListPreference) {
                    ListPreference pref = (ListPreference) preference;
                    CharSequence[] entries = pref.getEntries();
                    CharSequence[] entryValues = pref.getEntryValues();

                    for (int i = 0; i < entryValues.length; i++) {
                        if (entryValues[i] == newValue) {
                            preference.setSummary(entries[i]);
                            break;
                        }
                    }
                }
                return true;
            }
        });
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
		if (preference.getKey().equals(ICalendarPreferences.PREF_SHOW_HEADER)) {
			if (preference instanceof CheckBoxPreference) {
				CheckBoxPreference checkPref = (CheckBoxPreference) preference;
				if (!checkPref.isChecked()) {
					Toast.makeText(getActivity(), R.string.appearance_display_header_warning,
							Toast.LENGTH_LONG).show();
				}
			}
		}
		if (preference.getKey().equals(ICalendarPreferences.PREF_BACKGROUND_TRANSPARENCY)) {
			new BackgroundTransparencyDialog().show(getFragmentManager(),
					BACKGROUND_TRANSPARENCY_DIALOG);
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}

	@Override
	public void onPause() {
		super.onPause();
		EventAppWidgetProvider.updateEventList(getActivity());
		EventAppWidgetProvider.updateAllWidgets(getActivity());
	}
}