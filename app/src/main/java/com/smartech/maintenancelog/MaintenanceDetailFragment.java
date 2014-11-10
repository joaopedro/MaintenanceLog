package com.smartech.maintenancelog;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.smartech.maintenancelog.adapters.ProcedureRowAdapter;
import com.smartech.maintenancelog.db.Activity;
import com.smartech.maintenancelog.db.Equipamento;
import com.smartech.maintenancelog.db.Ordem;

import java.util.ArrayList;
import java.util.List;

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
    public static final String TWO_PANE = "two_pane";

    /**
     * The dummy content this fragment is presenting.
     */
    private Ordem mItem;

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
            String ordemId = getArguments().getString(ARG_ITEM_ID);
            mItem =  ((MaintenanceDetailActivity)getActivity()).getHelper().getOrdemRuntimeDao().queryForId(Long.valueOf(ordemId));
        }
        if (getArguments().containsKey(ARG_SCANNED_CODE)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            scannedCode = getArguments().getString(ARG_SCANNED_CODE);
            if(scannedCode != null){
                String numeroEquipamento = scannedCode.substring(scannedCode.indexOf("Número SAP: ") + 12, scannedCode.indexOf("\r\nEquipamento"));
                List<Equipamento> equipamentosByNumber = ((MaintenanceDetailActivity) getActivity()).getHelper().getEquipamentoRuntimeDao().queryForEq("number", numeroEquipamento);
                List<Ordem> ordensByFirstEquipment = ((MaintenanceDetailActivity) getActivity()).getHelper().getOrdemRuntimeDao().queryForEq("equipament_id", equipamentosByNumber.get(0).getId());
                mItem = ordensByFirstEquipment.get(0);
            }
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maintenance_detail, container, false);

        // Show the dummy content as text in a TextView.
//        if (mItem != null) {
//            ((TextView) rootView.findViewById(R.id.maintenance_detail)).setText(mItem.numOrdem);
//        }

        getActivity().getActionBar().setTitle("Equipamento "+mItem.getEquipament().getNumber() +" Ordem "+mItem.getOrderNumber());

        ListView listProcedimentos = (ListView) rootView.findViewById(R.id.list_procedimentos);

        ProcedureRowAdapter procedureRowAdapter = new ProcedureRowAdapter(getActivity(),
                new ArrayList<Activity>(mItem.getmActivities()));

        listProcedimentos.setAdapter(procedureRowAdapter);

        final Button historyButton = (Button) rootView.findViewById(R.id.historico);
        historyButton.setOnClickListener((View.OnClickListener) getActivity());

        final Button finalizarButton = (Button) rootView.findViewById(R.id.finalizar);
        finalizarButton.setOnClickListener((View.OnClickListener) getActivity());

        final Button standbyButton = (Button) rootView.findViewById(R.id.standby);
        standbyButton.setOnClickListener((View.OnClickListener) getActivity());

        final Button partsButton = (Button) rootView.findViewById(R.id.lista_material);
        partsButton.setOnClickListener((View.OnClickListener) getActivity());

        return rootView;
    }
}
