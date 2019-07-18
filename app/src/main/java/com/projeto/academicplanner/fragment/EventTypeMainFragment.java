package com.projeto.academicplanner.fragment;


import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.academicplanner.R;
import com.projeto.academicplanner.adapter.Adapter_EventsType;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.EventType;

import java.util.ArrayList;
import java.util.List;

public class EventTypeMainFragment extends Fragment {


    //general variables
    private Button buttonEventsType;
    private TextView backToAddEditMain;
    private String idUserLoged;
    private List<EventType> eventsType = new ArrayList<>();
    private EventTypeAddFragment addEventTypeFragment;
    private AddEditMainFragment fragmentMain;

    //recycler view variables
    private RecyclerView recylcerEventsType;
    private RecyclerView.LayoutManager layout;
    private Adapter_EventsType adapter;

    private static final String TAG = "AddEditParametersActivity";


    public EventTypeMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View mainEventsType = inflater.inflate(R.layout.fragment_event_type_main, container, false);

        //start configurations
        buttonEventsType = mainEventsType.findViewById(R.id.buttonEventsType);
        backToAddEditMain = mainEventsType.findViewById(R.id.backToAddEditMain);
        recylcerEventsType = mainEventsType.findViewById(R.id.recylcerEventsType);

        //recovery loged user ID
        idUserLoged = ConfigFirebase.getUserId();

        //call methods
        //adapterConstructor();

        //create object and fill recyclerViewEventsType
        //EventType eventType = new EventType();
        //eventType.recovery(idUserLoged, eventsType, adapter);

        buttonEventsType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNewFragment();
            }
        });

        backToAddEditMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMain();
            }
        });

        return mainEventsType;

    }

    /*private void adapterConstructor() {

        //recycler view configuration
        layout = new LinearLayoutManager(getContext());
        adapter = new Adapter_EventsType(eventsType, getContext());
        recylcerEventsType.setAdapter(adapter);
        recylcerEventsType.setLayoutManager(layout);
        recylcerEventsType.setHasFixedSize(true);
        recylcerEventsType.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        adapter.setOnItemClickListener(new Adapter_Disciplines.ClickListener() {
            @Override
            public void onItemClick(Adapter_Disciplines adapter_disciplines, View v, final int position) {

                final ImageView imageEdit = v.findViewById(R.id.imageEdit);
                final ImageView imageDelete = v.findViewById(R.id.imageDelete);

                final EventType objectToAction = eventsType.get(position);

                imageDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        disciplineDelete(objectToAction);

                    }
                });

                imageEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        disciplineUpdate(objectToAction);

                    }
                });
            }
        });

    }

    private void disciplineUpdate(final EventType selectedToUpdate) {

        final Discipline disciplineUpdate = new Discipline();

        final AlertDialog.Builder updateDialog = new AlertDialog.Builder(getContext());

        final View updateDialogView = getLayoutInflater().inflate(R.layout.dialog_model, null);

        final EditText dialogUname = updateDialogView.findViewById(R.id.dialogName);
        final EditText dialogUacron = updateDialogView.findViewById(R.id.dialogAcronym);
        final Button dialogUbutton = updateDialogView.findViewById(R.id.buttonDialog);
        dialogUbutton.setText("UPDATE");

        dialogUname.setText(selectedToUpdate.getEventTypeName());
        dialogUacron.setText(selectedToUpdate.getEventTypeDescription());


        updateDialog.setView(updateDialogView);
        final AlertDialog updateDialogAlert = updateDialog.create();
        updateDialogAlert.show();

        dialogUbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String disciplineDialogName = dialogUname.getText().toString();
                String disciplineDialogAcronym = dialogUacron.getText().toString();

                disciplineUpdate.setIdUser(idUserLoged);
                disciplineUpdate.setIdDiscipline(selectedToUpdate.getIdDiscipline());
                disciplineUpdate.setDisciplineName(disciplineDialogName);
                disciplineUpdate.setAcronymDiscipline(disciplineDialogAcronym);
                disciplineUpdate.setDisciplineYear(selectedToUpdate.getDisciplineYear());
                disciplineUpdate.setDisciplineSemester(selectedToUpdate.getDisciplineSemester());
                disciplineUpdate.setIdUniversity(selectedToUpdate.getIdUniversity());
                disciplineUpdate.setUniversityName(selectedToUpdate.getUniversityName());
                disciplineUpdate.setIdCourse(selectedToUpdate.getIdCourse());
                disciplineUpdate.setCourseName(selectedToUpdate.getCourseName());

                disciplineUpdate.updateCourseData(disciplineUpdate);
                toastMsg("Discipline " + disciplineUpdate.getDisciplineName() + " successfully update");
                adapter.notifyDataSetChanged();
                updateDialogAlert.cancel();

            }
        });
    }

    private void disciplineDelete(final EventType selectedToRemove) {

        final AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getContext());
        final View deleteDialogView = getLayoutInflater().inflate(R.layout.dialog_model_delete_request, null);

        final Button buttonNoDelete = deleteDialogView.findViewById(R.id.buttonNoDelete);
        final Button buttonDelete = deleteDialogView.findViewById(R.id.buttonDelete);

        //method to create and show AlertDialog to DELETE
        deleteDialog.setView(deleteDialogView);
        final AlertDialog deleteDialogAlert = deleteDialog.create();
        deleteDialogAlert.show();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //method to remove the selected object
                selectedToRemove.delete();
                toastMsg("Discipline " + selectedToRemove.getEventTypeName() + " has been removed!");
                adapter.notifyDataSetChanged();
                deleteDialogAlert.cancel();
            }
        });

        buttonNoDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //method to cancel the delete operation
                toastMsg("Request CANCELED");
                deleteDialogAlert.cancel();
            }
        });
    }*/

    public void goToNewFragment() {
        addEventTypeFragment = new EventTypeAddFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, addEventTypeFragment);
        transaction.commit();
    }

    public void goBackToMain() {

        fragmentMain = new AddEditMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, fragmentMain);
        transaction.commit();

    }

    public void toastMsg(String text) {

        //show toast parameters
        Toast toastError = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toastError.setGravity(Gravity.CENTER, 0, 800);
        toastError.show();

    }
}
