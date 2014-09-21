package com.smartech.maintenancelog;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.smartech.maintenancelog.dummy.DummyContent;

/**
 * A fragment representing a single Maintenance detail screen.
 * This fragment is either contained in a {@link MaintenanceListActivity}
 * in two-pane mode (on tablets) or a {@link MaintenanceDetailActivity}
 * on handsets.
 */
public class MaintenanceDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_SCANNED_CODE = "Scanned_Code";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    private String scannedCode;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MaintenanceDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
        if (getArguments().containsKey(ARG_SCANNED_CODE)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            scannedCode = getArguments().getString(ARG_SCANNED_CODE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maintenance_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.maintenance_detail)).setText(mItem.numOrdem);
        }

        if (scannedCode != null) {
            ((TextView) rootView.findViewById(R.id.maintenance_detail)).setText(scannedCode);
            //((TextView) rootView.findViewById(R.id.code_read_result)).setText(scannedCode);
        }

        return rootView;
    }
}
