package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.projeto.academicplanner.R;

public class AddAdmPersonFragment extends Fragment {

    public AddAdmPersonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View addAdmPerson = inflater.inflate(R.layout.fragment_add_adm_person, container, false);

        return addAdmPerson;

    }

}
