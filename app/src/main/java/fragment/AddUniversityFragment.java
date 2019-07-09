package fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.projeto.academicplanner.R;

import helper.ConfigFirebase;
import model.University;

public class AddUniversityFragment extends Fragment {

    //general variables
    private EditText universityName, universityAcronym;
    private Button buttonUniversity;
    private TextView backText;
    private String idUserLoged;

    private UniversityMainFragment universityMainFragment;

    public AddUniversityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View addUniversity = inflater.inflate(R.layout.fragment_add_university, container, false);

        //start configurations
        universityName = addUniversity.findViewById(R.id.universityName);
        universityAcronym = addUniversity.findViewById(R.id.universityAcronym);
        buttonUniversity = addUniversity.findViewById(R.id.buttonUniversity);
        backText = addUniversity.findViewById(R.id.backText);


        //recovery loged user ID
        idUserLoged = ConfigFirebase.getUserId();

        buttonUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String universitycSaveName = universityName.getText().toString();
                String universitySaveAcronym = universityAcronym.getText().toString();

                universityAddNew(universitycSaveName, universitySaveAcronym);

            }

        });

        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });

        return addUniversity;
    }

    private void universityAddNew(String universitycSaveName, String universitySaveAcronym) {

        if (!universitycSaveName.isEmpty()) {
            if (!universitySaveAcronym.isEmpty()) {

                University university = new University();
                university.setIdUser(idUserLoged);
                university.setUniversityName(universitycSaveName);
                university.setUniversityAcronym(universitySaveAcronym);

                university.saveUniversityData();

                toastMsg("University " + university.getUniversityName() + " successfully added ");

                universityName.setText("");
                universityAcronym.setText("");

                backToMain();

            } else {
                toastMsg("Enter an acronym to University");
            }
        } else {
            toastMsg("Enter an University name");
        }

    }

    public void toastMsg(String text) {

        //show toast parameters
        Toast toastError = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toastError.setGravity(Gravity.CENTER, 0, 800);
        toastError.show();

    }

    public void backToMain() {
        universityMainFragment = new UniversityMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditParameters, universityMainFragment);
        transaction.commit();
    }

}
